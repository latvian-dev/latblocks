package latmod.latblocks.tile;

import com.feed_the_beast.ftbl.api.FTBLibCapabilities;
import com.feed_the_beast.ftbl.api.paint.IPaintable;
import com.feed_the_beast.ftbl.api.paint.SidedPaintStorage;
import com.feed_the_beast.ftbl.api.paint.SinglePaintStorage;
import com.feed_the_beast.ftbl.api.tile.IWailaTile;
import com.feed_the_beast.ftbl.api.tile.TileLM;
import com.feed_the_beast.ftbl.api.waila.WailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public abstract class TilePaintable extends TileLM implements IWailaTile.Stack
{
    public static class Sided extends TilePaintable
    {
        public Sided()
        {
            super(new SidedPaintStorage());
        }
        
        @Override
        public void writeTileData(NBTTagCompound tag)
        {
            int[] ai = new int[6];
            
            for(EnumFacing f : EnumFacing.VALUES)
            {
                IBlockState p = paintable.getPaint(f);
                ai[f.ordinal()] = p == null ? 0 : Block.getStateId(p);
            }
            
            tag.setIntArray("Paint", ai);
        }
        
        @Override
        public void readTileData(NBTTagCompound tag)
        {
            int[] ai = tag.getIntArray("Paint");
            
            for(EnumFacing f : EnumFacing.VALUES)
            {
                int i = ai[f.ordinal()];
                paintable.setPaint(f, i == 0 ? null : Block.getStateById(i));
            }
        }
    }
    
    public static class Single extends TilePaintable
    {
        public Single()
        {
            super(new SinglePaintStorage());
        }
        
        @Override
        public void writeTileData(NBTTagCompound tag)
        {
            IBlockState p = paintable.getPaint(EnumFacing.UP);
            tag.setInteger("Paint", p == null ? 0 : Block.getStateId(p));
        }
        
        @Override
        public void readTileData(NBTTagCompound tag)
        {
            int p = tag.getInteger("Paint");
            paintable.setPaint(EnumFacing.UP, p == 0 ? null : Block.getStateById(p));
        }
    }
    
    public final IPaintable paintable;
    
    public TilePaintable(IPaintable p)
    {
        paintable = p;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing side)
    {
        if(capability == FTBLibCapabilities.PAINTABLE_TILE_CAPABILITY)
        {
            return true;
        }
        
        return super.hasCapability(capability, side);
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side)
    {
        if(capability == FTBLibCapabilities.PAINTABLE_TILE_CAPABILITY)
        {
            return (T) paintable;
        }
        
        return super.getCapability(capability, side);
    }
    
    @Override
    public ItemStack getWailaStack(WailaDataAccessor data)
    {
        IBlockState p = paintable.getPaint(data.side);
        
        if(p != null)
        {
            return new ItemStack(p.getBlock(), 1, p.getBlock().getMetaFromState(p));
        }
        
        return null;
    }
}