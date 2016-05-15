package latmod.latblocks.tile;

import com.feed_the_beast.ftbl.FTBLibCapabilities;
import com.feed_the_beast.ftbl.api.paint.IPaintable;
import com.feed_the_beast.ftbl.api.paint.SidedPaintStorage;
import com.feed_the_beast.ftbl.api.paint.SinglePaintStorage;
import com.feed_the_beast.ftbl.api.tile.IWailaTile;
import com.feed_the_beast.ftbl.api.tile.TileLM;
import com.feed_the_beast.ftbl.api.waila.WailaDataAccessor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class TilePaintable extends TileLM implements IWailaTile.Stack, IWailaTile.Body
{
	public IPaintable paintable;
	
	public TilePaintable()
	{
		paintable = new SinglePaintStorage();
	}
	
	public TilePaintable(boolean singlePaint)
	{
		paintable = singlePaint ? new SinglePaintStorage() : new SidedPaintStorage();
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
	
	@Override
	public void addWailaBody(WailaDataAccessor data, List<String> info)
	{
		ItemStack is = getWailaStack(data);
		
		if(is != null)
		{
			info.add(is.getDisplayName());
		}
	}
}