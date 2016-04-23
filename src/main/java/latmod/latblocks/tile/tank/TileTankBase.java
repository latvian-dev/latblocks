package latmod.latblocks.tile.tank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.tile.Tank;
import ftb.lib.api.tile.TileLM;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileTankBase extends TileLM implements IFluidHandler//FIXME, IQuartzNetTile
{
	public Tank tank;
	
	@SideOnly(Side.CLIENT)
	public abstract IIcon getTankBorderIcon();
	
	@SideOnly(Side.CLIENT)
	public abstract Fluid getTankRenderFluid();
	
	@SideOnly(Side.CLIENT)
	public abstract double getTankFluidHeight();
	
	@Override
	public final int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{ return tank.fill(from, resource, doFill); }
	
	@Override
	public final FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{ return tank.drain(from, resource, doDrain); }
	
	@Override
	public final FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{ return tank.drain(from, maxDrain, doDrain); }
	
	@Override
	public final boolean canFill(ForgeDirection from, Fluid fluid)
	{ return tank.canFill(from, fluid); }
	
	@Override
	public final boolean canDrain(ForgeDirection from, Fluid fluid)
	{ return tank.canDrain(from, fluid); }
	
	@Override
	public final FluidTankInfo[] getTankInfo(ForgeDirection from)
	{ return tank.getTankInfo(from); }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
	}
	
	public void onQClicked(EntityPlayer ep, int button)
	{
	}
}