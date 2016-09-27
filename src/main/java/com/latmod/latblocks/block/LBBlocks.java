package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.recipes.IRecipeHandler;
import com.feed_the_beast.ftbl.api.recipes.RecipeHandler;
import com.feed_the_beast.ftbl.lib.item.ODItems;
import com.feed_the_beast.ftbl.lib.util.LMUtils;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.latblocks.tile.TileNetherChest;
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
    public static final BlockNetherChest NETHER_CHEST = new BlockNetherChest();
    public static final BlockCraftingPanel CRAFTING_PANEL = new BlockCraftingPanel();

    public static void init()
    {
        LatBlocks.register("nether_chest", NETHER_CHEST);
        LatBlocks.register("crafting_panel", CRAFTING_PANEL);

        LMUtils.addTile(TileNetherChest.class, NETHER_CHEST.getRegistryName());
        LMUtils.addTile(TileCraftingPanel.class, CRAFTING_PANEL.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        NETHER_CHEST.registerDefaultModel();
        CRAFTING_PANEL.registerDefaultModel();
    }

    @RecipeHandler
    public static final IRecipeHandler RECIPES = recipes ->
    {
        recipes.addRecipe(new ItemStack(NETHER_CHEST),
                "NQN", "NCN", "NQN",
                'N', Blocks.NETHER_BRICK,
                'Q', ODItems.QUARTZ_BLOCK,
                'C', Items.END_CRYSTAL);

        recipes.addRecipe(new ItemStack(CRAFTING_PANEL, 4),
                " T ", "TCT", " T ",
                'T', Blocks.CRAFTING_TABLE,
                'C', ODItems.CHEST_WOOD);
    };
}