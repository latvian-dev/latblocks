package com.latmod.latblocks.item;

import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBItems
{
    public static final ItemBag BAG = LatBlocks.mod.register("bag", new ItemBag());
    public static final ItemEnderBag ENDER_BAG = LatBlocks.mod.register("ender_bag", new ItemEnderBag());

    public static void init()
    {
    }
}