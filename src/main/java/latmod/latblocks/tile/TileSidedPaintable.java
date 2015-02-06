package latmod.latblocks.tile;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TileSidedPaintable extends TilePaintableLB
{
	public final Paint[] paint = new Paint[6];
	
	public void readTileData(NBTTagCompound tag)
	{
		Paint.readFromNBT(tag, "Textures", paint);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		Paint.writeToNBT(tag, "Textures", paint);
	}
	
	public boolean setPaint(PaintData p)
	{
		if(p.player.isSneaking())
		{
			for(int i = 0; i < 6; i++)
				setPaint(i, p.paint);
			markDirty();
			return true;
		}
		
		if(p.canReplace(getPaint(p.side)))
		{
			setPaint(p.side, p.paint);
			markDirty();
			return true;
		}
		
		return false;
	}
	
	public Paint getPaint(int side)
	{ return paint[side]; }
	
	public void setPaint(int side, Paint p)
	{ paint[side] = p; }
}