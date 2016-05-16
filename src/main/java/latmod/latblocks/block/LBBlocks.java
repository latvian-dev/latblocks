package latmod.latblocks.block;

import latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBBlocks
{
	public static final BlockPaintableBlock PAINTABLE_BLOCK = LatBlocks.mod.register("paintable", new BlockPaintableBlock());
	public static final BlockPaintableSlab PAINTABLE_SLAB = LatBlocks.mod.register("paintable_slab", new BlockPaintableSlab());
	
	public static void init() { }
}