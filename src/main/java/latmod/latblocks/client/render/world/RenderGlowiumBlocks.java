package latmod.latblocks.client.render.world;

import latmod.ftbu.core.client.*;
import latmod.ftbu.core.tile.IPaintable;
import latmod.latblocks.block.BlockGlowium;
import latmod.latblocks.client.LatBlocksClient;
import latmod.latblocks.tile.TileGlowium;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderGlowiumBlocks extends BlockRendererLM
{
	public static final RenderGlowiumBlocks instance = new RenderGlowiumBlocks();
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		BlockGlowium bg = (BlockGlowium)b;
		
		renderBlocks.setCustomColor(LMRenderHelper.copyB(b.getRenderColor(meta), -20));
		
		GL11.glPushMatrix();
		LatBlocksClient.rotateBlocks();
		renderBlocks.setOverrideBlockTexture(bg.getBlockIcon());
		renderBlocks.renderBlockAsItem(b, meta, 1F);
		GL11.glPopMatrix();
		
		renderBlocks.setCustomColor(b.getRenderColor(meta));
		
		double d = -0.001D;
		renderBlocks.setRenderBounds(d, d, d, 1D - d, 1D - d, 1D - d);
		GL11.glPushMatrix();
		LatBlocksClient.rotateBlocks();
		LatCoreMCClient.pushMaxBrightness();
		renderBlocks.setOverrideBlockTexture(bg.getGlowItemIcon());
		renderBlocks.renderBlockAsItem(b, meta, 1F);
		LatCoreMCClient.popMaxBrightness();
		GL11.glPopMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int renderID, RenderBlocks renderer0)
	{
		renderBlocks.blockAccess = iba;
		BlockGlowium bg = (BlockGlowium)b;
		
		int meta = iba.getBlockMetadata(x, y, z);
		
		TileGlowium t = (TileGlowium)iba.getTileEntity(x, y, z);
		if(t == null || t.isInvalid()) return false;
		
		int renderColor = b.getRenderColor(meta);
		
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		
		for(int s = 0; s < 6; s++)
		{
			if(bg.shouldSideBeRendered(iba, x, y, z, s))
			{
				double d = -0.005D;
				renderBlocks.setFaceBounds(s, d, d, d, 1D - d, 1D - d, 1D - d);
				renderBlocks.setCustomColor(renderColor);
				renderBlocks.setOverrideBlockTexture(bg.getGlowIcon(iba, x, y, z, s));
				renderBlocks.renderStandardBlock(BlockGlowing.instGlowing, x, y, z);
				renderBlocks.setFaceBounds(s, 0D, 0D, 0D, 1D, 1D, 1D);
				
				if(t.paint[s] == null || t.paint[s].block == null)
				{
					renderBlocks.setOverrideBlockTexture(bg.getBlockIcon());
					renderBlocks.renderStandardBlock(BlockCustom.inst, x, y, z);
				}
				else
				{
					renderBlocks.setCustomColor(null);
					IPaintable.Renderer.renderFace(iba, renderBlocks, s, t.paint[s], bg.getBlockIcon(), x, y, z);
				}
			}
		}
		
		//renderBlocks.renderStandardBlock(empty, x, y, z);
		
		return true;
	}
}