package latmod.latblocks.block.paintable;

import java.util.List;

import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.core.util.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.paintable.TilePFence;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public class BlockPFence extends BlockPaintableSingle
{
	public BlockPFence(String s)
	{
		super(s, Material.wood, 1F);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePFence(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 4), "SPS", "SPS",
				'P', LatBlocksItems.b_paintable,
				'S', ODItems.STICK);
	}
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		double p = 1D / 8D * 4D;
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
		
		AxisAlignedBB bb1 = AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, 1.5D, z1).getOffsetBoundingBox(x, y, z);
		if(bb.intersectsWith(bb1)) l.add(bb1);
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		double p = 1F / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, pn, pp, 1D, pp));
		//boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, pn + 0.5D, pp, 1D, pp));
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		double p = 1F / 4D;
		
		double h1n = 1D / 8D * 2D;
		double h1p = 1D / 8D * 3D;
		double h2n = 1D / 8D * 5D;
		double h2p = 1D / 8D * 6D;
		
		boxes.add(MathHelper.getBox(0.5D, 0D, 0.5D, p, 1D, p));
		
		if(canConnect(iba, x, y, z - 1))
		{
			boxes.add(MathHelper.getBox(0.5D, h1n, 0.25D, 0.125D, h1p, 0.5D));
			boxes.add(MathHelper.getBox(0.5D, h2n, 0.25D, 0.125D, h2p, 0.5D));
		}
		
		if(canConnect(iba, x - 1, y, z))
		{
			boxes.add(MathHelper.getBox(0.25D, h1n, 0.5D, 0.5D, h1p, 0.125D));
			boxes.add(MathHelper.getBox(0.25D, h2n, 0.5D, 0.5D, h2p, 0.125D));
		}
		
		if(canConnect(iba, x, y, z + 1))
		{
			boxes.add(MathHelper.getBox(0.5D, h1n, 0.75D, 0.125D, h1p, 0.5D));
			boxes.add(MathHelper.getBox(0.5D, h2n, 0.75D, 0.125D, h2p, 0.5D));
		}
		
		if(canConnect(iba, x + 1, y, z))
		{
			boxes.add(MathHelper.getBox(0.75D, h1n, 0.5D, 0.5D, h1p, 0.125D));
			boxes.add(MathHelper.getBox(0.75D, h2n, 0.5D, 0.5D, h2p, 0.125D));
		}
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		double p = 1D / 8D * 4D;
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
}