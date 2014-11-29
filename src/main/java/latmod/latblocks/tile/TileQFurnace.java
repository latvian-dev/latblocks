package latmod.latblocks.tile;

import latmod.core.LatCoreMC;
import latmod.core.tile.TileLM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileQFurnace extends TileLM
{
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
	
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		
		if(is.hasDisplayName())
			customName = is.getDisplayName();
		
		setMeta(LatCoreMC.get2DRotation(ep).ordinal());
	}
	
	public void onBroken()
	{
		dropItems = false;
		super.onBroken();
	}
	
	public boolean isLit()
	{ return result != null && fuel > 0; }
}