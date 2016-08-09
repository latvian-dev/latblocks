package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.api.recipes.LMRecipes;
import com.feed_the_beast.ftbl.util.FTBLib;
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
    public static final BlockNetherChest NETHER_CHEST = LatBlocks.register("nether_chest", new BlockNetherChest());
    public static final BlockCraftingPanel CRAFTING_PANEL = LatBlocks.register("crafting_panel", new BlockCraftingPanel());

    public static void init()
    {
        FTBLib.addTile(TileNetherChest.class, NETHER_CHEST.getRegistryName());
        FTBLib.addTile(TileCraftingPanel.class, CRAFTING_PANEL.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        NETHER_CHEST.addDefaultModel();
        CRAFTING_PANEL.addDefaultModel();
    }

    public static void loadRecipes()
    {
        LMRecipes.INSTANCE.addRecipe(new ItemStack(NETHER_CHEST),
                "NQN",
                "NCN",
                "NQN",
                'N', Blocks.NETHER_BRICK,
                'Q', ODItems.QUARTZ_BLOCK,
                'C', Items.END_CRYSTAL);

        LMRecipes.INSTANCE.addRecipe(new ItemStack(CRAFTING_PANEL, 2),
                "C",
                "C",
                'C', Blocks.CRAFTING_TABLE);
    }
}