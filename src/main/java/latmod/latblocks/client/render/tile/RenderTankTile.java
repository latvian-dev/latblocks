package latmod.latblocks.client.render.tile;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.*;
import latmod.ftbu.util.client.TileRenderer;
import latmod.ftbu.util.client.model.CubeRenderer;
import latmod.latblocks.tile.tank.TileTankBase;
import latmod.lib.MathHelperLM;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

@SideOnly(Side.CLIENT)
public class RenderTankTile extends TileRenderer<TileTankBase>
{
	public static final RenderTankTile instance = new RenderTankTile();
	public static final CubeRenderer fluidRenderer = new CubeRenderer();
	
	public void renderTile(TileTankBase t, double x, double y, double z, float pt)
	{
		Fluid f = t.getTankRenderFluid();
		
		if(f != null)
		{
			double fluid_height = t.getTankFluidHeight();
			IIcon icon_fluid = null;
			
			if(fluid_height > 0D && (icon_fluid = f.getStillIcon()) != null)
			{
				Block b = f.getBlock();
				if(b == null || b == Blocks.air) b = Blocks.stone;
				
				GlStateManager.pushMatrix();
				GlStateManager.pushAttrib();
				GlStateManager.enableRescaleNormal();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.disableLighting();
				GlStateManager.translate(x, y, z);
				//GL11.glTranslated(x, y + 1D, z + 1D);
				//GL11.glScalef(1F, -1F, -1F);
				GlStateManager.color(1F, 1F, 1F, 1F);
				
				boolean glows = b.getLightValue() > 0 || f.getLuminosity() > 0;
				if(glows) FTBLibClient.pushMaxBrightness();
				
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
				
				double op = 1D / 16D + 0.001D;
				double h1 = MathHelperLM.map(fluid_height, 0D, 1D, op, 1D - op);
				
				fluidRenderer.setSize(op, op, op, 1D - op, h1, 1D - op);
				fluidRenderer.setUVFromIcon(icon_fluid);
				fluidRenderer.renderAll();
				
				/*
				RenderTank.instance.renderBlocks.blockAccess = t.getWorldObj();
				RenderTank.instance.renderBlocks.setRenderBounds(op, op, op, 1D - op, h1, 1D - op);
				RenderTank.instance.renderBlocks.setOverrideBlockTexture(icon_fluid);
				//RenderTank.instance.renderBlocks.renderBlockSandFalling(t.getBlockType(), t.getWorldObj(), t.xCoord, t.yCoord, t.zCoord, 0);
				RenderTank.instance.renderBlocks.renderStandardBlockIcons(b, t.xCoord, t.yCoord, t.zCoord, new IIcon[]{ icon_fluid, icon_fluid, icon_fluid, icon_fluid, icon_fluid, icon_fluid }, true);
				*/
				
				if(glows) FTBLibClient.popMaxBrightness();
				
				GlStateManager.popAttrib();
				GlStateManager.popMatrix();
			}
		}
	}
}