package latmod.latblocks.tile;

import java.util.List;

import latmod.ftbu.core.tile.*;
import latmod.latblocks.item.ItemGlasses;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class TilePaintableLB extends TileLM implements IPaintable, IWailaTile.Stack, IWailaTile.Body
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
	
	public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler config)
	{
		Paint p = getPaint(data.getSide().ordinal());
		return (p == null) ? null : new ItemStack(p.block, 1, p.meta);
	}
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
		if(ItemGlasses.hasPlayer(data.getPlayer()) && getWailaStack(data, config) != null)
			info.add((new ItemStack(getBlockType(), 1, getBlockMetadata())).getDisplayName());
	}
}