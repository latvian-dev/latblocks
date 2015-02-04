package latmod.latblocks.tile;

import latmod.core.tile.*;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class TilePaintableLB extends TileLM implements IPaintable, IWailaTile.Stack
{
	public boolean rerenderBlock()
	{ return true; }
	
	public abstract Paint getPaint(int side);
	public abstract void setPaint(int side, Paint p);
	
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z)
	{
		//return oldBlock != newBlock;
		return super.shouldRefresh(oldBlock, newBlock, oldMeta, newMeta, world, x, y, z);
	}
}