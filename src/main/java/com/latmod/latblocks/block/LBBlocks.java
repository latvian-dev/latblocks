package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.IRecipeHandler;
import com.feed_the_beast.ftbl.api.recipes.IRecipes;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.latblocks.tile.TileNetherChest;
import com.latmod.lib.util.LMUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class LBBlocks
{
    public static final BlockNetherChest NETHER_CHEST = LatBlocks.register("nether_chest", new BlockNetherChest());
    public static final BlockCraftingPanel CRAFTING_PANEL = LatBlocks.register("crafting_panel", new BlockCraftingPanel());

    public static void init()
    {
        LMUtils.addTile(TileNetherChest.class, NETHER_CHEST.getRegistryName());
        LMUtils.addTile(TileCraftingPanel.class, CRAFTING_PANEL.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        NETHER_CHEST.registerDefaultModel();
        CRAFTING_PANEL.registerDefaultModel();
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
            recipes.addRecipe(new ItemStack(NETHER_CHEST),
                    "NQN", "NCN", "NQN",
                    'N', Blocks.NETHER_BRICK,
                    'Q', ODItems.QUARTZ_BLOCK,
                    'C', Items.END_CRYSTAL);

            recipes.addRecipe(new ItemStack(CRAFTING_PANEL, 2),
                    "C", "C",
                    'C', Blocks.CRAFTING_TABLE);
        }
    }
}