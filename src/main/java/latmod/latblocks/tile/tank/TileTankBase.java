package latmod.latblocks.tile.tank;

import latmod.ftbu.core.tile.*;
import latmod.latblocks.tile.IQuartzNetTile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import cpw.mods.fml.relauncher.*;

public abstract class TileTankBase extends TileLM implements IFluidHandler, IQuartzNetTile
{
	public Tank tank;
	
	@SideOnly(Side.CLIENT)
	public abstract IIcon getTankBorderIcon();
	
	@SideOnly(Side.CLIENT)
	public abstract Fluid getTankRenderFluid();
	
	@SideOnly(Side.CLIENT)
	public abstract double getTankFluidHeight();
	
	public final int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{ return tank.fill(from, resource, doFill); }
	
	public final FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{ return tank.drain(from, resource, doDrain); }
	
	public final FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{ return tank.drain(from, maxDrain, doDrain); }
	
	public final boolean canFill(ForgeDirection from, Fluid fluid)
	{ return tank.canFill(from, fluid); }
	
	public final boolean canDrain(ForgeDirection from, Fluid fluid)
	{ return tank.canDrain(from, fluid); }
	
	public final FluidTankInfo[] getTankInfo(ForgeDirection from)
	{ return tank.getTankInfo(from); }
	
	public void readTileData(NBTTagCompound tag)
	{
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
	}
	
	public String getQTitle()
	{ return getQIcon().getDisplayName(); }
	
	public void onQClicked(EntityPlayerMP ep, int button)
	{
	}
}