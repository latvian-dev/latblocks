package latmod.latblocks.tile;

import latmod.ftbu.core.paint.*;
import latmod.latblocks.LatBlocksNetHandler;
import net.minecraft.entity.player.EntityPlayer;
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
	
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		LatBlocksNetHandler.setDefPaint(this, ep, paint);
	}
}