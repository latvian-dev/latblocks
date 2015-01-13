package latmod.latblocks.client.render;

import latmod.core.client.BlockRendererLM;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderGlowiumBlocks extends BlockRendererLM
{
	public static final RenderGlowiumBlocks instance = new RenderGlowiumBlocks();
	
	public Block glow = new BlockGlowing()
	{
		public IIcon getIcon(int s, int m)
		{ return LatBlocksItems.b_glowium_blocks.icons_glow[m]; }
	};
	
	public Block empty = new BlockCustom()
	{
		public IIcon getIcon(int s, int m)
		{ return LatBlocksItems.b_glowium_blocks.icons[m]; }
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderBlockAsItem(empty, meta, 1F);
		renderBlocks.renderBlockAsItem(glow, meta, 1F);
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int renderID, RenderBlocks renderer0)
	{
		renderBlocks.blockAccess = iba;
		renderBlocks.setRenderBounds(0D, 0D, 0D, 1D, 1D, 1D);
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderStandardBlock(empty, x, y, z);
		renderBlocks.renderStandardBlock(glow, x, y, z);
		
		return true;
	}
}