package com.latmod.latblocks.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class LBCapabilities
{
    public static final Capability.IStorage<Bag> BAG_STORAGE = new Capability.IStorage<Bag>()
    {
        @Override
        public NBTBase writeNBT(Capability<Bag> capability, Bag instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<Bag> capability, Bag instance, EnumFacing side, NBTBase nbt)
        {
            instance.deserializeNBT((NBTTagCompound) nbt);
        }
    };

    @CapabilityInject(Bag.class)
    public static Capability<Bag> BAG = null;

    private static boolean enabled = false;

    public static void enable()
    {
        if(!enabled)
        {
            enabled = true;
            CapabilityManager.INSTANCE.register(Bag.class, BAG_STORAGE, Bag::new);
        }
    }
}