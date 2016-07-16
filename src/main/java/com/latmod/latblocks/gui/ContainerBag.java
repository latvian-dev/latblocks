package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.client.gui.ContainerLM;
import com.latmod.latblocks.capabilities.IBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class ContainerBag extends ContainerLM
{
    public final IBag bag;

    public ContainerBag(EntityPlayer ep, IBag b)
    {
        super(ep, b.getInventoryFromTab(b.getCurrentTab()));
        bag = b;

        for(int y = 0; y < 5; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlotToContainer(new SlotItemHandler(itemHandler, x + y * 9, 7 + x * 18, 39 + y * 18));
            }
        }

        addPlayerSlots(7, 138, true);
    }

    public boolean enchantItem(EntityPlayer ep, int id)
    {
        if(!ep.worldObj.isRemote)
        {
            if(id >= 0 && id < bag.getTabCount())
            {
                IItemHandler inv = bag.getInventoryFromTab((byte) id);

                if(inv != null)
                {
                    bag.setCurrentTab((byte) id);
                    itemHandler = inv;

                    for(int y = 0; y < 5; y++)
                    {
                        for(int x = 0; x < 9; x++)
                        {
                            inventorySlots.set(x + y * 9, new SlotItemHandler(itemHandler, x + y * 9, 7 + x * 18, 39 + y * 18));
                        }
                    }

                    detectAndSendChanges();
                }
            }

            return true;
        }

        return false;
    }
}