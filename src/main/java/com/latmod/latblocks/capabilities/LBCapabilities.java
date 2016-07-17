package com.latmod.latblocks.capabilities;

import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.latmod.lib.util.LMUtils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class LBCapabilities
{
    public static final Capability.IStorage<IBag> BAG_STORAGE = new Capability.IStorage<IBag>()
    {
        @Override
        public NBTBase writeNBT(Capability<IBag> capability, IBag instance, EnumFacing side)
        {
            NBTTagCompound tag = new NBTTagCompound();

            tag.setByte("Tab", (byte) instance.getCurrentTab());
            tag.setInteger("Color", instance.getColor());
            tag.setByte("Privacy", (byte) instance.getPrivacyLevel().ordinal());

            if(instance.getOwner() != null)
            {
                tag.setString("Owner", LMUtils.fromUUID(instance.getOwner()));
            }

            NBTTagCompound invTag = new NBTTagCompound();

            for(byte i = 0; i < instance.getTabCount(); i++)
            {
                IItemHandler inv = instance.getInventoryFromTab(i);

                if(inv != null && inv instanceof INBTSerializable<?>)
                {
                    invTag.setTag(Byte.toString(i), ((INBTSerializable<?>) inv).serializeNBT());
                }
            }

            tag.setTag("Inv", invTag);

            //FTBLib.dev_logger.info("TX bag: " + instance.getTabCount() + ": " + tag);

            return tag;
        }

        @Override
        public void readNBT(Capability<IBag> capability, IBag instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound tag = (NBTTagCompound) nbt;

            instance.setCurrentTab(tag.getByte("Tab") & 0xFF);
            instance.setColor(tag.getInteger("Color"));
            instance.setOwner(tag.hasKey("Owner") ? LMUtils.fromString(tag.getString("Owner")) : null);
            instance.setPrivacyLevel(EnumPrivacyLevel.VALUES[tag.getByte("Privacy")]);

            NBTTagCompound invTag = tag.getCompoundTag("Inv");

            for(byte i = 0; i < instance.getTabCount(); i++)
            {
                if(invTag.hasKey(Byte.toString(i)))
                {
                    IItemHandler inv = instance.getInventoryFromTab(i);

                    if(inv != null && inv instanceof INBTSerializable<?>)
                    {
                        ((INBTSerializable<? super NBTBase>) inv).deserializeNBT(invTag.getTag(Byte.toString(i)));
                    }
                }
            }

            //FTBLib.dev_logger.info("RX bag: " + instance.getTabCount() + ": " + tag);
        }
    };

    @CapabilityInject(IBag.class)
    public static Capability<IBag> BAG = null;

    private static boolean inited = false;

    public static void init()
    {
        if(!inited)
        {
            inited = true;
            CapabilityManager.INSTANCE.register(IBag.class, BAG_STORAGE, () -> new Bag(1));
        }
    }
}