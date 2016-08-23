package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.IRecipeHandler;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBItems
{
    public static final ItemBag BAG = LatBlocks.register("bag", new ItemBag());
    public static final ItemEnderBag ENDER_BAG = LatBlocks.register("ender_bag", new ItemEnderBag());

    public static void init()
    {
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        BAG.addDefaultModel();
        ENDER_BAG.addDefaultModel();
    }

    public static class Recipes implements IRecipeHandler
    {
        @Override
        public boolean isActive()
        {
            return true;
        }

        @Override
        public void loadRecipes(IRecipes recipes)
        {
            recipes.addRecipe(new ItemStack(BAG, 1, 0),
                    "DSD", "WCW", "WQW",
                    'W', ODItems.WOOL,
                    'S', ODItems.STRING,
                    'C', ODItems.CHEST_WOOD,
                    'D', ODItems.DIAMOND,
                    'Q', ODItems.QUARTZ_BLOCK);

            recipes.addRecipe(new ItemStack(ENDER_BAG, 1, 0),
                    "LSL", "LCL", "LLL",
                    'L', ODItems.LEATHER,
                    'S', ODItems.STRING,
                    'C', "chestEnder");
        }
    }
}