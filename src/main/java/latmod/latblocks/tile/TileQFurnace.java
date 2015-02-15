package latmod.latblocks.tile;

import latmod.core.*;
import latmod.core.tile.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.relauncher.*;

public class TileQFurnace extends TileInvLM implements IGuiTile, ISidedInventory // TileEntityFurnace
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
				if(result != null && isServer())
				{
					progress = 0;
					markDirty();
					
					if(items[SLOT_OUTPUT] == null || items[SLOT_OUTPUT].stackSize + result.stackSize <= items[SLOT_OUTPUT].getMaxStackSize())
					{
						if(items[SLOT_OUTPUT] == null)
							items[SLOT_OUTPUT] = result.copy();
						else items[SLOT_OUTPUT].stackSize += result.stackSize;
						
						result = null;
						markDirty();
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
				}
			}
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer() && !ep.isSneaking())
		{
			//if(!security.canInteract(ep))
			//	printOwner(ep); else
				LatCoreMC.openGui(ep, this, 0);
		}
		else if(isServer() && security.canInteract(ep) && LatCoreMC.isWrench(is))
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		
		return true;
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
	
	public void onBroken()
	{
		dropItems = false;
		ItemStack is = new ItemStack(LatBlocksItems.b_qfurnace, 1, 0);
		
		if(fuel > 0 || result != null || customName != null || InvUtils.getFirstFilledIndex(this, null, -1) != -1)
		{
			NBTTagCompound tag = new NBTTagCompound();
			writeTileData(tag);
			tag.removeTag("CustomName");
			is.setTagCompound(new NBTTagCompound());
			is.stackTagCompound.setTag(ITEM_TAG, tag);
			if(customName != null) is.setStackDisplayName(customName);
		}
		
		InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, is, 10);
		
		super.onBroken();
	}
	
	public boolean isLit()
	{ return result != null && fuel > 0; }
	
	public Container getContainer(EntityPlayer ep, int ID)
	{ return new ContainerQFurnace(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int ID)
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
}