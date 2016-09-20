package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.gui.ContainerLM;
import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.latmod.latblocks.capabilities.Bag;
import com.latmod.latblocks.capabilities.LBCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class ContainerBag extends ContainerLM
{
    public final EnumHand hand;
    public Bag bag;
    private int lastTab = -1;
    private int lastPrivacyLevel = -1;

    public ContainerBag(EntityPlayer ep, EnumHand h)
    {
        super(ep);
        hand = h;
        bag = ep.getHeldItem(hand).getCapability(LBCapabilities.BAG, null);

        for(int y = 0; y < 5; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                addSlotToContainer(new SlotItemHandler(null, x + y * 9, 7 + x * 18, 29 + y * 18)
                {
                    @Override
                    public IItemHandler getItemHandler()
                    {
                        return ContainerBag.this.getItemHandler();
                    }
                });
            }
        }

        addPlayerSlots(7, 128, true);
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack)
    {
        if(slotID == 0)
        {
            bag = player.getHeldItem(hand).getCapability(LBCapabilities.BAG, null);
        }

        super.putStackInSlot(slotID, stack);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        int newTab = bag.currentTab;
        int newPrivacyLevel = bag.privacyLevel.ordinal();

        for(IContainerListener l : listeners)
        {
            if(lastTab != newTab)
            {
                l.sendProgressBarUpdate(this, 0, newTab);
            }

            if(lastPrivacyLevel != newPrivacyLevel)
            {
                l.sendProgressBarUpdate(this, 1, newPrivacyLevel);
            }
        }

        lastTab = newTab;
        lastPrivacyLevel = newPrivacyLevel;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        switch(id)
        {
            case 0:
                bag.currentTab = (byte) data;
                break;
            case 1:
                bag.privacyLevel = EnumPrivacyLevel.VALUES[data];
                break;
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer ep, int id)
    {
        if(!ep.worldObj.isRemote)
        {
            id = id & 0xFF;
            if(id == 210 || id == 211)
            {
                int level;

                if(id == 210)
                {
                    level = (bag.privacyLevel.ordinal() + 1) % EnumPrivacyLevel.VALUES.length;
                }
                else
                {
                    level = bag.privacyLevel.ordinal() - 1;
                    if(level < 0)
                    {
                        level = EnumPrivacyLevel.VALUES.length - 1;
                    }
                }

                bag.privacyLevel = EnumPrivacyLevel.VALUES[level];
            }
            else if(id >= 200 && id < 200 + bag.inv.size())
            {
                bag.currentTab = (byte) (id - 200);
            }

            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return bag.inv.get(bag.currentTab);
    }
}