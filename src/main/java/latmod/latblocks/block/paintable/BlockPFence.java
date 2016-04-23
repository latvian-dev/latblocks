package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.MathHelperMC;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.config.LatBlocksConfigGeneral;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TilePaintableLB;
import latmod.latblocks.tile.TileSinglePaintable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockPFence extends BlockPaintableSingle
{
	public BlockPFence(String s)
	{
		super(s, 1F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePFence(); }
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 2), "SSS", "SSS", 'S', ItemMaterialsLB.ROD);
	}
	
	@Override
	public void addCollisionBoxes(World w, int x, int y, int z, int m, List<AxisAlignedBB> boxes, Entity e)
	{
		double p = 1D / 4D;
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
		if(LatBlocksConfigGeneral.fencesIgnorePlayers.getAsBoolean() && e instanceof EntityPlayer) h = 1D;
		boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, h, z1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(List<AxisAlignedBB> boxes)
	{
		double p = 1F / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		double ds = 1D / 16D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(ds, 0D, pn, ds + p, 1D, pp));
		boxes.add(AxisAlignedBB.getBoundingBox(1D - (ds + p), 0D, pn, 1D - ds, 1D, pp));
		
		double h1n = 1D / 8D * 2D;
		double h1p = 1D / 8D * 3D;
		double h2n = 1D / 8D * 5D;
		double h2p = 1D / 8D * 6D;
		
		double dd = 1D / 8D;
		double z0 = 0.5D - dd / 2D;
		double z1 = 0.5D + dd / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, h1n, z0, 1D, h1p, z1));
		boxes.add(AxisAlignedBB.getBoundingBox(0D, h2n, z0, 1D, h2p, z1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawHighlight(List<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(List<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		double p = 1F / 4D;
		
		double h1n = 1D / 8D * 2D;
		double h1p = 1D / 8D * 3D;
		double h2n = 1D / 8D * 5D;
		double h2p = 1D / 8D * 6D;
		
		boxes.add(MathHelperMC.getBox(0.5D, 0D, 0.5D, p, 1D, p));
		
		if(canConnect(iba, x, y, z - 1))
		{
			boxes.add(MathHelperMC.getBox(0.5D, h1n, 0.25D, 0.125D, h1p, 0.5D));
			boxes.add(MathHelperMC.getBox(0.5D, h2n, 0.25D, 0.125D, h2p, 0.5D));
		}
		
		if(canConnect(iba, x - 1, y, z))
		{
			boxes.add(MathHelperMC.getBox(0.25D, h1n, 0.5D, 0.5D, h1p, 0.125D));
			boxes.add(MathHelperMC.getBox(0.25D, h2n, 0.5D, 0.5D, h2p, 0.125D));
		}
		
		if(canConnect(iba, x, y, z + 1))
		{
			boxes.add(MathHelperMC.getBox(0.5D, h1n, 0.75D, 0.125D, h1p, 0.5D));
			boxes.add(MathHelperMC.getBox(0.5D, h2n, 0.75D, 0.125D, h2p, 0.5D));
		}
		
		if(canConnect(iba, x + 1, y, z))
		{
			boxes.add(MathHelperMC.getBox(0.75D, h1n, 0.5D, 0.5D, h1p, 0.125D));
			boxes.add(MathHelperMC.getBox(0.75D, h2n, 0.5D, 0.5D, h2p, 0.125D));
		}
	}
	
	@Override
	public void addBoxes(List<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		double p = 1D / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		double x0 = pn;
		double x1 = pp;
		double z0 = pn;
		double z1 = pp;
		
		double d = 0.01D;
		
		if(canConnect(iba, x - 1, y, z)) x0 = -d;
		if(canConnect(iba, x + 1, y, z)) x1 = 1D + d;
		if(canConnect(iba, x, y, z - 1)) z0 = -d;
		if(canConnect(iba, x, y, z + 1)) z1 = 1D + d;
		
		boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, 1D, z1));
	}
	
	public boolean canConnect(IBlockAccess iba, int x, int y, int z)
	{
		Block b = iba.getBlock(x, y, z);
		return b == this || b == Blocks.fence_gate || b == LatBlocksItems.b_fence_gate || (b.getMaterial().isOpaque());
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{ return side == ForgeDirection.UP || side == ForgeDirection.DOWN; }
	
	public static class TilePFence extends TileSinglePaintable
	{ }
}