package com.latmod.latblocks;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.FTBLibPlugin;
import com.feed_the_beast.ftbl.api.IFTBLibClientRegistry;
import com.feed_the_beast.ftbl.api.IFTBLibPlugin;
import com.feed_the_beast.ftbl.api.IFTBLibRegistry;
import com.feed_the_beast.ftbl.api.IRecipes;
import com.feed_the_beast.ftbl.lib.item.ODItems;
import com.feed_the_beast.ftbl.lib.util.LMUtils;
import com.latmod.latblocks.block.LBBlocks;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.gui.ContainerBag;
import com.latmod.latblocks.gui.ContainerCraftingPanel;
import com.latmod.latblocks.gui.ContainerNetherChest;
import com.latmod.latblocks.gui.GuiBag;
import com.latmod.latblocks.gui.GuiCraftingPanel;
import com.latmod.latblocks.gui.GuiNetherChest;
import com.latmod.latblocks.item.LBItems;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;

import java.io.File;

/**
 * Created by LatvianModder on 20.09.2016.
 */
public enum FTBLibIntegration implements IFTBLibPlugin
{
    @FTBLibPlugin
    INSTANCE;

    public static FTBLibAPI API;

    @Override
    public void init(FTBLibAPI api)
    {
        API = api;
    }

    @Override
    public void registerCommon(IFTBLibRegistry reg)
    {
        reg.addConfigFileProvider(LatBlocks.MOD_ID, () -> new File(LMUtils.folderConfig, "LatBlocks.json"));
        reg.addConfig(LatBlocks.MOD_ID, "bag.level_cost", LatBlocksConfig.BAG_LEVEL_COST);
        reg.addConfig(LatBlocks.MOD_ID, "bag.upgrade_items", LatBlocksConfig.BAG_ITEMS);

        reg.addGuiContainer(ContainerNetherChest.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileNetherChest) ? new ContainerNetherChest(player, (TileNetherChest) te) : null;
        });

        reg.addGuiContainer(ContainerCraftingPanel.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileCraftingPanel) ? new ContainerCraftingPanel(player, (TileCraftingPanel) te) : null;
        });

        reg.addGuiContainer(ContainerBag.BAG_MAIN_HAND, (player, pos, nbt) ->
        {
            ItemStack is = player.getHeldItem(EnumHand.MAIN_HAND);
            return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new ContainerBag(player, EnumHand.MAIN_HAND) : null;
        });

        reg.addGuiContainer(ContainerBag.BAG_OFF_HAND, (player, pos, nbt) ->
        {
            ItemStack is = player.getHeldItem(EnumHand.OFF_HAND);
            return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new ContainerBag(player, EnumHand.OFF_HAND) : null;
        });
    }

    @Override
    public void registerClient(IFTBLibClientRegistry reg)
    {
        reg.addGui(ContainerNetherChest.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileNetherChest) ? new GuiNetherChest(new ContainerNetherChest(player, (TileNetherChest) te)).getWrapper() : null;
        });

        reg.addGui(ContainerCraftingPanel.ID, (player, pos, nbt) ->
        {
            TileEntity te = player.worldObj.getTileEntity(pos);
            return (te instanceof TileCraftingPanel) ? new GuiCraftingPanel(new ContainerCraftingPanel(player, (TileCraftingPanel) te)).getWrapper() : null;
        });

        reg.addGui(ContainerBag.BAG_MAIN_HAND, (player, pos, nbt) ->
        {
            ItemStack is = player.getHeldItem(EnumHand.MAIN_HAND);
            return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new GuiBag(new ContainerBag(player, EnumHand.MAIN_HAND)).getWrapper() : null;
        });

        reg.addGui(ContainerBag.BAG_OFF_HAND, (player, pos, nbt) ->
        {
            ItemStack is = player.getHeldItem(EnumHand.OFF_HAND);
            return (is != null && is.hasCapability(LBCapabilities.BAG, null)) ? new GuiBag(new ContainerBag(player, EnumHand.OFF_HAND)).getWrapper() : null;
        });
    }

    @Override
    public void registerRecipes(IRecipes recipes)
    {
        //@formatter:off
        recipes.addRecipe(new ItemStack(LBItems.bag, 1, 0),
                "DSD", "WCW", "WQW",
                'W', ODItems.WOOL,
                'S', ODItems.STRING,
                'C', ODItems.CHEST_WOOD,
                'D', ODItems.DIAMOND,
                'Q', ODItems.QUARTZ_BLOCK);

        recipes.addRecipe(new ItemStack(LBItems.ender_bag, 1, 0),
                "LSL", "LCL", "LLL",
                'L', ODItems.LEATHER,
                'S', ODItems.STRING,
                'C', "chestEnder");

        recipes.addRecipe(new ItemStack(LBBlocks.nether_chest),
                "NQN", "NCN", "NQN",
                'N', net.minecraft.init.Blocks.NETHER_BRICK,
                'Q', ODItems.QUARTZ_BLOCK,
                'C', net.minecraft.init.Items.END_CRYSTAL);

        recipes.addRecipe(new ItemStack(LBBlocks.crafting_panel, 4),
                " T ", "TCT", " T ",
                'T', net.minecraft.init.Blocks.CRAFTING_TABLE,
                'C', ODItems.CHEST_WOOD);
        //@formatter:on
    }
}