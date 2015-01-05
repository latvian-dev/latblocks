package latmod.latblocks.tile;
import latmod.core.*;
import latmod.core.mod.LC;
import latmod.core.tile.*;
import latmod.latblocks.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileRSCable extends TileLM implements IPaintable
{
	public final Paint[] paint = new Paint[6];
	public boolean hasCover;
	public boolean power;
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		hasCover = tag.getBoolean("HasCover");
		Paint.readFromNBT(tag, "Paint", paint);
		power = tag.getBoolean("Power");
		tick = tag.getLong("Tick");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		tag.setBoolean("HasCover", hasCover);
		Paint.writeToNBT(tag, "Paint", paint);
		tag.setBoolean("Power", power);
		tag.setLong("Tick", tick);
	}
	
	public void onUpdate()
	{
		if(!isServer() && tick % 10 == 0 && power)
			worldObj.spawnParticle("reddust", xCoord + ParticleHelper.rand.nextFloat(), yCoord + ParticleHelper.rand.nextFloat(), zCoord + ParticleHelper.rand.nextFloat(), 0D, 0D, 0D);
	}
	
	public void onNeighborBlockChange()
	{
		if(isServer())
		{
			boolean pP = power;
			power = false;
			
			FastList<TileRSCable> l = new FastList<TileRSCable>();
			addToList(l);
			
			for(int i = 0; i < l.size(); i++)
			{
				TileRSCable t = l.get(i);
				
				if((Math.abs(xCoord - t.xCoord) + Math.abs(yCoord - t.yCoord) + Math.abs(zCoord - t.zCoord)) < LatBlocksConfig.General.redGlowiumCableDist && t.isRSPowered())
				{
					power = true;
					break;
				}
			}
			
			if(pP != power)
			{
				markDirty();
				notifyNeighbors();
			}
		}
		
		//redstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}
	
	public boolean isRSPowered()
	{
		for(int i = 0; i < 6; i++)
		{
			ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[i];
			if(worldObj.getBlock(xCoord + f.offsetX, yCoord + f.offsetY, zCoord + f.offsetZ) != LatBlocksItems.b_rs_cable && worldObj.isBlockProvidingPowerTo(xCoord + f.offsetX, yCoord + f.offsetY, zCoord + f.offsetZ, f.ordinal()) > 0)
			return true;
		}
		
		return false;
	}
	
	public void addToList(FastList<TileRSCable> l)
	{
		for(int i = 0; i < 6; i++)
		{
			ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[i];
			
			TileEntity te = worldObj.getTileEntity(xCoord + f.offsetX, yCoord + f.offsetY, zCoord + f.offsetZ);
			if(te != null && te != this && !te.isInvalid() && te instanceof TileRSCable && !l.contains(te))
			{
				l.add((TileRSCable)te);
				((TileRSCable)te).addToList(l);
			}
		}
	}
	
	public boolean setPaint(PaintData p)
	{
		if(paint == null) return false;
		
		if(p.player.isSneaking())
		{
			for(int i = 0; i < 6; i++)
				paint[i] = p.paint;
			markDirty();
			return true;
		}
		
		if(p.canReplace(paint[p.side]))
		{
			paint[p.side] = p.paint;
			markDirty();
			return true;
		}
		
		return false;
	}
	
	public static boolean connectCable(TileRSCable c, ForgeDirection f)
	{
		Block b = c.worldObj.getBlock(c.xCoord + f.offsetX, c.yCoord + f.offsetY, c.zCoord + f.offsetZ);
		return b != Blocks.air &&
		(b == LatBlocksItems.b_rs_cable
		|| b instanceof BlockRedstoneLight
		|| b.canConnectRedstone(c.worldObj, c.xCoord + f.offsetX, c.yCoord + f.offsetY, c.zCoord + f.offsetZ, f.getOpposite().ordinal()));
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(hasCover)
		{
			if(is != null && is.getItem() instanceof IPaintable.IPainterItem)
				return false;
			
			if(is == null)
			{
				hasCover = false;
				
				if(isServer())
				{
					InvUtils.giveItem(ep, new ItemStack(LatBlocksItems.b_paintable));
					markDirty();
				}
				
				return true;
			}
		}
		else
		{
			if(is != null && is.getItem() == LatBlocksItems.b_paintable.getItem())
			{
				hasCover = true;
				
				if(isServer())
				{
					if(!ep.capabilities.isCreativeMode)
						is.stackSize--;
					
					markDirty();
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAABBEnabled(int i)
	{
		if(i == 0) return true;
		
		ForgeDirection fd = MathHelper.getDir(i - 1);
		if(fd == ForgeDirection.UNKNOWN) return true;
		
		if(worldObj.getBlock(xCoord + fd.offsetX, yCoord + fd.offsetY, zCoord + fd.offsetZ) != Blocks.air) return true;
		
		EntityPlayer clientP = LC.proxy.getClientPlayer();
		
		if(clientP != null && clientP.getHeldItem() != null)
		{
			Item item = clientP.getHeldItem().getItem();
			if(item == LatBlocksItems.b_paintable.getItem()) return true;
		}
		
		return false;
	}
}