package com.latmod.latblocks;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.util.CreativeTabLM;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.latblocks.block.LBBlocks;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.gui.LBGuis;
import com.latmod.latblocks.item.LBItems;
import com.latmod.latblocks.net.LBNetHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

/**
 * Created by LatvianModder on 12.05.2016.
 */
@Mod(modid = LatBlocks.MOD_ID, name = "LatBlocks", version = "@VERSION@")
public class LatBlocks
{
    public static final String MOD_ID = "latblocks";

    @Mod.Instance(MOD_ID)
    public static LatBlocks inst;

    @SidedProxy(serverSide = "com.latmod.latblocks.LatBlocksCommon", clientSide = "com.latmod.latblocks.client.LatBlocksClient")
    public static LatBlocksCommon proxy;

    public static CreativeTabLM tab;

    public static <K extends IForgeRegistryEntry<?>> K register(String id, K obj)
    {
        return FTBLib.register(new ResourceLocation(MOD_ID, id), obj);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        tab = new CreativeTabLM("latblocks");
        LBCapabilities.enable();
        LBBlocks.init();
        LBItems.init();
        LBNetHandler.init();
        LBGuis.init();
        proxy.preInit();
        MinecraftForge.EVENT_BUS.register(new LBEventHandler());
        tab.addIcon(new ItemStack(LBBlocks.NETHER_CHEST));

        FTBLibAPI.get().getRegistries().recipeHandlers().register(new ResourceLocation(MOD_ID, "blocks"), new LBBlocks.Recipes());
        FTBLibAPI.get().getRegistries().recipeHandlers().register(new ResourceLocation(MOD_ID, "items"), new LBItems.Recipes());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
