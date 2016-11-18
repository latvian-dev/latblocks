package com.latmod.latblocks;

import com.feed_the_beast.ftbl.lib.config.PropertyByte;
import com.feed_the_beast.ftbl.lib.config.PropertyItemStackList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * Created by LatvianModder on 05.10.2016.
 */
public class LatBlocksConfig
{
    public static final PropertyByte BAG_LEVEL_COST = new PropertyByte(3, 0, 100);
    public static final PropertyItemStackList BAG_ITEMS = new PropertyItemStackList(Arrays.asList(new ItemStack(Items.DIAMOND, 3), new ItemStack(Items.NETHER_STAR)));
}
