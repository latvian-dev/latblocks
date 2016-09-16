package com.latmod.latblocks.capabilities;

import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.latmod.lib.util.LMStringUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class Bag implements INBTSerializable<NBTTagCompound>
{
    public static class BagItemStackHandler extends ItemStackHandler
    {
        public BagItemStackHandler()
        {
            super(45);
        }

        public static boolean isItemValid(ItemStack stack)
        {
            return stack == null || !stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            if(!isItemValid(stack))
            {
                return null;
            }

            return super.insertItem(slot, stack, simulate);
        }
    }

    public final List<BagItemStackHandler> inv;
    public UUID owner;
    public EnumPrivacyLevel privacyLevel;
    public int currentTab;
    private byte color;

    public Bag()
    {
        inv = new ArrayList<>();
        inv.add(new BagItemStackHandler());
        privacyLevel = EnumPrivacyLevel.TEAM;
        color = 0xFFFFFFFF;
        currentTab = 0;
    }

    public byte getColorID()
    {
        return color;
    }

    public void setColorID(byte id)
    {
        color = id;
    }

    public boolean upgrade(boolean simulate)
    {
        if(inv.size() < 5)
        {
            if(!simulate)
            {
                inv.add(new BagItemStackHandler());
            }

            return true;
        }

        return false;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setByte("Tab", (byte) currentTab);
        tag.setByte("Color", getColorID());
        tag.setByte("Privacy", (byte) privacyLevel.ordinal());

        if(owner != null)
        {
            tag.setString("Owner", LMStringUtils.fromUUID(owner));
        }

        NBTTagList invList = new NBTTagList();

        for(BagItemStackHandler i : inv)
        {
            invList.appendTag(i.serializeNBT());
        }

        tag.setTag("InvTabs", invList);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        currentTab = nbt.getByte("Tab") & 0xFF;
        setColorID(nbt.getByte("Color"));
        owner = nbt.hasKey("Owner") ? LMStringUtils.fromString(nbt.getString("Owner")) : null;
        privacyLevel = EnumPrivacyLevel.VALUES[nbt.getByte("Privacy")];
        inv.clear();

        NBTTagList invList = nbt.getTagList("InvTabs", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < invList.tagCount(); i++)
        {
            BagItemStackHandler inv1 = new BagItemStackHandler();
            inv1.deserializeNBT(invList.getCompoundTagAt(i));
            inv.add(inv1);
        }

        if(inv.isEmpty())
        {
            inv.add(new BagItemStackHandler());
        }
    }
}