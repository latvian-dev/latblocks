package com.latmod.latblocks;

import com.feed_the_beast.ftbl.lib.CreativeTabLM;
import com.latmod.latblocks.block.LBBlocks;
import com.latmod.latblocks.capabilities.Bag;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.item.LBItems;
import com.latmod.latblocks.net.LBNetHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
        LBBlocks.init();
        LBItems.init();
        LBNetHandler.init();
        tab.addIcon(new ItemStack(LBBlocks.nether_chest));
        PROXY.preInit();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        PROXY.postInit();
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
}
