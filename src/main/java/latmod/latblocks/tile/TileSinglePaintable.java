package latmod.latblocks.tile;

import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileSinglePaintable extends TilePaintableLB
{
	public final Paint[] paint = new Paint[1];
	
	public void readTileData(NBTTagCompound tag)
	{
		Paint.readFromNBT(tag, "Texture", paint);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		Paint.writeToNBT(tag, "Texture", paint);
	}
	
	public boolean setPaint(PaintData p)
	{
		if(p.canReplace(paint[0]))
		{
			paint[0] = p.paint;
			markDirty();
			return true;
		}
		
		return false;
	}
	
	public Paint getPaint(int side)
	{ return paint[0]; }
	
	public void setPaint(int side, Paint p)
	{ paint[0] = p; }
	
	public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler config)
	{ return (paint[0] == null) ? null : new ItemStack(paint[0].block, 1, paint[0].meta); }
}