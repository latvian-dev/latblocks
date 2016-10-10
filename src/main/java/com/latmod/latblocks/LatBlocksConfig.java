package com.latmod.latblocks;

import com.feed_the_beast.ftbl.api.RegistryObject;
import com.feed_the_beast.ftbl.api.config.ConfigValue;
import com.feed_the_beast.ftbl.api.config.IConfigFileProvider;
import com.feed_the_beast.ftbl.lib.config.PropertyByte;
import com.feed_the_beast.ftbl.lib.config.PropertyItemStackList;
import com.feed_the_beast.ftbl.lib.util.LMUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.Arrays;

/**
 * Created by LatvianModder on 05.10.2016.
 */
public class LatBlocksConfig
{
    @RegistryObject(LatBlocks.MOD_ID)
    public static final IConfigFileProvider FILE = () -> new File(LMUtils.folderConfig, "LatBlocks.json");

    @ConfigValue(id = "bag.level_cost", file = LatBlocks.MOD_ID)
    public static final PropertyByte BAG_LEVEL_COST = new PropertyByte(3, 0, 100);

    @ConfigValue(id = "bag.upgrade_items", file = LatBlocks.MOD_ID)
    public static final PropertyItemStackList BAG_ITEMS = new PropertyItemStackList(Arrays.asList(new ItemStack(Items.DIAMOND, 3), new ItemStack(Items.NETHER_STAR)));
}
