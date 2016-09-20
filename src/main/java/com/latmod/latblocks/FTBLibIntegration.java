package com.latmod.latblocks;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.FTBLibAddon;
import com.feed_the_beast.ftbl.api.IFTBLibAddon;
import com.latmod.latblocks.block.LBBlocks;
import com.latmod.latblocks.gui.LBGuis;
import com.latmod.latblocks.item.LBItems;
import net.minecraft.util.ResourceLocation;

/**
 * Created by LatvianModder on 20.09.2016.
 */
@FTBLibAddon
public class FTBLibIntegration implements IFTBLibAddon
{
    public static FTBLibAPI API;

    @Override
    public void onFTBLibLoaded(FTBLibAPI api)
    {
        API = api;
        API.getRegistries().recipeHandlers().register(new ResourceLocation(LatBlocks.MOD_ID, "blocks"), new LBBlocks.Recipes());
        API.getRegistries().recipeHandlers().register(new ResourceLocation(LatBlocks.MOD_ID, "items"), new LBItems.Recipes());
        LBGuis.init(API.getRegistries());
    }
}