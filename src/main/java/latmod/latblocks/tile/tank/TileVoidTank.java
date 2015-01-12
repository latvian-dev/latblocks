package latmod.latblocks.tile.tank;
import latmod.core.tile.Tank;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import cpw.mods.fml.relauncher.*;

public class TileVoidTank extends TileTankBase
{
	public TileVoidTank()
	{
		tank = new Tank("Tank", 1D)
		{
			public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
			{ return (resource == null) ? 0 : resource.amount; }
			
			public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
			{ return null; }
			
			public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
			{ return null; }
			
			public boolean canFill(ForgeDirection from, Fluid fluid)
			{ return true; }
			
			public boolean canDrain(ForgeDirection from, Fluid fluid)
			{ return false; }
		};
	}
	
	public void onUpdate()
	{
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankBorderIcon()
	{ return LatBlocksItems.b_tank_void.getBlockIcon(); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankFluidIcon()
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public double getTankFluidHeight()
	{ return 0D; }
}