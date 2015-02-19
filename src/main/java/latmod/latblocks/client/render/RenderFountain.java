package latmod.latblocks.client.render;
import latmod.core.client.BlockRendererLM;
import latmod.core.tile.IPaintable;
import latmod.core.util.FastList;
import latmod.latblocks.LatBlocksItems;
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
	
	private static final FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
	private static final FastList<AxisAlignedBB> fluid_boxes = new FastList<AxisAlignedBB>();
	
	static { refreshBoxes(); }
	
	private static void refreshBoxes()
	{
		boxes.clear();
		fluid_boxes.clear();
		
		double b = 1D / 16D;
		double b2 = b * 2D;
		
		double p = b * 3D;
		boxes.add(cb(0D, b, 0D, p, b * 14D, p));
		
		double te = b / 8D;
		
		for(int i = 0; i < 3; i++)
		{
			boxes.add(cb(0D, b * i, 0D, b * (14D - i * 3D), b * (i + 1), b * (14D - i * 3D)));
		}
		
		{
			double r = b * 16D;
			double sh = b * 4D;
			double h = b * 4D;
			double rb2 = (r - b) / 2D;
			
			fluid_boxes.add(cb(0D, sh + h - b2, 0D, r - b2, sh + h - b, r - b2));
			
			boxes.add(cb(0D, sh, 0D, r - b2, sh + b, r - b2));
			
			boxes.add(cb(0D, sh + b, rb2, r - b2, sh + h, b));
			boxes.add(cb(0D, sh + b, -rb2, r - b2, sh + h, b));
			boxes.add(cb(rb2, sh + b, 0D, b, sh + h, r - b2));
			boxes.add(cb(-rb2, sh + b, 0D, b, sh + h, r - b2));
		}
		
		{
			double r = b * 12D;
			double sh = b * 9D;
			double h = b * 3D;
			double rb2 = (r - b) / 2D;
			
			fluid_boxes.add(cb(0D, sh + h - b2, 0D, r - b2, sh + h, r - b2));
			
			boxes.add(cb(0D, sh, 0D, r - b2, sh + b, r - b2));
			
			boxes.add(cb(0D, sh + b, rb2, r - b2, sh + h, b));
			boxes.add(cb(0D, sh + b, -rb2, r - b2, sh + h, b));
			boxes.add(cb(rb2, sh + b, 0D, b, sh + h, r - b2));
			boxes.add(cb(-rb2, sh + b, 0D, b, sh + h, r - b2));
			
			fluid_boxes.add(cb(0D, sh - b * 2D, rb2, b + te, sh + h + te, b + te));
			fluid_boxes.add(cb(0D, sh - b * 2D, -rb2, b + te, sh + h + te, b + te));
			fluid_boxes.add(cb(rb2, sh - b * 2D, 0D, b + te, sh + h + te, b + te));
			fluid_boxes.add(cb(-rb2, sh - b * 2D, 0D, b + te, sh + h + te, b + te));
		}
		
		{
			double r = b * 8D;
			double sh = b * 13D;
			double h = b * 3D;
			double rb2 = (r - b) / 2D;
			
			fluid_boxes.add(cb(0D, sh + h - b2, 0D, r - b2, sh + h, r - b2));
			
			boxes.add(cb(0D, sh, 0D, r - b2, sh + b, r - b2));
			
			boxes.add(cb(0D, sh + b, rb2, r - b2, sh + h, b));
			boxes.add(cb(0D, sh + b, -rb2, r - b2, sh + h, b));
			boxes.add(cb(rb2, sh + b, 0D, b, sh + h, r - b2));
			boxes.add(cb(-rb2, sh + b, 0D, b, sh + h, r - b2));
			
			fluid_boxes.add(cb(0D, sh - b, rb2, b + te, sh + h + te, b + te));
			fluid_boxes.add(cb(0D, sh - b, -rb2, b + te, sh + h + te, b + te));
			fluid_boxes.add(cb(rb2, sh - b, 0D, b + te, sh + h + te, b + te));
			fluid_boxes.add(cb(-rb2, sh - b, 0D, b + te, sh + h + te, b + te));
		}
		
		double s = -0.0005D;
		for(int i = 0; i < fluid_boxes.size(); i++)
			fluid_boxes.set(i, fluid_boxes.get(i).expand(s, s, s));
	}
	
	private static AxisAlignedBB cb(double x, double y1, double z, double w, double y2, double d)
	{
		x += 0.5D; z += 0.5D; w /= 2D; d /= 2D;
		return AxisAlignedBB.getBoundingBox(x - w, y1, z - d, x + w, y2, z + d);
	}
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.setCustomColor(null);
		renderBlocks.clearOverrideBlockTexture();
		
		GL11.glPushMatrix();
		rotateBlocks();
		
		for(int i = 0; i < boxes.size(); i++)
		{
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(boxes.get(i));
			renderBlocks.renderBlockAsItem(LatBlocksItems.b_paintable, 0, 1F);
			GL11.glPopMatrix();
		}
		
		for(int i = 0; i < fluid_boxes.size(); i++)
		{
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(fluid_boxes.get(i));
			renderBlocks.renderBlockAsItem(Blocks.flowing_water, 0, 1F);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		//refreshBoxes();
		
		renderBlocks.renderAllFaces = false;
		renderBlocks.blockAccess = iba;
		
		TileFountain t = (TileFountain)iba.getTileEntity(x, y, z);
		if(t == null || t.isInvalid()) return false;
		
		IIcon defIcon = LatBlocksItems.b_paintable.getBlockIcon();
		IPaintable.Paint[] paint = IPaintable.Renderer.to6(t.paint[0]);
		IIcon[] icons = IPaintable.Renderer.to6(defIcon);
		
		for(int i = 0; i < boxes.size(); i++)
			IPaintable.Renderer.renderCube(iba, renderBlocks, paint, icons, t.xCoord, t.yCoord, t.zCoord, boxes.get(i));
		
		if(t.tank.hasFluid())
		{
			for(int i = 0; i < fluid_boxes.size(); i++)
			{
				renderBlocks.setRenderBounds(fluid_boxes.get(i));
				renderFluidBlock(t);
			}
		}
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
	
	public void renderFluidBlock(TileFountain t)
	{
		renderBlocks.setCustomColor(null);
		
		IIcon icon = t.tank.getFluid().getStillIcon();
		if(icon == null && t.tank.getFluid().getBlock() != null)
			icon = t.tank.getFluid().getBlock().getBlockTextureFromSide(2);
		
		if(icon != null)
		{
			renderBlocks.setOverrideBlockTexture(icon);
			renderBlocks.renderStandardBlock(Blocks.stone, t.xCoord, t.yCoord, t.zCoord);
		}
		else
		{
			renderBlocks.clearOverrideBlockTexture();
			renderBlocks.renderStandardBlock(Blocks.flowing_water, t.xCoord, t.yCoord, t.zCoord);
		}
	}
}