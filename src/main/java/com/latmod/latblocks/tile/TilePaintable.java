package com.latmod.latblocks.tile;

import com.feed_the_beast.ftbl.api.FTBLibCapabilities;
import com.feed_the_beast.ftbl.api.paint.Paintable;
import com.feed_the_beast.ftbl.api.tile.TileLM;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public abstract class TilePaintable extends TileLM
{
    public static class Sided extends TilePaintable
    {
        private final Map<EnumFacing, Paintable> paint;

        public Sided()
        {
            paint = new EnumMap<>(EnumFacing.class);

            for(EnumFacing f : EnumFacing.VALUES)
            {
                paint.put(f, new Paintable());
            }
        }

        @Override
        public void writeTileData(@Nonnull NBTTagCompound tag)
        {
            int[] ai = new int[6];

            for(EnumFacing f : EnumFacing.VALUES)
            {
                ai[f.ordinal()] = paint.get(f).getPaintID();
            }

            tag.setIntArray("Paint", ai);
        }

        @Override
        public void readTileData(@Nonnull NBTTagCompound tag)
        {
            int[] ai = tag.getIntArray("Paint");

            for(EnumFacing f : EnumFacing.VALUES)
            {
                paint.get(f).setFromID(ai[f.ordinal()]);
            }
        }

        @Nonnull
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing side)
        {
            if(capability == FTBLibCapabilities.PAINTABLE_TILE)
            {
                return (T) paint.get(side);
            }

            return super.getCapability(capability, side);
        }
    }

    public static class Single extends TilePaintable
    {
        private final Paintable paint;

        public Single()
        {
            paint = new Paintable();
        }

        @Override
        public void writeTileData(@Nonnull NBTTagCompound tag)
        {
            tag.setInteger("Paint", paint.getPaintID());
        }

        @Override
        public void readTileData(@Nonnull NBTTagCompound tag)
        {
            paint.setFromID(tag.getInteger("Paint"));
        }

        @Nonnull
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing side)
        {
            if(capability == FTBLibCapabilities.PAINTABLE_TILE)
            {
                return (T) paint;
            }

            return super.getCapability(capability, side);
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing side)
    {
        if(capability == FTBLibCapabilities.PAINTABLE_TILE)
        {
            return true;
        }

        return super.hasCapability(capability, side);
    }
}