package latmod.latblocks.tile.blocks;

import latmod.latblocks.tile.TileSidedPaintable;
import net.minecraft.nbt.NBTTagCompound;

public class TilePaintableRS extends TileSidedPaintable
{
	public final Paint[] paint_on = new Paint[6];
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		Paint.readFromNBT(tag, "TexturesOn", paint_on);
		redstonePowered = tag.getBoolean("RSIn");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		Paint.writeToNBT(tag, "TexturesOn", paint_on);
		tag.setBoolean("RSIn", redstonePowered);
	}
	
	public void onUpdate()
	{
	}
	
	public void onNeighborBlockChange()
	{
		if(isServer())
		{
			redstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
			markDirty();
		}
	}
	
	public Paint[] getPaint()
	{ return redstonePowered ? paint_on : paint; }
	
	public int iconMeta()
	{ return redstonePowered ? 1 : 0; }
}