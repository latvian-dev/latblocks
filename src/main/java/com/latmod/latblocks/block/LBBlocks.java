package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.lib.block.ItemBlockLM;
import com.feed_the_beast.ftbl.lib.util.LMUtils;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Map;

/**
 * Created by LatvianModder on 13.11.2016.
 */
public class LBBlocks
{
    public static final Block nether_chest = new BlockNetherChest();
    public static final Block crafting_panel = new BlockCraftingPanel();

    public static Map<String, Block> BLOCKS;

    public static void init()
    {
        BLOCKS = LMUtils.findAndRegister(Block.class, LatBlocks.MOD_ID, LBBlocks.class, null);
        BLOCKS.forEach((key, value) -> GameRegistry.register(new ItemBlockLM(value).setRegistryName(LatBlocks.MOD_ID, key)));

        GameRegistry.registerTileEntity(TileNetherChest.class, "latblocks.nether_chest");
        GameRegistry.registerTileEntity(TileCraftingPanel.class, "latblocks.crafting_panel");
    }
}