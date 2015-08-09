package latmod.latblocks.client.render.world;
import latmod.ftbu.core.client.*;
import latmod.ftbu.core.paint.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.client.LatBlocksClient;
import latmod.latblocks.tile.TileFountain;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderFountain extends BlockRendererLM
{
	public static final RenderFountain instance = new RenderFountain();
	
	private static final AxisAlignedBB[] boxes = new AxisAlignedBB[19];
	private static final AxisAlignedBB[] fluid_boxes = new AxisAlignedBB[11];
	
	static
	{
		double b = 1D / 16D;
		double b2 = b * 2D;
		
		double p = b * 3D;
		boxes[0] = cb(0D, b, 0D, p, b * 14D, p);
		
		double te = b / 8D;
		
		for(int i = 0; i < 3; i++)
			boxes[i + 1] = cb(0D, b * i, 0D, b * (14D - i * 3D), b * (i + 1), b * (14D - i * 3D));
		
		{
			double r = b * 16D;
			double sh = b * 4D;
			double h = b * 4D;
			double rb2 = (r - b) / 2D;
			
			fluid_boxes[0] = cb(0D, sh + h - b2, 0D, r - b2, sh + h - b, r - b2);
			
			boxes[4] = cb(0D, sh, 0D, r - b2, sh + b, r - b2);
			
			boxes[5] = cb(0D, sh + b, rb2, r - b2, sh + h, b);
			boxes[6] = cb(0D, sh + b, -rb2, r - b2, sh + h, b);
			boxes[7] = cb(rb2, sh + b, 0D, b, sh + h, r - b2);
			boxes[8] = cb(-rb2, sh + b, 0D, b, sh + h, r - b2);
		}
		
		{
			double r = b * 12D;
			double sh = b * 9D;
			double h = b * 3D;
			double rb2 = (r - b) / 2D;
			
			fluid_boxes[1] = cb(0D, sh + h - b2, 0D, r - b2, sh + h, r - b2);
			
			boxes[9] = cb(0D, sh, 0D, r - b2, sh + b, r - b2);
			
			boxes[10] = cb(0D, sh + b, rb2, r - b2, sh + h, b);
			boxes[11] = cb(0D, sh + b, -rb2, r - b2, sh + h, b);
			boxes[12] = cb(rb2, sh + b, 0D, b, sh + h, r - b2);
			boxes[13] = cb(-rb2, sh + b, 0D, b, sh + h, r - b2);
			
			fluid_boxes[2] = cb(0D, sh - b * 2D, rb2, b + te, sh + h + te, b + te);
			fluid_boxes[3] = cb(0D, sh - b * 2D, -rb2, b + te, sh + h + te, b + te);
			fluid_boxes[4] = cb(rb2, sh - b * 2D, 0D, b + te, sh + h + te, b + te);
			fluid_boxes[5] = cb(-rb2, sh - b * 2D, 0D, b + te, sh + h + te, b + te);
		}
		
		{
			double r = b * 8D;
			double sh = b * 13D;
			double h = b * 3D;
			double rb2 = (r - b) / 2D;
			
			fluid_boxes[6] = cb(0D, sh + h - b2, 0D, r - b2, sh + h, r - b2);
			
			boxes[14] = cb(0D, sh, 0D, r - b2, sh + b, r - b2);
			
			boxes[15] = cb(0D, sh + b, rb2, r - b2, sh + h, b);
			boxes[16] = cb(0D, sh + b, -rb2, r - b2, sh + h, b);
			boxes[17] = cb(rb2, sh + b, 0D, b, sh + h, r - b2);
			boxes[18] = cb(-rb2, sh + b, 0D, b, sh + h, r - b2);
			
			fluid_boxes[7] = cb(0D, sh - b, rb2, b + te, sh + h + te, b + te);
			fluid_boxes[8] = cb(0D, sh - b, -rb2, b + te, sh + h + te, b + te);
			fluid_boxes[9] = cb(rb2, sh - b, 0D, b + te, sh + h + te, b + te);
			fluid_boxes[10] = cb(-rb2, sh - b, 0D, b + te, sh + h + te, b + te);
		}
		
		double s = -0.0005D;
		for(int i = 0; i < fluid_boxes.length; i++)
			fluid_boxes[i] = fluid_boxes[i].expand(s, s, s);
	}
	
	private static AxisAlignedBB cb(double x, double y1, double z, double w, double y2, double d)
	{
		x += 0.5D; z += 0.5D; w /= 2D; d /= 2D;
		return AxisAlignedBB.getBoundingBox(x - w, y1, z - d, x + w, y2, z + d);
	}
	
	private static TileFountain tile;
	
	public BlockCustom base = new BlockCustom()
	{
		public IIcon getIcon(int s, int m)
		{ return LatBlocksItems.b_paintable.getBlockIcon(); }
		
		public boolean isOpaqueCube()
		{ return false; }
		
		public boolean renderAsNormalBlock()
		{ return false; }
		
		public int getLightValue()
		{
			if(LatBlocksClient.blocksGlow.getB() && PaintableRenderer.currentPaint != null)
				return PaintableRenderer.currentPaint.block.getLightValue();
			return 0;
		}
	};
	
	public BlockCustom fluid = new BlockCustom()
	{
		public IIcon getIcon(int s, int m)
		{
			IIcon icon = tile.tank.getFluid().getStillIcon();
			if(icon == null && tile.tank.getFluid().getBlock() != null)
				icon = tile.tank.getFluid().getBlock().getBlockTextureFromSide(1);
			return icon;
		}
		
		public int getLightValue()
		{
			if(LatBlocksClient.blocksGlow.getB() && tile.tank.getFluid().getBlock() != null)
				return Math.max(tile.tank.getFluid().getLuminosity(), tile.tank.getFluid().getBlock().getLightValue());
			return 0;
		}
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.setCustomColor(null);
		renderBlocks.clearOverrideBlockTexture();
		
		GL11.glPushMatrix();
		LatBlocksClient.rotateBlocks();
		
		for(int i = 0; i < boxes.length; i++)
		{
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(boxes[i]);
			renderBlocks.renderBlockAsItem(LatBlocksItems.b_paintable, 0, 1F);
			GL11.glPopMatrix();
		}
		
		for(int i = 0; i < fluid_boxes.length; i++)
		{
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(fluid_boxes[i]);
			renderBlocks.renderBlockAsItem(Blocks.flowing_water, 0, 1F);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block block, int modelID, RenderBlocks rb)
	{
		renderBlocks.setInst(iba);
		renderBlocks.renderAllFaces = false;
		renderBlocks.currentSide = -1;
		renderBlocks.setCustomColor(null);
		
		tile = (TileFountain)iba.getTileEntity(x, y, z);
		if(tile == null) return false;
		
		Paint[] paint = PaintableRenderer.to6(tile.paint[0]);
		
		for(int i = 0; i < boxes.length; i++)
			PaintableRenderer.renderCube(iba, renderBlocks, paint, base, tile.xCoord, tile.yCoord, tile.zCoord, boxes[i]);
		
		renderBlocks.currentSide = -1;
		
		if(tile.tank.hasFluid())
		{
			renderBlocks.setOverrideBlockTexture(fluid.getIcon(1, 0));
			
			for(int i = 0; i < fluid_boxes.length; i++)
			{
				renderBlocks.setRenderBounds(fluid_boxes[i]);
				renderBlocks.renderStandardBlock(fluid, x, y, z);
			}
		}
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}