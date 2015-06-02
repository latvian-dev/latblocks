package latmod.latblocks.client.render.world;

import latmod.core.client.*;
import latmod.core.tile.IPaintable;
import latmod.latblocks.block.BlockGlowium;
import latmod.latblocks.tile.TileGlowium;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderGlowiumBlocks extends BlockRendererLM
{
	public static final RenderGlowiumBlocks instance = new RenderGlowiumBlocks();
	
	public Block glow = new BlockCustom()
	{
		public int getMixedBrightnessForBlock(IBlockAccess iba, int x, int y, int z)
		{ return BlockGlowing.MAX; }
	};
	
	public Block empty = new BlockCustom()
	{
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		BlockGlowium bg = (BlockGlowium)b;
		
		renderBlocks.setCustomColor(LMRenderHelper.copyB(b.getRenderColor(meta), -20));
		
		GL11.glPushMatrix();
		rotateBlocks();
		renderBlocks.setOverrideBlockTexture(bg.getBlockIcon());
		renderBlocks.renderBlockAsItem(b, meta, 1F);
		GL11.glPopMatrix();
		
		renderBlocks.setCustomColor(b.getRenderColor(meta));
		
		GL11.glPushMatrix();
		rotateBlocks();
		renderBlocks.setOverrideBlockTexture(bg.icon_glow);
		renderBlocks.renderBlockAsItem(b, meta, 1F);
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
			Block b1 = iba.getBlock(x + Facing.offsetsXForSide[s], y + Facing.offsetsYForSide[s], z + Facing.offsetsZForSide[s]);
			if(b1.shouldSideBeRendered(iba, x + Facing.offsetsXForSide[s], y + Facing.offsetsYForSide[s], z + Facing.offsetsZForSide[s], Facing.oppositeSide[s]))
			{
				renderBlocks.setFaceBounds(s, 0D, 0D, 0D, 1D, 1D, 1D);
				renderBlocks.setCustomColor(renderColor);
				renderBlocks.setOverrideBlockTexture(bg.icon_glow);
				renderBlocks.renderStandardBlock(glow, x, y, z);
				
				if(t.paint[s] == null || t.paint[s].block == null)
				{
					renderBlocks.setOverrideBlockTexture(bg.getBlockIcon());
					renderBlocks.renderStandardBlock(empty, x, y, z);
				}
				else
				{
					renderBlocks.setCustomColor(null);
					double d = 0.001D;
					renderBlocks.setFaceBounds(s, d, d, d, 1D - d, 1D - d, 1D - d);
					IPaintable.Renderer.renderFace(iba, renderBlocks, s, t.paint[s], bg.getBlockIcon(), x, y, z);
				}
			}
		}
		
		//renderBlocks.renderStandardBlock(empty, x, y, z);
		
		return true;
	}
}