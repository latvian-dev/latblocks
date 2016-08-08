package com.latmod.latblocks.block;

import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBBlocks
{
    public static final BlockNetherChest NETHER_CHEST = LatBlocks.mod.register("nether_chest", new BlockNetherChest());
    public static final BlockCraftingPanel CRAFTING_PANEL = LatBlocks.mod.register("crafting_panel", new BlockCraftingPanel());

    public static void init()
    {
    }
}