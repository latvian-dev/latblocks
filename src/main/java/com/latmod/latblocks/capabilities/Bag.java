package com.latmod.latblocks.capabilities;

import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class Bag implements IBag, ICapabilitySerializable<NBTTagCompound>
{
    private final IItemHandler[] invMap;
    private UUID owner;
    private EnumPrivacyLevel privacyLevel;
    private int color;
    private byte currentTab;

    public Bag(int t)
    {
        invMap = new IItemHandler[t];

        for(int i = 0; i < invMap.length; i++)
        {
            invMap[i] = new ItemStackHandler(9 * 5)
            {
                @Override
                public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
                {
                    if(stack != null && stack.stackSize > 0 && stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
                    {
                        return null;
                    }

                    return super.insertItem(slot, stack, simulate);
                }
            };
        }

        privacyLevel = EnumPrivacyLevel.TEAM;
        color = 0xFFFFFFFF;
        currentTab = 0;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == LBCapabilities.BAG || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if(capability == LBCapabilities.BAG)
        {
            return (T) this;
        }
        else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) invMap[getCurrentTab()];
        }

        return null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return (NBTTagCompound) LBCapabilities.BAG_STORAGE.writeNBT(LBCapabilities.BAG, this, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        LBCapabilities.BAG_STORAGE.readNBT(LBCapabilities.BAG, this, null, nbt);
    }

    @Override
    public int getColor()
    {
        return color;
    }

    @Override
    public void setColor(int c)
    {
        color = 0xFF000000 | c;
    }

    @Override
    @Nullable
    public UUID getOwner()
    {
        return owner;
    }

    @Override
    public void setOwner(@Nullable UUID id)
    {
        owner = id;
    }

    @Nonnull
    @Override
    public EnumPrivacyLevel getPrivacyLevel()
    {
        return privacyLevel;
    }

    @Override
    public void setPrivacyLevel(@Nonnull EnumPrivacyLevel level)
    {
        privacyLevel = level;
    }

    @Override
    public int getTabCount()
    {
        return invMap.length;
    }

    @Nullable
    @Override
    public IItemHandler getInventoryFromTab(byte tab)
    {
        return invMap[tab];
    }

    @Override
    public byte getCurrentTab()
    {
        return currentTab;
    }

    @Override
    public void setCurrentTab(byte tab)
    {
        currentTab = tab;
    }
}