package latmod.latblocks.tile;

import java.util.List;

import latmod.core.EnumDyeColor;
import mcp.mobius.waila.api.*;
import net.minecraft.nbt.NBTTagCompound;

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
	
	public boolean setPaint(PaintData p)
	{
		if(p.paint != null && (!p.paint.block.isOpaqueCube() || !p.paint.block.renderAsNormalBlock()))
			return false; return super.setPaint(p);
	}
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
		super.addWailaBody(data, config, info);
		
		if(blockMetadata >= 0 && blockMetadata < 16)
			info.add(EnumDyeColor.VALUES[blockMetadata].toString());
	}
}