package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.client.gui.ContainerLM;
import com.latmod.latblocks.capabilities.IBag;
import net.minecraft.entity.player.EntityPlayer;
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
}