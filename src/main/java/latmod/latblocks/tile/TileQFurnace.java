package latmod.latblocks.tile;

import latmod.core.MathHelper;
import latmod.core.tile.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class TileQFurnace extends TileLM implements IGuiTile, ISidedInventory
{
	public int fuel = 0;
	public int progress = 0;
	public ItemStack result = null;
	
	public TileQFurnace()
	{
		items = new ItemStack[9];
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		fuel = tag.getInteger("Fuel");
		progress = tag.getByte("BurnTime");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setInteger("Fuel", fuel);
		tag.setByte("Progress", (byte)progress);
	}
	
	public void onUpdate()
	{
		if(isServer())
		{
		}
		else
		{
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int s, float x, float y, float z)
	{
		return true;
	}
	
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		
		if(is.hasDisplayName())
			customName = is.getDisplayName();
		
		setMeta(MathHelper.get2DRotation(ep).ordinal());
	}
	
	public void onBroken()
	{
		dropItems = false;
		super.onBroken();
	}
	
	public boolean isLit()
	{ return result != null && fuel > 0; }
	
	public Container getContainer(EntityPlayer ep, int ID)
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int ID)
	{ return null; }

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}
}