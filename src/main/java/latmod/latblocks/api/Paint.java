package latmod.latblocks.api;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public class Paint implements Cloneable
{
	public final Block block;
	public final int meta;
	
	public Paint(Block b, int m)
	{
		block = b;
		meta = m;
	}
	
	public static void readFromNBT(NBTTagCompound tag, String texture, Paint[] paint)
	{
	}
	
	public static void writeToNBT(NBTTagCompound tag, String texture, Paint[] paint)
	{
	}
	
	public Paint clone()
	{ return new Paint(block, meta); }
}