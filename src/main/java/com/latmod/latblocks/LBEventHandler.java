package com.latmod.latblocks;

import com.latmod.latblocks.item.ItemBag;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
        if(e.getLeft() != null && e.getRight() != null && e.getLeft().getItem() instanceof ItemBag)
        {
            ItemBag bag = (ItemBag) e.getLeft().getItem();

            if(bag.nextTierBag != null && e.getRight().getItem() == Items.DIAMOND && e.getRight().stackSize >= 3)
            {
                e.setMaterialCost(3);
                e.setCost(3);
                NBTTagCompound tag = new NBTTagCompound();
                e.getLeft().writeToNBT(tag);
                e.setOutput(new ItemStack(bag.nextTierBag, 1, 0, tag.getCompoundTag("ForgeCaps")));
            }
        }
    }
}