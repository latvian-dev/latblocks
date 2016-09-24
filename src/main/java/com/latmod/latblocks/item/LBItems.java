package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.IRecipeHandler;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.feed_the_beast.ftbl.api.recipes.RecipeHandler;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBItems
{
    public static final ItemBag BAG = new ItemBag();
    public static final ItemEnderBag ENDER_BAG = new ItemEnderBag();

    public static void init()
    {
        LatBlocks.register("bag", BAG);
        LatBlocks.register("ender_bag", ENDER_BAG);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        BAG.registerDefaultModel();
        ENDER_BAG.registerDefaultModel();
    }

    @RecipeHandler
    public static final IRecipeHandler RECIPES = new IRecipeHandler()
    {
        @Override
        public ResourceLocation getID()
        {
            return new ResourceLocation(LatBlocks.MOD_ID, "items");
        }

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
    };
}