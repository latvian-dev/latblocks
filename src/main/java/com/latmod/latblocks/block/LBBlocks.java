package com.latmod.latblocks.block;

import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBBlocks
{
    public static final BlockMultifurnace MULTIFURNACE = LatBlocks.mod.register("multifurnace", new BlockMultifurnace());
    public static final BlockNetherChest NETHER_CHEST = LatBlocks.mod.register("nether_chest", new BlockNetherChest());

    public static void init()
    {
    }
}