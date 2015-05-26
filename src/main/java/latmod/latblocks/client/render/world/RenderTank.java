package latmod.latblocks.client.render.world;
import latmod.core.client.BlockRendererLM;
import latmod.core.mod.LCConfig;
import latmod.core.util.MathHelperLM;
import latmod.latblocks.block.tank.BlockTankBase;
import latmod.latblocks.tile.tank.TileTankBase;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

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
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{ return true; }
	
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{ return helper != ItemRendererHelper.ENTITY_ROTATION || !LCConfig.Client.rotateBlocks; }
	
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.setCustomColor(null);
		
		BlockTankBase tb = (BlockTankBase)Block.getBlockFromItem(item.getItem());
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		
		double p = 1D / 16D;
		
		GL11.glPushMatrix();
		rotateBlocks();
		
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glColor3f(1, 1, 1);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		renderBlocks.setOverrideBlockTexture(tb.getTankItemBorderIcon(item));
		
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
		
		FluidStack fluid = tb.getTankItemFluid(item);
		
		if(fluid != null && fluid.getFluid() != null && fluid.amount > 0)
		{
			double op = p + 0.001D;
			double h1 = MathHelperLM.map(Math.min(fluid.amount / 1000D, 1D), 0D, 1D, op, 1D - op);
			
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(op, op, op, 1D - op, h1, 1D - op);
			renderBlocks.setOverrideBlockTexture(fluid.getFluid().getStillIcon());
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
			GL11.glPopMatrix();
		}
		
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
}