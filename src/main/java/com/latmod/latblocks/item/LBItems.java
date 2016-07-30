package com.latmod.latblocks.item;

import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBItems
{
    public static final ItemBag BAG_1 = LatBlocks.mod.register("bag_1", new ItemBag(1));
    public static final ItemBag BAG_2 = LatBlocks.mod.register("bag_2", new ItemBag(2));
    public static final ItemBag BAG_3 = LatBlocks.mod.register("bag_3", new ItemBag(3));
    public static final ItemBag BAG_4 = LatBlocks.mod.register("bag_4", new ItemBag(4));
    public static final ItemBag BAG_5 = LatBlocks.mod.register("bag_5", new ItemBag(5));
    public static final ItemEnderBag ENDER_BAG = LatBlocks.mod.register("ender_bag", new ItemEnderBag());

    public static void init()
    {
        BAG_1.nextTierBag = BAG_2;
        BAG_2.nextTierBag = BAG_3;
        BAG_3.nextTierBag = BAG_4;
        BAG_4.nextTierBag = BAG_5;
    }
}