package latmod.latblocks.tile.tank;

import java.util.List;

import latmod.core.InvUtils;
import latmod.core.tile.*;
import latmod.latblocks.LatBlocksItems;
import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import cpw.mods.fml.relauncher.*;

public class TileTank extends TileTankBase implements IWailaTile.Body
{
	private Integer prevFluidAmmount = null;
	
	public TileTank()
	{
		tank = new Tank("Tank", 0D);
	}
	
	public TileTank(int meta)
	{
		this();
		if(meta == 0) tank.setCapacity(1D);
		else if(meta == 1) tank.setCapacity(8D);
		else if(meta == 2) tank.setCapacity(64D);
		else if(meta == 3) tank.setCapacity(512D);
		else if(meta == 4) tank.setCapacity(4096D);
		else if(meta == 5) tank.setCapacity(Integer.MAX_VALUE / 1000D);
		blockMetadata = meta;
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		tick = tag.getLong("Tick");
		tank.readFromNBT(tag);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		tag.setLong("Tick", tick);
		tank.writeToNBT(tag);
	}
	
	public void onNeighborBlockChange()
	{
	}
	
	public void onUpdate()
	{
		if(isServer() && tick % 5 == 0 && (prevFluidAmmount == null || prevFluidAmmount != tank.getAmount()))
		{
			prevFluidAmmount = tank.getAmount();
			markDirty();
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
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
		if(tank.isEmpty()) info.add("Tank: Empty");
		else info.add("Tank: " + tank.getAmount() + " mB of " + tank.getFluidStack().getLocalizedName() + (blockMetadata == 5 ? "" : (" [ " + ((int)(tank.getAmountD() * 100D)) + "% ]")));
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankBorderIcon()
	{ getMeta(); return LatBlocksItems.b_tank.icons[blockMetadata]; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankFluidIcon()
	{ return tank.isEmpty() ? null : tank.getFluid().getStillIcon(); }
	
	@SideOnly(Side.CLIENT)
	public double getTankFluidHeight()
	{
		if(blockMetadata == 5 && tank.hasFluid()) return 1D;
		return tank.getAmountD();
	}
}