package latmod.latblocks.tile;

import latmod.core.*;
import latmod.core.tile.*;
import latmod.core.util.MathHelperLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class TileQFurnace extends TileInvLM implements IGuiTile, ISidedInventory, ISecureTile // TileEntityFurnace
{
	public static final String ITEM_TAG = "Furnace";
	public static final int MAX_ITEMS = 3;
	public static final double MAX_PROGRESS = 175D;
	
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FUEL = 1;
	public static final int SLOT_OUTPUT = 2;
	
	public int fuel = 0;
	public int progress = 0;
	public ItemStack result = null;
	
	public TileQFurnace()
	{ super(MAX_ITEMS); }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		fuel = tag.getInteger("Fuel");
		progress = tag.getShort("Progress");
		result = InvUtils.loadStack(tag, "Result");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setInteger("Fuel", fuel);
		tag.setShort("Progress", (short)progress);
		InvUtils.saveStack(tag, "Result", result);
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer() && security.canInteract(ep) && InvUtils.isWrench(is))
		{
			if(ep.isSneaking())
			{
				dropItems = false;
				ItemStack drop = new ItemStack(LatBlocksItems.b_qfurnace, 1, 0);
				
				if(fuel > 0 || result != null || customName != null || InvUtils.getFirstFilledIndex(this, null, -1) != -1)
				{
					NBTTagCompound tag = new NBTTagCompound();
					writeTileData(tag);
					tag.removeTag("CustomName");
					drop.setTagCompound(new NBTTagCompound());
					drop.stackTagCompound.setTag(ITEM_TAG, tag);
					if(customName != null) drop.setStackDisplayName(customName);
				}
				
				InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, drop, 10);
				
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
			else
			{
				if(side == 0 || side == 1)
				{
					getMeta();
					if(blockMetadata == 2) setMeta(5);
					else if(blockMetadata == 3) setMeta(4);
					else if(blockMetadata == 4) setMeta(2);
					else if(blockMetadata == 5) setMeta(3);
				}
				else setMeta(side);
				markDirty();
			}
		}
		else if(isServer()) LatCoreMC.openGui(ep, this, null);
		return true;
	}
	
	public void onUpdate()
	{
		if(fuel == 0 && isServer() && items[SLOT_FUEL] != null)
		{
			fuel = TileEntityFurnace.getItemBurnTime(items[SLOT_FUEL]);
			
			if(fuel > 0)
			{
				items[SLOT_FUEL] = InvUtils.reduceItem(items[SLOT_FUEL]);
				markDirty();
			}
		}
		
		if(progress == 0)
		{
			if(isServer())
			{
				ItemStack out = (items[SLOT_INPUT] == null) ? null : FurnaceRecipes.smelting().getSmeltingResult(items[SLOT_INPUT]);
				
				if(out != null && fuel > 0)
				{
					items[SLOT_INPUT] = InvUtils.reduceItem(items[SLOT_INPUT]);
					result = out.copy();
					progress = 1;
					markDirty();
				}
			}
		}
		else
		{
			if(progress >= MAX_PROGRESS)
			{
				if(result != null)
				{
					progress = 0;
					markDirty();
					
					if(items[SLOT_OUTPUT] == null || items[SLOT_OUTPUT].stackSize + result.stackSize <= items[SLOT_OUTPUT].getMaxStackSize())
					{
						if(isServer())
						{
							if(items[SLOT_OUTPUT] == null)
								items[SLOT_OUTPUT] = result.copy();
							else items[SLOT_OUTPUT].stackSize += result.stackSize;
							
							result = null;
						}
						
						ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[blockMetadata];
						double px = xCoord + 0.5D + fd.offsetX * 0.6D;
						double pz = zCoord + 0.5D + fd.offsetZ * 0.6D;
						
						for(int i = 0; i < 40; i++)
						{
							double r = MathHelperLM.randomDouble(ParticleHelper.rand, -0.25D, 0.25D);
							double px1 = px + ((fd == ForgeDirection.WEST || fd == ForgeDirection.EAST) ? 0D : r);
							double py = yCoord + ParticleHelper.rand.nextFloat() * 6.0D / 16.0D;
							double pz1 = pz + ((fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH) ? 0D : r);
							
							worldObj.spawnParticle("flame", px1 , py, pz1, 0D, 0D, 0D);
						}
					}
				}
			}
			else
			{
				if(fuel > 0)
				{
					progress++;
					fuel--;
					
					if(fuel == 0 && isServer())
						markDirty();
					
					if(progress % 3 == 0)
					{
						ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[blockMetadata];
						double r = MathHelperLM.randomDouble(ParticleHelper.rand, -0.25D, 0.25D);
						double px1 = xCoord + 0.5D + fd.offsetX * 0.6D + ((fd == ForgeDirection.WEST || fd == ForgeDirection.EAST) ? 0D : r);
						double py = yCoord + ParticleHelper.rand.nextFloat() * 6.0D / 16.0D;
						double pz1 = zCoord + 0.5D + fd.offsetZ * 0.6D + ((fd == ForgeDirection.NORTH || fd == ForgeDirection.SOUTH) ? 0D : r);
						
						worldObj.spawnParticle("flame", px1 , py, pz1, 0D, 0D, 0D);
					}
				}
			}
		}
	}
	
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(ITEM_TAG))
			readTileData(is.stackTagCompound.getCompoundTag(ITEM_TAG));
		
		if(is.hasDisplayName())
			customName = is.getDisplayName();
		else
			customName = null;
		
		setMeta(MathHelperLM.get2DRotation(ep).ordinal());
	}
	
	public boolean isLit()
	{ return result != null && fuel > 0; }
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerQFurnace(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiQFurnace(new ContainerQFurnace(ep, this)); }
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
	
	public int[] getAccessibleSlotsFromSide(int s)
	{
		if(s == LatCoreMC.BOTTOM) return new int[]{ SLOT_FUEL };
		if(s == LatCoreMC.TOP) return new int[]{ SLOT_INPUT };
		return new int[] { SLOT_OUTPUT };
	}
	
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return true; }
	
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return true; }
	
	public boolean canPlayerInteract(EntityPlayer ep, boolean breakBlock)
	{ return security.canInteract(ep); }
	
	public void onPlayerNotOwner(EntityPlayer ep, boolean breakBlock)
	{ printOwner(ep); }
}