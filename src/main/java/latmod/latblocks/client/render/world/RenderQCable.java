package latmod.latblocks.client.render.world;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.GlStateManager;
import latmod.ftbu.util.client.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockQCable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class RenderQCable extends BlockRendererLM
{
	public static final RenderQCable instance = new RenderQCable();
	
	public BlockCustom empty = new BlockCustom()
	{
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.pushMatrix();
		b.setBlockBoundsForItemRender();
		renderBlocks.setCustomColor(0xFFFFFFFF);
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setOverrideBlockTexture(LatBlocksItems.b_qcable.getBlockIcon());
		renderBlocks.renderBlockAsItem(empty, meta, 1F);
		GlStateManager.popMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int renderID, RenderBlocks renderer0)
	{
		renderBlocks.setInst(iba);
		renderBlocks.renderAllFaces = true;
		
		double s0 = BlockQCable.border;
		double s1 = 1D - s0;
		
		boolean x0 = BlockQCable.connects(iba, x - 1, y, z);
		boolean x1 = BlockQCable.connects(iba, x + 1, y, z);
		boolean y0 = BlockQCable.connects(iba, x, y - 1, z);
		boolean y1 = BlockQCable.connects(iba, x, y + 1, z);
		boolean z0 = BlockQCable.connects(iba, x, y, z - 1);
		boolean z1 = BlockQCable.connects(iba, x, y, z + 1);
		
		renderBlocks.setCustomColor(null);
		renderBlocks.setOverrideBlockTexture(LatBlocksItems.b_qcable.getBlockIcon());
		
		renderBox(x, y, z, s0, s0, s0, s1, s1, s1);
		if(x0) renderBox(x, y, z, 0D, s0, s0, s0, s1, s1);
		if(x1) renderBox(x, y, z, s1, s0, s0, 1D, s1, s1);
		if(y0) renderBox(x, y, z, s0, 0D, s0, s1, s0, s1);
		if(y1) renderBox(x, y, z, s0, s1, s0, s1, 1D, s1);
		if(z0) renderBox(x, y, z, s0, s0, 0D, s1, s1, s0);
		if(z1) renderBox(x, y, z, s0, s0, s1, s1, s1, 1D);
		
		return true;
	}
	
	private void renderBox(int x, int y, int z, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
	{
		renderBlocks.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
		renderBlocks.renderStandardBlock(empty, x, y, z);
	}
}