package latmod.latblocks.client.render.world;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.util.client.BlockRendererLM;
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
		
		GL11.glPushMatrix();
		LatBlocksClient.rotateBlocks();
		
		for(int i = 0; i < 6; i++)
		{
			if(i != 0 && i != 3)
			{
				GL11.glPushMatrix();
				renderBlocks.setRenderBounds(renderBlocks.fullBlock);
				renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
				GL11.glPopMatrix();
			}
		}
		
		GL11.glPopMatrix();
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