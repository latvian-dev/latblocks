package latmod.latblocks.tile;

import ftb.lib.EnumMCColor;
import ftb.lib.api.waila.WailaDataAccessor;
import latmod.latblocks.api.Paint;
import latmod.latblocks.item.ItemGlasses;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.List;

public class TileGlowium extends TileSidedPaintable
{
	@Override
	public boolean rerenderBlock()
	{ return true; }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
	}
	
	@Override
	public boolean isPaintValid(int side, Paint p)
	{ return p == null || (p.block instanceof IFluidBlock || p.block instanceof BlockLiquid || (p.block.isOpaqueCube() && p.block.renderAsNormalBlock())); }
	
	@Override
	public void addWailaBody(WailaDataAccessor data, List<String> info)
	{
		if(blockMetadata < 0 && blockMetadata >= 16) return;
		
		boolean hasGlasses = ItemGlasses.hasPlayer(data.player);
		
		if(hasGlasses && paint[data.side] != null)
			info.add((new ItemStack(getBlockType(), 1, getBlockMetadata())).getDisplayName() + " [" + EnumMCColor.VALUES[blockMetadata].toString() + "]");
		else if(hasGlasses) info.add(EnumMCColor.VALUES[blockMetadata].toString());
	}
}