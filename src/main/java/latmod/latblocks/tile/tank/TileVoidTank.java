package latmod.latblocks.tile.tank;

import cpw.mods.fml.relauncher.*;
import ftb.lib.item.LMInvUtils;
import ftb.lib.mod.FTBLibMod;
import latmod.ftbu.tile.Tank;
import latmod.latblocks.LatBlocksItems;
import latmod.lib.MathHelperLM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

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
		if(!isServer() && tick % 20 == 0) for(int i = 0; i < 10; i++)
		{
			double s = 0.25D;
			double x = xCoord + s + MathHelperLM.rand.nextFloat() * (1D - s * 2D);
			double y = yCoord + s + MathHelperLM.rand.nextFloat() * (1D - s * 2D);
			double z = zCoord + s + MathHelperLM.rand.nextFloat() * (1D - s * 2D);
			int[] col = {0xAA000000, 0xAA666666, 0xAA3300FF};
			FTBLibMod.proxy.spawnDust(worldObj, x, y, z, col[MathHelperLM.rand.nextInt(col.length)]);
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(is);
		
		if(liquid != null)
		{
			ep.inventory.setInventorySlotContents(ep.inventory.currentItem, LMInvUtils.reduceItem(is));
			ep.inventory.markDirty();
			markDirty();
			
			return true;
		}
		
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankBorderIcon()
	{ return LatBlocksItems.b_tank_void.getBlockIcon(); }
	
	@SideOnly(Side.CLIENT)
	public Fluid getTankRenderFluid()
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public double getTankFluidHeight()
	{ return 0D; }
	
	public ItemStack getQIconItem()
	{ return new ItemStack(LatBlocksItems.b_tank_void); }
}