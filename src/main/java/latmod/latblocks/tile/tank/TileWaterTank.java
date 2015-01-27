package latmod.latblocks.tile.tank;
import latmod.core.InvUtils;
import latmod.core.tile.Tank;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import cpw.mods.fml.relauncher.*;

public class TileWaterTank extends TileTankBase
{
	public TileWaterTank()
	{
		tank = new Tank("Tank", 1D)
		{
			public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
			{ return 0; }
			
			public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
			{ return resource.copy(); }
			
			public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
			{ return new FluidStack(FluidRegistry.WATER, maxDrain); }
			
			public boolean canFill(ForgeDirection from, Fluid fluid)
			{ return false; }
			
			public boolean canDrain(ForgeDirection from, Fluid fluid)
			{ return true; }
		};
		
		tank.fluidTank.setFluid(new FluidStack(FluidRegistry.WATER, tank.getCapacity()));
	}
	
	public void onUpdate()
	{
		if(!worldObj.isRemote && tick % 10 == 0 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1)
		{
			for(int i = 0; i < 6; i++)
			{
				ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
				
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				
				if(te != null && !te.isInvalid() && te instanceof IFluidHandler)
				{
					IFluidHandler h = (IFluidHandler)te;
					
					if(h.canFill(dir.getOpposite(), FluidRegistry.WATER))
						h.fill(dir.getOpposite(), new FluidStack(FluidRegistry.WATER, 1000), true);
				}
			}
		}
	}
	
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(is == null && ep.isSneaking())
		{
			if(!worldObj.isRemote)
			{
				getMeta();
				setMeta(1 - blockMetadata);
				markDirty();
			}
			
			return true;
		}
		
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(is);
		
		if (liquid == null)
		{
			FluidStack available = new FluidStack(FluidRegistry.WATER, 1000);
			
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
		
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankBorderIcon()
	{ return LatBlocksItems.b_tank_water.getTankItemBorderIcon(blockMetadata); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankFluidIcon()
	{ return Blocks.water.getBlockTextureFromSide(1); }
	
	@SideOnly(Side.CLIENT)
	public double getTankFluidHeight()
	{ return 1D; }
}