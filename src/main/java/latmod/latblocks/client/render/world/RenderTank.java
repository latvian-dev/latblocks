package latmod.latblocks.client.render.world;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.GlStateManager;
import latmod.ftbu.util.client.BlockRendererLM;
import latmod.latblocks.block.tank.BlockTankBase;
import latmod.latblocks.client.LatBlocksClient;
import latmod.latblocks.tile.tank.TileTankBase;
import latmod.lib.MathHelperLM;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidStack;

@SideOnly(Side.CLIENT)
public class RenderTank extends BlockRendererLM implements IItemRenderer
{
	public static final RenderTank instance = new RenderTank();
	
	private static final AxisAlignedBB[] boxes = new AxisAlignedBB[12];
	
	public static IIcon icon_inside = null;
	
	static
	{
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
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.setInst(iba);
		renderBlocks.renderAllFaces = true;
		renderBlocks.setCustomColor(null);
		
		TileTankBase t = (TileTankBase)iba.getTileEntity(x, y, z);
		if(t == null || t.isInvalid()) return false;
		
		renderBlocks.setOverrideBlockTexture(t.getTankBorderIcon());
		
		for(int i = 0; i < boxes.length; i++)
		{
			renderBlocks.setRenderBounds(boxes[i]);
			renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
		}
		
		double p = 1D / 16D - 0.001D;
		
		renderBlocks.setRenderBounds(p, p, p, 1D - p, 1D - p, 1D - p);
		renderBlocks.setOverrideBlockTexture(icon_inside);
		renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{ return true; }
	
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{ return helper != ItemRendererHelper.ENTITY_ROTATION || !LatBlocksClient.rotateBlocks.get(); }
	
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.setCustomColor(null);
		
		BlockTankBase tb = (BlockTankBase)Block.getBlockFromItem(item.getItem());
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		
		double p = 1D / 16D;
		
		GlStateManager.pushMatrix();
		LatBlocksClient.rotateBlocks();
		
		GlStateManager.pushAttrib();
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableAlpha();
		
		renderBlocks.setOverrideBlockTexture(tb.getTankItemBorderIcon(item));
		
		for(int i = 0; i < boxes.length; i++)
		{
			GlStateManager.pushMatrix();
			renderBlocks.setRenderBounds(boxes[i]);
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.pushMatrix();
		double p1 = p - 0.001D;
		renderBlocks.setRenderBounds(p1, p1, p1, 1D - p1, 1D - p1, 1D - p1);
		renderBlocks.setOverrideBlockTexture(icon_inside);
		renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
		GlStateManager.popMatrix();
		
		FluidStack fluid = tb.getTankItemFluid(item);
		
		if(fluid != null && fluid.getFluid() != null && fluid.amount > 0)
		{
			double op = p + 0.001D;
			double h1 = MathHelperLM.map(Math.min(fluid.amount / 1000D, 1D), 0D, 1D, op, 1D - op);
			
			GlStateManager.pushMatrix();
			renderBlocks.setRenderBounds(op, op, op, 1D - op, h1, 1D - op);
			renderBlocks.setOverrideBlockTexture(fluid.getFluid().getStillIcon());
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}
}