package com.latmod.latblocks;

import com.feed_the_beast.ftbl.api.events.RegisterRecipesEvent;
import com.feed_the_beast.ftbl.lib.CreativeTabLM;
import com.feed_the_beast.ftbl.lib.item.ODItems;
import com.latmod.latblocks.block.BlockCraftingPanel;
import com.latmod.latblocks.block.BlockNetherChest;
import com.latmod.latblocks.capabilities.Bag;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.item.ItemBag;
import com.latmod.latblocks.item.ItemEnderBag;
import com.latmod.latblocks.net.LBNetHandler;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;

/**
 * Created by LatvianModder on 12.05.2016.
 */
@Mod(modid = LatBlocks.MOD_ID, name = LatBlocks.MOD_ID, version = "0.0.0", useMetadata = true, dependencies = "required-after:ftbl")
@Mod.EventBusSubscriber
public class LatBlocks
{
    public static final String MOD_ID = "latblocks";

    @Mod.Instance(MOD_ID)
    public static LatBlocks INST;

    @SidedProxy(serverSide = "com.latmod.latblocks.LatBlocksCommon", clientSide = "com.latmod.latblocks.client.LatBlocksClient")
    public static LatBlocksCommon PROXY;

    public CreativeTabLM tab;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        tab = new CreativeTabLM("latblocks");
        LBCapabilities.enable();
        LBNetHandler.init();
        tab.addIcon(new ItemStack(LatBlocks.Items.nether_chest));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        PROXY.postInit();
    }

    @GameRegistry.ObjectHolder(LatBlocks.MOD_ID)
    public static class Items
    {
        public static Item bag;
        public static Item ender_bag;

        public static Item nether_chest;
        public static Item crafting_panel;
    }

    @GameRegistry.ObjectHolder(LatBlocks.MOD_ID)
    public static class Blocks
    {
        public static Block nether_chest;
        public static Block crafting_panel;
    }

    @SubscribeEvent
    public static void onAnvilEvent(AnvilUpdateEvent e)
    {
        if(e.getLeft().hasCapability(LBCapabilities.BAG, null))
        {
            Bag bag = e.getLeft().getCapability(LBCapabilities.BAG, null);
            ItemStack rightItem = LatBlocksConfig.BAG_ITEMS.containsItem(e.getRight(), 2);

            if(rightItem != null && bag.upgrade(true))
            {
                ItemStack out = e.getLeft().copy();

                bag = out.getCapability(LBCapabilities.BAG, null);
                bag.upgrade(false);

                e.setMaterialCost(rightItem.stackSize);
                e.setCost(LatBlocksConfig.BAG_LEVEL_COST.getByte());
                e.setOutput(out);
            }
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) throws Exception
    {
        //@formatter:off
        event.getRegistry().registerAll
        (
            new BlockNetherChest().setRegistryName(LatBlocks.MOD_ID, "nether_chest"),
            new BlockCraftingPanel().setRegistryName(LatBlocks.MOD_ID, "crafting_panel")
        );
        //@formatter:on

        GameRegistry.registerTileEntity(TileNetherChest.class, "latblocks.nether_chest");
        GameRegistry.registerTileEntity(TileCraftingPanel.class, "latblocks.crafting_panel");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) throws Exception
    {
        //@formatter:off
        event.getRegistry().registerAll
        (
            new ItemBag().setRegistryName(LatBlocks.MOD_ID, "bag"),
            new ItemEnderBag().setRegistryName(LatBlocks.MOD_ID, "ender_bag")
        );
        //@formatter:on

        for(Block block : new Block[] {Blocks.nether_chest, Blocks.crafting_panel})
        {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegisterRecipesEvent event) throws Exception
    {
        //@formatter:off
        event.getRecipes().addRecipe(new ItemStack(Items.bag, 1, 0),
                "DSD", "WCW", "WQW",
                'W', ODItems.WOOL,
                'S', ODItems.STRING,
                'C', ODItems.CHEST_WOOD,
                'D', ODItems.DIAMOND,
                'Q', ODItems.QUARTZ_BLOCK);

        event.getRecipes().addRecipe(new ItemStack(Items.ender_bag, 1, 0),
                "LSL", "LCL", "LLL",
                'L', ODItems.LEATHER,
                'S', ODItems.STRING,
                'C', "chestEnder");
        
        event.getRecipes().addRecipe(new ItemStack(Items.nether_chest),
                "NQN", "NCN", "NQN",
                'N', net.minecraft.init.Blocks.NETHER_BRICK,
                'Q', ODItems.QUARTZ_BLOCK,
                'C', net.minecraft.init.Items.END_CRYSTAL);

        event.getRecipes().addRecipe(new ItemStack(Items.crafting_panel, 4),
                " T ", "TCT", " T ",
                'T', net.minecraft.init.Blocks.CRAFTING_TABLE,
                'C', ODItems.CHEST_WOOD);
        //@formatter:on
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) throws Exception
    {
        for(Field f : Items.class.getDeclaredFields())
        {
            Item item = (Item) f.get(null);
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
