package com.latmod.latblocks.block;

import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBBlocks
{
    public static final BlockPaintableBlock PAINTABLE_BLOCK = LatBlocks.mod.register("paintable", new BlockPaintableBlock());
    public static final BlockPaintableSlab PAINTABLE_SLAB = LatBlocks.mod.register("slab", new BlockPaintableSlab(8D / 16D));
    public static final BlockPaintableSlab PAINTABLE_PANEL = LatBlocks.mod.register("panel", new BlockPaintableSlab(4D / 16D));
    public static final BlockPaintableSlab PAINTABLE_COVER = LatBlocks.mod.register("cover", new BlockPaintableSlab(2D / 16D));
    public static final BlockPaintableSlab PAINTABLE_CARPET = LatBlocks.mod.register("carpet", new BlockPaintableSlab(1D / 16D));
    public static final BlockMultifurnace MULTIFURNACE = LatBlocks.mod.register("multifurnace", new BlockMultifurnace());

    public static void init()
    {
    }
}