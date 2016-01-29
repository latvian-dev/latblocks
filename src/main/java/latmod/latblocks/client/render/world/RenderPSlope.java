package latmod.latblocks.client.render.world;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.client.GlStateManager;
import latmod.latblocks.block.paintable.BlockPSlope;
import latmod.latblocks.client.LatBlocksClient;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class RenderPSlope extends BlockRendererLM
{
	public static final RenderPSlope instance = new RenderPSlope();
	
	public RenderPSlope()
	{
	}
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = false;
		renderBlocks.setCustomColor(null);
		renderBlocks.setOverrideBlockTexture(b.getBlockTextureFromSide(1));
		
		GlStateManager.pushMatrix();
		LatBlocksClient.rotateBlocks();
		
		for(int i = 0; i < 6; i++)
		{
			if(i != 0 && i != 3)
			{
				GlStateManager.pushMatrix();
				renderBlocks.setRenderBounds(renderBlocks.fullBlock);
				renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
				GlStateManager.popMatrix();
			}
		}
		
		GlStateManager.popMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.setInst(iba);
		renderBlocks.renderAllFaces = true;
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		
		BlockPSlope.TilePSlope t = (BlockPSlope.TilePSlope) iba.getTileEntity(x, y, z);
		
		if(t == null) return false;
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}