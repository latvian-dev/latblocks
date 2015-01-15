package latmod.latblocks.tile;

import java.util.List;

import latmod.core.*;
import latmod.core.tile.*;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileFountain extends TileLM implements IPaintable, IFluidHandler, ISidedInventory, IWailaTile.Body
{
	private static final int[] ALL_SLOTS = new int[] { 0 };
	
	public final Paint[] paint = new Paint[1];
	public final Tank tank;
	
	private Integer prevFluidAmmount = null;
	
	public TileFountain()
	{
		tank = new Tank("Tank", 1D)
		{
			public boolean canFill(ForgeDirection from, Fluid fluid)
			{ return fluid != null && fluid.getBlock() != null; }
		};
		
		items = new ItemStack[ALL_SLOTS.length];
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
			
			if(items[0] != null && items[0].stackSize == 1)
			{
				ItemStack is1 = getFilled(items[0]);
				
				if(is1 != null)
				{
					items[0] = is1;
					tank.drain(ForgeDirection.UNKNOWN, 1000, true);
				}
			}
		}
		
		if(!isServer() && redstonePowered && tank.hasFluid() && tank.getFluid().getBlock() != null)
		{
			double mv = MathHelperLM.sin(tick * 0.1D);
			
			double mxz = MathHelperLM.map(mv, -1D, 1D, 0.15D, 0.1D);
			double my = MathHelperLM.map(mv, -1D, 1D, 0.4D, 0.5D);
			String s = "blockdust_" + Item.getIdFromItem(Item.getItemFromBlock(tank.getFluid().getBlock())) + "_" + 0;
			
			int c = 12;
			double t = tick * 3D;
			
			for(int i = 0; i < c * 3; i++)
			{
				double mx = MathHelperLM.sinFromDeg(i * 360D / (double)c + t) * mxz;
				double mz = MathHelperLM.cosFromDeg(i * 360D / (double)c + t) * mxz;
				
				worldObj.spawnParticle(s, xCoord + 0.5D, yCoord + 0.7D + MathHelperLM.rand.nextFloat() * 0.3D, zCoord + 0.5D, mx, my, mz);
			}
		}
	}
	
	public ItemStack getFilled(ItemStack is)
	{
		if(tank.getAmount() < 1000) return null;
		if(is == null || is.getItem() == null) return null;
		ItemStack is1 = InvUtils.singleCopy(is);
		FluidStack fs = new FluidStack(tank.getFluid(), 1000);
		
		if(is1.getItem() instanceof IFluidContainerItem)
		{
			IFluidContainerItem i = (IFluidContainerItem)is1.getItem();
			int f = i.fill(is1, fs, true);
			if(f == 1000) return is1;
		}
		
		return FluidContainerRegistry.fillFluidContainer(fs, is1);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(is == null || is.getItem() instanceof IPaintable.IPainterItem) return false;
		
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(is);
		
		if (liquid != null)
		{
			int qty = fill(ForgeDirection.UNKNOWN, liquid, true);
			
			if (qty != 0 && !ep.capabilities.isCreativeMode)
			{
				ep.inventory.setInventorySlotContents(ep.inventory.currentItem, InvUtils.reduceItem(is));
				ep.inventory.markDirty();
				markDirty();
			}
			
			return true;
		}
		else
		{
			FluidStack available = tank.getFluidStack();
			
			if (available != null)
			{
				ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, is);
				
				liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
				
				if (liquid != null)
				{
					if (!ep.capabilities.isCreativeMode)
					{
						if (is.stackSize > 1)
						{
							if (!ep.inventory.addItemStackToInventory(filled))
								return false;
							else
							{
								ep.inventory.setInventorySlotContents(ep.inventory.currentItem, InvUtils.reduceItem(is));
								ep.inventory.markDirty();
								markDirty();
							}
						}
						else
						{
							ep.inventory.setInventorySlotContents(ep.inventory.currentItem, InvUtils.reduceItem(is));
							ep.inventory.setInventorySlotContents(ep.inventory.currentItem, filled);
							ep.inventory.markDirty();
							markDirty();
						}
					}
					
					drain(ForgeDirection.UNKNOWN, liquid.amount, true);
					
					return true;
				}
			}
		}
		
		return false;
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
	{ return getFilled(is) != null; }
	
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return getFilled(is) == null; }

	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
		if(tank.isEmpty()) info.add("Tank: Empty");
		else info.add("Tank: " + tank.getAmount() + " mB of " + tank.getFluidStack().getLocalizedName());
	}
}