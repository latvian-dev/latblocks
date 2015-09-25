package latmod.latblocks.client.render.world;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.paint.*;
import latmod.ftbu.util.client.*;
import latmod.latblocks.block.BlockGlowium;
import latmod.latblocks.client.LatBlocksClient;
import latmod.latblocks.tile.TilePaintableLB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class RenderGlowiumBlocks extends BlockRendererLM // RenderPaintable
{
	public static final RenderGlowiumBlocks instance = new RenderGlowiumBlocks();
	private static int currentColor = 0;
	private static BlockGlowium currentGBlock;
	
	/*
	public BlockCustom glow = new BlockGlowing()
	{
		public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
		{ return s == BlockCustom.currentSide; }
		
		public int colorMultiplier(IBlockAccess iba, int x, int y, int z)
		{ return currentColor; }
		
		public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
		{ return currentGBlock.getGlowIcon(iba, x, y, z, s); }
	};
	*/
	
	public BlockCustom base = new BlockCustom()
	{
		public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
		{ return currentGBlock.getBlockIcon(); }
		
		public int colorMultiplier(IBlockAccess iba, int x, int y, int z)
		{ return currentColor; }
		
		public int getLightValue()
		{
			if(LatBlocksClient.blocksGlow.getB() && PaintableRenderer.currentPaint != null)
				return PaintableRenderer.currentPaint.block.getLightValue();
			return 0;
		}
	};
	
	public BlockCustom glow = new BlockCustom()
	{
		public int getLightValue()
		{ return 15; }
		
		public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
		{ return currentGBlock.getGlowIcon(iba, x, y, z, s); }
		
		public int colorMultiplier(IBlockAccess iba, int x, int y, int z)
		{ return currentColor; }
	};
	
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
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.setInst(iba);
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		
		TilePaintableLB t = (TilePaintableLB)iba.getTileEntity(x, y, z);
		
		if(t == null || t.isInvalid()) return false;
		
		currentGBlock = (BlockGlowium)b;
		
		currentColor = BlockGlowium.brightColors[iba.getBlockMetadata(x, y, z)];
		
		AxisAlignedBB glowBB = renderBlocks.fullBlock.expand(0.003D, 0.003D, 0.003D);
		
		for(int s = 0; s < 6; s++)
		{
			Paint p = t.getPaint(s);
			renderBlocks.currentSide = s;
			
			if((p != null && (!p.block.isOpaqueCube() || !p.block.renderAsNormalBlock())) || currentGBlock.shouldSideBeRendered(iba, x + Facing.offsetsXForSide[s], y + Facing.offsetsYForSide[s], z + Facing.offsetsZForSide[s], s))
			{
				renderBlocks.renderAllFaces = false;
				renderBlocks.setCustomColor(currentColor);
				
				renderBlocks.setFaceBounds(s, glowBB);
				renderBlocks.setOverrideBlockTexture(currentGBlock.getGlowIcon(iba, x, y, z, s));
				renderBlocks.renderStandardBlock(glow, x, y, z);
				
				renderBlocks.setFaceBounds(s, renderBlocks.fullBlock);
				PaintableRenderer.renderFace(iba, renderBlocks, s, p, base, x, y, z);
			}
		}
		
		return true;
	}
}