package com.latmod.latblocks;

import com.latmod.latblocks.capabilities.Bag;
import com.latmod.latblocks.capabilities.LBCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class LBEventHandler
{
    @SubscribeEvent
    public void onAnvilEvent(AnvilUpdateEvent e)
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