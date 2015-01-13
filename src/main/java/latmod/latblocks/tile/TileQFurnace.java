package latmod.latblocks.tile;

import latmod.core.*;
import latmod.core.tile.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.relauncher.*;

public class TileQFurnace extends TileLM implements IGuiTile, ISidedInventory // TileEntityFurnace
{
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FUEL = 1;
	public static final int SLOT_OUTPUT = 2;
	
	public int fuel = 0;
	public int progress = 0;
	public ItemStack result = null;
	
	public TileQFurnace()
	{
		items = new ItemStack[3];
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		fuel = tag.getInteger("Fuel");
		progress = tag.getShort("Progress");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setInteger("Fuel", fuel);
		tag.setShort("Progress", (byte)progress);
	}
	
	public void onUpdate()
	{
		if(isServer())
		{
			if(fuel == 0 && items[SLOT_FUEL] != null)
			{
				fuel = TileEntityFurnace.getItemBurnTime(items[SLOT_FUEL]);
				
				if(fuel > 0)
				{
					items[SLOT_FUEL] = InvUtils.reduceItem(items[SLOT_FUEL]);
					markDirty();
				}
			}
			
			if(progress >= 175)
			{
				progress = 0;
			}
			else progress++;
			
			if(fuel > 0) fuel--;
			
			markDirty();
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int s, float x, float y, float z)
	{ if(isServer()) openGui(ep, 0); return true; }
	
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("Furnace"))
			readTileData(is.stackTagCompound.getCompoundTag("Furnace"));
		
		if(is.hasDisplayName())
			customName = is.getDisplayName();
		
		setMeta(MathHelperLM.get2DRotation(ep).ordinal());
	}
	
	public void onBroken()
	{
		dropItems = false;
		super.onBroken();
		
		ItemStack is = new ItemStack(LatBlocksItems.b_qfurnace, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		writeTileData(tag);
		is.setTagCompound(new NBTTagCompound());
		is.stackTagCompound.setTag("Furnace", tag);
		if(customName != null) is.setStackDisplayName(customName);
		InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, is, 10);
	}
	
	public boolean isLit()
	{ return result != null && fuel > 0; }
	
	public Container getContainer(EntityPlayer ep, int ID)
	{ return new ContainerQFurnace(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int ID)
	{ return new GuiQFurnace(new ContainerQFurnace(ep, this)); }
	
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