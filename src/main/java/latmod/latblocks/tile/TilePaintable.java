package latmod.latblocks.tile;

import com.feed_the_beast.ftbl.api.FTBLibCapabilities;
import com.feed_the_beast.ftbl.api.paint.PaintStorage;
import com.feed_the_beast.ftbl.api.tile.IInfoTile;
import com.feed_the_beast.ftbl.api.tile.TileInfoDataAccessor;
import com.feed_the_beast.ftbl.api.tile.TileLM;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public abstract class TilePaintable extends TileLM implements IInfoTile, IInfoTile.Stack
{
    public static class Sided extends TilePaintable
    {
        private final Map<EnumFacing, PaintStorage> paint;

        public Sided()
        {
            paint = new EnumMap<>(EnumFacing.class);

            for(EnumFacing f : EnumFacing.VALUES)
            {
                paint.put(f, new PaintStorage());
            }
        }

        @Override
        public void writeTileData(NBTTagCompound tag)
        {
            int[] ai = new int[6];

            for(EnumFacing f : EnumFacing.VALUES)
            {
                ai[f.ordinal()] = paint.get(f).getPaintID();
            }

            tag.setIntArray("Paint", ai);
        }

        @Override
        public void readTileData(NBTTagCompound tag)
        {
            int[] ai = tag.getIntArray("Paint");

            for(EnumFacing f : EnumFacing.VALUES)
            {
                paint.get(f).setFromID(ai[f.ordinal()]);
            }
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing side)
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
        private final PaintStorage paint;

        public Single()
        {
            paint = new PaintStorage();
        }

        @Override
        public void writeTileData(NBTTagCompound tag)
        {
            tag.setInteger("Paint", paint.getPaintID());
        }

        @Override
        public void readTileData(NBTTagCompound tag)
        {
            paint.setFromID(tag.getInteger("Paint"));
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing side)
        {
            if(capability == FTBLibCapabilities.PAINTABLE_TILE)
            {
                return (T) paint;
            }

            return super.getCapability(capability, side);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing side)
    {
        if(capability == FTBLibCapabilities.PAINTABLE_TILE)
        {
            return true;
        }

        return super.hasCapability(capability, side);
    }

    @Override
    public void getInfo(TileInfoDataAccessor info, List<String> list, boolean adv)
    {
        ItemStack is = getInfoItem(info);

        if(is != null)
        {
            list.add(is.getDisplayName());
        }
    }

    @Override
    public ItemStack getInfoItem(TileInfoDataAccessor info)
    {
        IBlockState p = getCapability(FTBLibCapabilities.PAINTABLE_TILE, info.hit.sideHit).getPaint();

        if(p != null)
        {
            return new ItemStack(p.getBlock(), 1, p.getBlock().getMetaFromState(p));
        }

        return null;
    }
}