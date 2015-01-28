package latmod.latblocks.tile;

import latmod.latblocks.block.paintable.BlockPaintableLamp;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TilePaintableRS extends TileSidedPaintable
{
	public final Paint[] paint_on = new Paint[6];
	public int power = 0;
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		Paint.readFromNBT(tag, "TexturesOn", paint_on);
		power = tag.getByte("Power");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		Paint.writeToNBT(tag, "TexturesOn", paint_on);
		tag.setByte("Power", (byte)power);
	}
	
	public void onUpdate()
	{
	}
	
	public void onNeighborBlockChange()
	{
		if(isServer())
		{
			int pP = power;
			
			power = 0;
			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
				power = 32;
			else
			{
				int b = 0;
				
				for(int i = 0; i < 6; i++)
				{
					ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[i];
					TileEntity te = worldObj.getTileEntity(xCoord + f.offsetX, yCoord + f.offsetY, zCoord + f.offsetZ);
					if(te != null && !te.isInvalid() && (te instanceof TilePaintableRS || te instanceof BlockPaintableLamp.TilePaintableLamp))
						b = Math.max(b, ((TilePaintableRS)te).power);
				}
				
				if(b > 0) power = b - 1;
				if(power > 32) power = 32;
			}
			
			if(power != pP)
			{
				markDirty();
				notifyNeighbors();
			}
		}
	}
	
	public Paint[] getPaint()
	{ return (power > 0) ? paint_on : paint; }
	
	public int iconMeta()
	{ return (power > 0) ? 1 : 0; }
}