package latmod.latblocks.client.render.world;

import latmod.core.client.*;
import latmod.latblocks.block.BlockGlowium;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
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
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		BlockGlowium bg = (BlockGlowium)b;
		
		int meta = iba.getBlockMetadata(x, y, z);
		renderBlocks.setCustomColor(b.getRenderColor(meta));
		
		renderBlocks.setOverrideBlockTexture(bg.getBlockIcon());
		renderBlocks.renderStandardBlock(empty, x, y, z);
		
		renderBlocks.setOverrideBlockTexture(bg.icon_glow);
		renderBlocks.renderStandardBlock(glow, x, y, z);
		
		return true;
	}
}