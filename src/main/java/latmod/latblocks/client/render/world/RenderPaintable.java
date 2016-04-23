package latmod.latblocks.client.render.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.client.GlStateManager;
import latmod.latblocks.api.Paint;
import latmod.latblocks.api.PaintableRenderer;
import latmod.latblocks.block.BlockPaintableLB;
import latmod.latblocks.client.LatBlocksClient;
import latmod.latblocks.tile.TilePaintableLB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class RenderPaintable extends BlockRendererLM
{
	public static final RenderPaintable instance = new RenderPaintable();
	private static final ArrayList<AxisAlignedBB> boxes0 = new ArrayList<>();
	private static BlockPaintableLB blockP;
	
	public BlockCustom base = new BlockCustom()
	{
		@Override
		public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
		{ return blockP.getDefaultWorldIcon(iba, x, y, z, s); }
		
		@Override
		public int getLightValue()
		{
			if(LatBlocksClient.blocksGlow.getAsBoolean() && PaintableRenderer.currentPaint != null)
				return PaintableRenderer.currentPaint.block.getLightValue();
			return 0;
		}
	};
	
	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = false;
		boxes0.clear();
		((BlockPaintableLB) b).addItemRenderBoxes(boxes0);
		renderBlocks.setCustomColor(null);
		renderBlocks.setOverrideBlockTexture(((BlockPaintableLB) b).getDefaultItemIcon());
		
		GlStateManager.pushMatrix();
		LatBlocksClient.rotateBlocks();
		
		for(int i = 0; i < boxes0.size(); i++)
		{
			GlStateManager.pushMatrix();
			renderBlocks.setRenderBounds(boxes0.get(i));
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.setInst(iba);
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		
		TilePaintableLB t = (TilePaintableLB) iba.getTileEntity(x, y, z);
		
		if(t == null || t.isInvalid()) return false;
		
		blockP = (BlockPaintableLB) b;
		
		Paint[] p = new Paint[6];
		for(int i = 0; i < 6; i++)
			p[i] = t.getPaint(i);
		
		boxes0.clear();
		blockP.addRenderBoxes(boxes0, iba, x, y, z, -1);
		
		for(int i = 0; i < boxes0.size(); i++)
			PaintableRenderer.renderCube(iba, renderBlocks, p, base, x, y, z, boxes0.get(i));
		
		return true;
	}
}