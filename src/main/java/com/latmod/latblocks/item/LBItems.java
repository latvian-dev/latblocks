package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.lib.util.LMUtils;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.item.Item;

import java.util.Map;

/**
 * Created by LatvianModder on 13.11.2016.
 */
public class LBItems
{
    public static final Item bag = new ItemBag();
    public static final Item ender_bag = new ItemEnderBag();

    public static Map<String, Item> ITEMS;

    public static void init()
    {
        ITEMS = LMUtils.findAndRegister(Item.class, LatBlocks.MOD_ID, LBItems.class, null);
    }
}