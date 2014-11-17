package latmod.latblocks.tile;

import latmod.core.*;
import latmod.core.tile.*;
import latmod.core.util.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileFountain extends TileLM implements IPaintable, IFluidHandler, ISidedInventory
{
	private static final int[] ALL_SLOTS = new int[] { 0 };
	
	public final Paint[] paint = new Paint[1];
	public final Tank tank;
	
	private Integer prevFluidAmmount = null;
	
	public TileFountain()
	{
		tank = new Tank("Tank", 4D)
		{
			public boolean canFill(ForgeDirection from, Fluid fluid)
			{ return true; }
		};
		
		items = new ItemStack[1];
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		tank.readFromNBT(tag);
		Paint.readFromNBT(tag, "Texture", paint);
		redstonePowered = tag.getBoolean("RSIn");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tank.writeToNBT(tag);
		Paint.writeToNBT(tag, "Texture", paint);
		tag.setBoolean("RSIn", redstonePowered);
	}
	
	public boolean setPaint(PaintData p)
	{
		if(p.canReplace(paint[0]))
		{
			paint[0] = p.paint;
			markDirty();
			return true;
		}
		
		return false;
	}
	
	public void onNeighborBlockChange()
	{
		if(isServer())
		{
			redstonePowered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
			markDirty();
		}
	}
	
	public void onUpdate()
	{
		if(isServer() && tick % 20 == 0)
		{
			if(prevFluidAmmount == null || prevFluidAmmount != tank.getAmount())
			{
				prevFluidAmmount = tank.getAmount();
				markDirty();
			}
			
			if(tank.getAmount() >= 1000 && items[0] != null && LatCoreMC.isBucket(items[0]))
			{
			}
		}
		
		if(redstonePowered && tank.hasFluid() && tank.getFluid().getBlock() != null)
		{
			double mxz = 0.15D;
			double my = 0.4D;
			String s = "blockdust_" + Item.getIdFromItem(Item.getItemFromBlock(tank.getFluid().getBlock())) + "_" + 0;
			
			int c = 12;
			double t = tick * 3D;
			
			for(int i = 0; i < c * 4; i++)
			{
				double mx = MathHelper.sinFromDeg(i * 360D / (double)c + t) * mxz;
				double mz = MathHelper.cosFromDeg(i * 360D / (double)c + t) * mxz;
				
				worldObj.spawnParticle(s, xCoord + 0.5D, yCoord + 0.7D + MathHelper.rand.nextFloat() * 0.3D, zCoord + 0.5D, mx, my, mz);
			}
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(is != null && is.getItem() instanceof IPaintable.IPainterItem) return false;
		
		if(is != null && tank.getAmount() >= 1000 && LatCoreMC.isBucket(is))
		{
			is.stackSize--;
			tank.drain(ForgeDirection.UNKNOWN, 1000, true);
			InvUtils.giveItem(ep, new ItemStack(Blocks.dirt), ep.inventory.currentItem);
		}
		
		return true;
	}
	
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{ return tank.fill(from, resource, doFill); }
	
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{ return tank.drain(from, resource, doDrain); }
	
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{ return tank.drain(from, maxDrain, doDrain); }
	
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{ return tank.canFill(from, fluid); }
	
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{ return tank.canDrain(from, fluid); }
	
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{ return tank.getTankInfo(from); }
	
	public int getInventoryStackLimit()
	{ return 1; }

	public int[] getAccessibleSlotsFromSide(int i)
	{ return ALL_SLOTS; }
	
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return LatCoreMC.isBucket(is); }
	
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return !LatCoreMC.isBucket(is); }
}