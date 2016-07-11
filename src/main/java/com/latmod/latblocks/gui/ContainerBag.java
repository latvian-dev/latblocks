package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.client.gui.ContainerLM;
import com.latmod.latblocks.capabilities.IBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class ContainerBag extends ContainerLM
{
    public final IBag bag;
    public final EnumHand hand;

    public ContainerBag(EntityPlayer ep, IBag b, EnumHand h)
    {
        super(ep, b.getInventoryFromTab(b.getCurrentTab()));
        bag = b;
        hand = h;

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