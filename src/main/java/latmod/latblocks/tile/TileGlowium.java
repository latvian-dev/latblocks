package latmod.latblocks.tile;

import java.util.List;

import latmod.ftbu.api.paint.Paint;
import latmod.ftbu.util.EnumDyeColor;
import latmod.ftbu.waila.WailaDataAccessor;
import latmod.latblocks.item.ItemGlasses;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.IFluidBlock;

public class TileGlowium extends TileSidedPaintable
{
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
	}
	
	public boolean isPaintValid(int side, Paint p)
	{ return p == null || (p.block instanceof IFluidBlock || p.block instanceof BlockLiquid || (p.block.isOpaqueCube() && p.block.renderAsNormalBlock())); }
	
	public void addWailaBody(WailaDataAccessor data, List<String> info)
	{
		if(blockMetadata < 0 && blockMetadata >= 16) return;
		
		boolean hasGlasses = ItemGlasses.hasPlayer(data.player);
		
		if(hasGlasses && paint[data.side] != null)
			info.add((new ItemStack(getBlockType(), 1, getBlockMetadata())).getDisplayName() + " [" + EnumDyeColor.VALUES[blockMetadata].toString() + "]");
		else if(hasGlasses)
			info.add(EnumDyeColor.VALUES[blockMetadata].toString());
	}
}