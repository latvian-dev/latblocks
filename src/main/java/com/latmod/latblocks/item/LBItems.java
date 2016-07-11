package com.latmod.latblocks.item;

import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBItems
{
    public static final ItemBag BAG = LatBlocks.mod.register("bag", new ItemBag());
    public static final ItemPainter PAINTER = LatBlocks.mod.register("painter", new ItemPainter(false));
    public static final ItemPainter PAINTER_DMD = LatBlocks.mod.register("painter_dmd", new ItemPainter(true));

    public static void init()
    {
    }
}