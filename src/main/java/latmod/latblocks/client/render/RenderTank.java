package latmod.latblocks.client.render;
import latmod.core.*;
import latmod.core.client.BlockRendererLM;
import latmod.latblocks.block.tank.*;
import latmod.latblocks.tile.tank.TileTankBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderTank extends BlockRendererLM
{
	public static final RenderTank instance = new RenderTank();
	
	private static AxisAlignedBB[] boxes = new AxisAlignedBB[0];
	
	public static IIcon icon_inside = null;
	
	static { refreshBoxes(); }
	
	private static void refreshBoxes()
	{
		boxes = new AxisAlignedBB[12];
		
		double p = 1D / 16D - 0.001D;
		
		boxes[0] = AxisAlignedBB.getBoundingBox(0D, 0D, 0D, p, 1D, p);
		boxes[1] = AxisAlignedBB.getBoundingBox(0D, 0D, 1D - p, p, 1D, 1D);
		boxes[2] = AxisAlignedBB.getBoundingBox(1D - p, 0D, 0D, 1D, 1D, p);
		boxes[3] = AxisAlignedBB.getBoundingBox(1D - p, 0D, 1D - p, 1D, 1D, 1D);
		
		boxes[4] = AxisAlignedBB.getBoundingBox(p, 0D, 0D, 1D - p, p, p);
		boxes[5] = AxisAlignedBB.getBoundingBox(p, 0D, 1D - p, 1D - p, p, 1D);
		boxes[6] = AxisAlignedBB.getBoundingBox(0D, 0D, p, p, p, 1D - p);
		boxes[7] = AxisAlignedBB.getBoundingBox(1D - p, 0D, p, 1D, p, 1D - p);
		
		boxes[8] = AxisAlignedBB.getBoundingBox(p, 1D - p, 0D, 1D - p, 1D, p);
		boxes[9] = AxisAlignedBB.getBoundingBox(p, 1D - p, 1D - p, 1D - p, 1D, 1D);
		boxes[10] = AxisAlignedBB.getBoundingBox(0D, 1D - p, p, p, 1D, 1D - p);
		boxes[11] = AxisAlignedBB.getBoundingBox(1D - p, 1D - p, p, 1D, 1D, 1D - p);
	}
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.setCustomColor(null);
		renderBlocks.clearOverrideBlockTexture();
		
		BlockTankBase tb = (BlockTankBase)b;
		
		double p = 1D / 16D;
		
		GL11.glPushMatrix();
		rotateBlocks();
		
		renderBlocks.setOverrideBlockTexture(tb.getTankItemBorderIcon(meta));
		
		for(int i = 0; i < boxes.length; i++)
		{
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(boxes[i]);
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		renderBlocks.setRenderBounds(p, p, p, 1D - p, 1D - p, 1D - p);
		renderBlocks.setOverrideBlockTexture(icon_inside);
		renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
		GL11.glPopMatrix();
		
		IIcon fluid_icon = tb.getTankItemFluidIcon(meta);
		
		if(fluid_icon != null)
		{
			double op = p + 0.001D;
			
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(op, op, op, 1D - op, 1D - op, 1D - op);
			renderBlocks.setOverrideBlockTexture(fluid_icon);
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.blockAccess = iba;
		renderBlocks.setCustomColor(null);
		
		TileTankBase t = (TileTankBase)iba.getTileEntity(x, y, z);
		if(t == null || t.isInvalid()) return false;
		
		renderBlocks.setOverrideBlockTexture(t.getTankBorderIcon());
		
		for(int i = 0; i < boxes.length; i++)
		{
			renderBlocks.setRenderBounds(boxes[i]);
			renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
		}
		
		double p = 1D / 16D;
		
		renderBlocks.setRenderBounds(p, p, p, 1D - p, 1D - p, 1D - p);
		renderBlocks.setOverrideBlockTexture(icon_inside);
		renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
		
		double fluid_height = t.getTankFluidHeight();
		IIcon icon_fluid = null;
		
		if(fluid_height > 0D && (icon_fluid = t.getTankFluidIcon()) != null)
		{
			double op = p + 0.001D;
			double h1 = MathHelperLM.map(fluid_height, 0D, 1D, op, 1D - op);
			
			renderBlocks.setRenderBounds(op, op, op, 1D - op, h1, 1D - op);
			renderBlocks.setOverrideBlockTexture(icon_fluid);
			renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
		}
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}