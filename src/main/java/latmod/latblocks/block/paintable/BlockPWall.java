package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.*;
import ftb.lib.MathHelperMC;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.config.LatBlocksConfigGeneral;
import latmod.latblocks.tile.*;
import latmod.lib.FastList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPWall extends BlockPaintableSingle
{
	public BlockPWall(String s)
	{
		super(s, 1F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePWall(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 6), "PPP", "PPP",
				'P', LatBlocksItems.b_paintable);
	}
	
	public void addCollisionBoxes(World w, int x, int y, int z, int m, FastList<AxisAlignedBB> boxes, Entity e)
	{
		double p = 1D / 2D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		double x0 = pn;
		double x1 = pp;
		double z0 = pn;
		double z1 = pp;
		
		double d = 0.01D;
		
		if(canConnect(w, x - 1, y, z)) x0 = -d;
		if(canConnect(w, x + 1, y, z)) x1 = 1D + d;
		if(canConnect(w, x, y, z - 1)) z0 = -d;
		if(canConnect(w, x, y, z + 1)) z1 = 1D + d;
		
		double h = 1.5D;
		if(LatBlocksConfigGeneral.fencesIgnorePlayers.get() && e instanceof EntityPlayer) h = 1D;
		boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, h, z1));
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		double p = 1D / 2D;
		double p2 = 1D / 16D * 6D;
		double h2 = 1D - 1D / 16D * 3D;
		
		boxes.add(MathHelperMC.getBox(0.5D, 0D, 0.5D, p, 1D, p));
		boxes.add(MathHelperMC.getBox(0.5D, 0D, 0.5D, p2, h2, 1D));
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		double p = 1D / 2D;
		double p2 = 1D / 16D * 6D;
		double h = 1D - 1D / 16D * 3D;
		
		boolean canConnectZN = canConnect(iba, x, y, z - 1);
		boolean canConnectXN = canConnect(iba, x - 1, y, z);
		boolean canConnectZP = canConnect(iba, x, y, z + 1);
		boolean canConnectXP = canConnect(iba, x + 1, y, z);
		
		if(canConnectZN) boxes.add(MathHelperMC.getBox(0.5D, 0D, 0.25D, p2, h, 0.5D));
		if(canConnectXN) boxes.add(MathHelperMC.getBox(0.25D, 0D, 0.5D, 0.5D, h, p2));
		if(canConnectZP) boxes.add(MathHelperMC.getBox(0.5D, 0D, 0.75D, p2, h, 0.5D));
		if(canConnectXP) boxes.add(MathHelperMC.getBox(0.75D, 0D, 0.5D, 0.5D, h, p2));
		
		if(!((canConnectZN && canConnectZP && !canConnectXN && !canConnectXP) || (!canConnectZN && !canConnectZP && canConnectXN && canConnectXP)) || !iba.isAirBlock(x, y + 1, z))
			boxes.add(MathHelperMC.getBox(0.5D, 0D, 0.5D, p, 1D, p));
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		double p = 1D / 2D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		double x0 = pn;
		double x1 = pp;
		double z0 = pn;
		double z1 = pp;
		
		double d = 0.01D;
		
		boolean canConnectZN = canConnect(iba, x, y, z - 1);
		boolean canConnectXN = canConnect(iba, x - 1, y, z);
		boolean canConnectZP = canConnect(iba, x, y, z + 1);
		boolean canConnectXP = canConnect(iba, x + 1, y, z);
		
		if(canConnectZN) z0 = -d;
		if(canConnectXN) x0 = -d;
		if(canConnectZP) z1 = 1D + d;
		if(canConnectXP) x1 = 1D + d;
		
		double h = 1D - 1D / 16D * 3D;
		
		if(!((canConnectZN && canConnectZP && !canConnectXN && !canConnectXP) || (!canConnectZN && !canConnectZP && canConnectXN && canConnectXP)) || !iba.isAirBlock(x, y + 1, z))
			h = 1D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, h, z1));
	}
	
	public boolean canConnect(IBlockAccess iba, int x, int y, int z)
	{ return LatBlocksItems.b_fence.canConnect(iba, x, y, z); }
	
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{ return side == ForgeDirection.UP || side == ForgeDirection.DOWN; }
	
	public static class TilePWall extends TileSinglePaintable
	{
	}
}