package latmod.latblocks.tile.tank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.item.LMInvUtils;
import ftb.lib.api.tile.Tank;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class TileWaterTank extends TileTankBase
{
	public static final FluidStack WATER_1000 = new FluidStack(FluidRegistry.WATER, 1000);
	
	public TileWaterTank()
	{
		tank = new Tank("Tank", 1D)
		{
			@Override
			public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
			{ return 0; }
			
			@Override
			public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
			{ return resource.copy(); }
			
			@Override
			public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
			{ return new FluidStack(FluidRegistry.WATER, maxDrain); }
			
			@Override
			public boolean canFill(ForgeDirection from, Fluid fluid)
			{ return false; }
			
			@Override
			public boolean canDrain(ForgeDirection from, Fluid fluid)
			{ return true; }
		};
		
		tank.fluidTank.setFluid(new FluidStack(FluidRegistry.WATER, tank.getCapacity()));
	}
	
	@Override
	public void onUpdate()
	{
		if(isServer() && tick % 10 == 0 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1)
		{
			for(int i = 0; i < 6; i++)
			{
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
				
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				
				if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
				{
					IFluidHandler h = (IFluidHandler) te;
					
					if(h.canFill(dir.getOpposite(), FluidRegistry.WATER))
						h.fill(dir.getOpposite(), new FluidStack(FluidRegistry.WATER, 1000), true);
				}
			}
			
			if(tank.getAmount() != tank.getCapacity())
			{
				tank.fluidTank.setFluid(new FluidStack(FluidRegistry.WATER, tank.getCapacity()));
				markDirty();
			}
		}
	}
	
	
	@Override
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(is == null && ep.isSneaking())
		{
			if(isServer())
			{
				getMeta();
				setMeta((blockMetadata == 0) ? 1 : 0);
				markDirty();
			}
			
			return true;
		}
		
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(is);
		
		if(liquid == null)
		{
			FluidStack available = new FluidStack(FluidRegistry.WATER, 1000);
			
			ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, is);
			
			liquid = FluidContainerRegistry.getFluidForFilledItem(filled);
			
			if(liquid != null)
			{
				if(!ep.capabilities.isCreativeMode)
				{
					if(is.stackSize > 1)
					{
						if(!ep.inventory.addItemStackToInventory(filled)) return false;
						else
						{
							ep.inventory.setInventorySlotContents(ep.inventory.currentItem, LMInvUtils.reduceItem(is));
							ep.inventory.markDirty();
							markDirty();
						}
					}
					else
					{
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, LMInvUtils.reduceItem(is));
						ep.inventory.setInventorySlotContents(ep.inventory.currentItem, filled);
						ep.inventory.markDirty();
						markDirty();
					}
				}
				
				drain(ForgeDirection.UNKNOWN, liquid.amount, true);
				
				return true;
			}
		}
		
		return !(is == null || is.getItem() instanceof ItemBlock);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTankBorderIcon()
	{ return blockMetadata == 1 ? LatBlocksItems.b_tank_water.icon_on : LatBlocksItems.b_tank_water.getBlockIcon(); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public Fluid getTankRenderFluid()
	{ return FluidRegistry.WATER; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getTankFluidHeight()
	{ return 1D; }
	
	public ItemStack getQIconItem()
	{ return new ItemStack(LatBlocksItems.b_tank_water); }
}