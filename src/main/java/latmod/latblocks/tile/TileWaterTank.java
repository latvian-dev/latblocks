package latmod.latblocks.tile;
import latmod.core.tile.TileLM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileWaterTank extends TileLM implements IFluidHandler
{
	public FluidTank tank;
	
	public TileWaterTank()
	{
		tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME);
		tank.setFluid(new FluidStack(FluidRegistry.WATER, tank.getCapacity()));
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
		
		if(is != null)
		{
			if(is.getItem() == Items.bucket)
			{
				if(ep.inventory.addItemStackToInventory(new ItemStack(Items.water_bucket)))
				is.stackSize--;
				return true;
			}
		}
		
		return false;
	}
	
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
	
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{ return new FluidTankInfo[] { tank.getInfo() }; }
}