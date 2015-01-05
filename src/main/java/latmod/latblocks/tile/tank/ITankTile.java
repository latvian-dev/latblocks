package latmod.latblocks.tile.tank;

import latmod.core.tile.ITileInterface;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.*;

public interface ITankTile extends ITileInterface, IFluidHandler
{
	@SideOnly(Side.CLIENT)
	public IIcon getTankBorderIcon();
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankFluidIcon();
	
	@SideOnly(Side.CLIENT)
	public double getTankFluidHeight();
}