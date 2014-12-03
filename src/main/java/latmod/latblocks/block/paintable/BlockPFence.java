package latmod.latblocks.block.paintable;

import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.core.util.FastList;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.*;
import latmod.latblocks.tile.paintable.TilePFence;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
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
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		double p = 1F / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, pn, pp, 1D, pp));
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		double hitY = mop.hitVec.yCoord - mop.blockY;
		
		if(!(mop.sideHit == Placement.D_DOWN || mop.sideHit == Placement.D_UP))
			hitY = 1D - hitY;
		
		int l = MathHelper.floor_double((double)(ep.rotationYaw * 8F / 360F) + 0.5D) & 7;
		return (hitY >= 0.5D) ? (8 + l) : l;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z)
	{
		double p = 1F / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		/*
		double h1n = 1D / 8D * 2D;
		double h1p = 1D / 8D * 3D;
		double h2n = 1D / 8D * 5D;
		double h2p = 1D / 8D * 6D;
		*/
		
		boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, pn, pp, 1D, pp));
		
		/*
		boolean hasN = iba.getBlock(x, y, z - 1) == this;
		boolean hasW = iba.getBlock(x - 1, y, z) == this;
		boolean hasS = iba.getBlock(x, y, z + 1) == this;
		boolean hasE = iba.getBlock(x + 1, y, z) == this;
		*/
		
		//if(hasN) boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.0D, 0.5D, h1, 0.5D));
		//if(hasW) boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.0D, 1.0D, h1, 0.5D));
		//if(hasS) boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.5D, 0.5D, h1, 1.0D));
		//if(hasE) boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.5D, 1.0D, h1, 1.0D));
	}
}