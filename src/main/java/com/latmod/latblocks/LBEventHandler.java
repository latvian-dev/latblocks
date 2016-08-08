package com.latmod.latblocks;

import com.feed_the_beast.ftbl.api.item.ODItems;
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
        if(e.getLeft().hasCapability(LBCapabilities.BAG, null) && ODItems.itemHasOre(e.getRight(), ODItems.DIAMOND) && e.getRight().stackSize >= 3)
        {
            Bag bag = e.getLeft().getCapability(LBCapabilities.BAG, null);

            if(bag.upgrade(true))
            {
                ItemStack out = e.getLeft().copy();

                bag = out.getCapability(LBCapabilities.BAG, null);
                bag.upgrade(false);

                e.setMaterialCost(3);
                e.setCost(3);
                e.setOutput(out);
            }
        }
    }
}