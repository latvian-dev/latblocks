package latmod.latblocks.tile;

import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;

public class QNetFinder
{
	private static final FastList<ChunkCoordinates> tempCoordList = new FastList<ChunkCoordinates>();
	
	public static FastList<IQuartzInventory> getTiles(IBlockAccess w, int x, int y, int z, int maxNetSize)
	{
		tempCoordList.clear();
		FastList<IQuartzInventory> l = new FastList<IQuartzInventory>();
		addToList(l, w, new ChunkCoordinates(x, y, z), maxNetSize, true);
		return l;
	}
	
	private static void addToList(FastList<IQuartzInventory> l, IBlockAccess w, ChunkCoordinates c, int maxNetSize, boolean first)
	{
		if(tempCoordList.contains(c)) return;
		tempCoordList.add(c);
		
		if(first)
		{
			TileEntity te = w.getTileEntity(c.posX, c.posY, c.posZ);
			if(te != null && te instanceof IQuartzInventory)
			{
				l.add((IQuartzInventory)te);
				if(l.size() == maxNetSize) return;
			}
			
			for(int i = 0; i < 6; i++)
			{
				int x1 = c.posX + Facing.offsetsXForSide[i];
				int y1 = c.posY + Facing.offsetsYForSide[i];
				int z1 = c.posZ + Facing.offsetsZForSide[i];
				
				Block b = w.getBlock(x1, y1, z1);
				if(b == LatBlocksItems.b_qcable)
					addToList(l, w, new ChunkCoordinates(x1, y1, z1), maxNetSize, false);
			}
			
			return;
		}
		
		Block b = w.getBlock(c.posX, c.posY, c.posZ);
		
		if(b == LatBlocksItems.b_qcable)
		{
			for(int i = 0; i < 6; i++)
			{
				int x1 = c.posX + Facing.offsetsXForSide[i];
				int y1 = c.posY + Facing.offsetsYForSide[i];
				int z1 = c.posZ + Facing.offsetsZForSide[i];
				addToList(l, w, new ChunkCoordinates(x1, y1, z1), maxNetSize, false);
			}
		}
		else
		{
			TileEntity te = w.getTileEntity(c.posX, c.posY, c.posZ);
			if(te != null && te instanceof IQuartzInventory)
			{
				l.add((IQuartzInventory)te);
				if(l.size() == maxNetSize) return;
			}
		}
	}
}