package latmod.latblocks.client.render.tile;
import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.util.client.*;
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
				
				GL11.glPushMatrix();
				GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glTranslated(x, y, z);
				//GL11.glTranslated(x, y + 1D, z + 1D);
				//GL11.glScalef(1F, -1F, -1F);
				GL11.glColor4f(1F, 1F, 1F, 1F);
				
				boolean glows = b.getLightValue() > 0 || f.getLuminosity() > 0;
				if(glows) LatCoreMCClient.pushMaxBrightness();
				
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
				
				if(glows) LatCoreMCClient.popMaxBrightness();
				
				GL11.glPopAttrib();
				GL11.glPopMatrix();
			}
		}
	}
}