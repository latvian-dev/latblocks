package latmod.latblocks.api;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public class PaintBlockAccess implements IBlockAccess
{
	public final IBlockAccess parent;
	
	public PaintBlockAccess(IBlockAccess w, int x, int y, int z, Paint p)
	{
		parent = w;
	}
	
	public Block getBlock(int x, int y, int z)
	{
		return parent.getBlock(x, y, z);
	}
	
	public TileEntity getTileEntity(int x, int y, int z)
	{
		return parent.getTileEntity(x, y, z);
	}
	
	public int getLightBrightnessForSkyBlocks(int x, int y, int z, int l)
	{
		return parent.getLightBrightnessForSkyBlocks(x, y, z, l);
	}
	
	public int getBlockMetadata(int x, int y, int z)
	{
		return parent.getBlockMetadata(x, y, z);
	}
	
	public int isBlockProvidingPowerTo(int x, int y, int z, int s)
	{
		return parent.isBlockProvidingPowerTo(x, y, z, s);
	}
	
	public boolean isAirBlock(int x, int y, int z)
	{
		return parent.isAirBlock(x, y, z);
	}
	
	public BiomeGenBase getBiomeGenForCoords(int x, int z)
	{
		return parent.getBiomeGenForCoords(x, z);
	}
	
	public int getHeight()
	{
		return parent.getHeight();
	}
	
	public boolean extendedLevelsInChunkCache()
	{
		return parent.extendedLevelsInChunkCache();
	}
	
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default)
	{
		return parent.isSideSolid(x, y, z, side, _default);
	}
}
