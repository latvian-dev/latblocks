package latmod.latblocks.tile;

import latmod.core.util.*;
import net.minecraft.nbt.NBTTagCompound;

public class TileNoteBoard extends TileSinglePaintable
{
	public final FastList<FastList<String>> notes = new FastList<FastList<String>>();
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
	}
}