package latmod.latblocks.tile;

import java.util.List;

import latmod.core.InvUtils;
import latmod.core.tile.*;
import latmod.latblocks.LatBlocks;
import mcp.mobius.waila.api.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileFountain extends TileInvLM implements IPaintable, IFluidHandler, ISidedInventory, IWailaTile.Body
{
	public final Paint[] paint = new Paint[1];
	public final Tank tank;
	
	private Integer prevFluidAmmount = null;
	
	public TileFountain()
	{
		super(1);
		
		tank = new Tank("Tank", 1D)
		{
			public boolean canFill(ForgeDirection from, Fluid fluid)
			{ return fluid != null && fluid.getBlock() != null; }
		};
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
	
	public void onNeighborBlockChange(Block b)
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
			LatBlocks.proxy.spawnFountainParticle(this);
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
		if(is == null)
		{
			redstonePowered = !redstonePowered;
			markDirty();
			return true;
		}
		
		if(is.getItem() instanceof IPaintable.IPainterItem) return false;
		
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
	
	public String getInventoryName()
	{ return "Fountain"; }
	
	public boolean hasCustomInventoryName()
	{ return false; }
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return true; }
	
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