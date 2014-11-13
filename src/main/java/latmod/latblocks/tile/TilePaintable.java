package latmod.latblocks.tile;

import latmod.core.tile.*;
import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TilePaintable extends TileLM implements IPaintable, IWailaTile.Stack
{
	public final Paint[] paint = new Paint[6];
	
	public boolean rerenderBlock()
	{ return true; }
	
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
				currentPaint()[i] = p.paint;
			markDirty();
			return true;
		}
		
		if(p.canReplace(currentPaint()[p.side]))
		{
			currentPaint()[p.side] = p.paint;
			markDirty();
			return true;
		}
		
		return false;
	}
	
	public Paint[] currentPaint()
	{ return paint; }
	
	public int iconMeta()
	{ return 0; }
	
	public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler config)
	{
		Paint p = currentPaint()[data.getSide().ordinal()];
		return (p == null) ? null : new ItemStack(p.block, 1, p.meta);
	}
}