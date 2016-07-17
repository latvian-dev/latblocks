package com.latmod.latblocks;

import com.feed_the_beast.ftbl.api.client.gui.GuiHandlerRegistry;
import com.feed_the_beast.ftbl.util.CreativeTabLM;
import com.feed_the_beast.ftbl.util.LMMod;
import com.latmod.latblocks.block.LBBlocks;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.gui.LBGuiHandler;
import com.latmod.latblocks.item.LBItems;
import com.latmod.latblocks.net.LBNetHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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

    public static LMMod mod;
    public static CreativeTabLM tab, tabGlowium;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        mod = LMMod.create(MOD_ID);
        tab = new CreativeTabLM("latblocks");
        tabGlowium = new CreativeTabLM("latblocks.glowium");

        LBCapabilities.init();
        LBBlocks.init();
        LBItems.init();
        LBNetHandler.init();

        MinecraftForge.EVENT_BUS.register(new LBEventHandler());

        proxy.preInit();
        mod.onPostLoaded();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
        mod.loadRecipes();

        GuiHandlerRegistry.add(LBGuiHandler.INSTANCE);
    }
}
