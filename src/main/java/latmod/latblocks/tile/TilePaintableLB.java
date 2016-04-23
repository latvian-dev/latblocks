package latmod.latblocks.tile;

import ftb.lib.api.tile.IWailaTile;
import ftb.lib.api.tile.TileLM;
import ftb.lib.api.waila.WailaDataAccessor;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.api.IPaintable;
import latmod.latblocks.api.Paint;
import latmod.latblocks.api.PaintData;
import latmod.latblocks.item.ItemGlasses;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public abstract class TilePaintableLB extends TileLM implements IPaintable, IWailaTile.Stack, IWailaTile.Body
{
	public final Paint[] paint;
	
	public TilePaintableLB(int i)
	{ paint = new Paint[i]; }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		Paint.readFromNBT(tag, "Texture", paint);
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		Paint.writeToNBT(tag, "Texture", paint);
	}
	
	@Override
	public boolean rerenderBlock()
	{ return true; }
	
	public abstract Paint getPaint(int side);
	public abstract void setPaint(int side, Paint p);
	
	@Override
	public boolean isPaintValid(int side, Paint p)
	{ return true; }
	
	@Override
	public final boolean setPaint(PaintData p)
	{
		if(p.player.isSneaking())
		{
			for(int i = 0; i < paint.length; i++)
				if(p.paint == null || isPaintValid(i, p.paint)) setPaint(i, p.paint);
			markDirty();
			return true;
		}
		
		if(p.canReplace(getPaint(p.side)))
		{
			if(p.paint == null || isPaintValid(p.side, p.paint)) setPaint(p.side, p.paint);
			markDirty();
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		LatBlocks.proxy.setDefPaint(this, ep, paint);
	}
	
	@Override
	public ItemStack getWailaStack(WailaDataAccessor data)
	{
		Paint p = getPaint(data.side);
		return (p == null) ? null : new ItemStack(p.block, 1, p.meta);
	}
	
	@Override
	public void addWailaBody(WailaDataAccessor data, List<String> info)
	{
		if(ItemGlasses.hasPlayer(data.player) && getWailaStack(data) != null)
			info.add((new ItemStack(getBlockType(), 1, getBlockMetadata())).getDisplayName());
	}
}