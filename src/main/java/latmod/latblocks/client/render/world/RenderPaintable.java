package latmod.latblocks.client.render.world;
import latmod.ftbu.core.client.BlockRendererLM;
import latmod.ftbu.core.tile.*;
import latmod.ftbu.core.tile.IPaintable.Paint;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.block.BlockPaintableLB;
import latmod.latblocks.client.LatBlocksClient;
import latmod.latblocks.tile.TilePaintableLB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderPaintable extends BlockRendererLM
{
	public static final RenderPaintable instance = new RenderPaintable();
	private static final FastList<AxisAlignedBB> boxes0 = new FastList<AxisAlignedBB>();
	private static BlockPaintableLB blockP;
	
	public BlockCustom base = new BlockCustom()
	{
		public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
		{ return blockP.getDefaultWorldIcon(iba, x, y, z, s); }
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = false;
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		((BlockPaintableLB)b).addItemRenderBoxes(boxes);
		renderBlocks.setCustomColor(null);
		renderBlocks.setOverrideBlockTexture(((BlockPaintableLB)b).getDefaultItemIcon());
		
		GL11.glPushMatrix();
		LatBlocksClient.rotateBlocks();
		
		for(int i = 0; i < boxes.size(); i++)
		{
			GL11.glPushMatrix();
			renderBlocks.setRenderBounds(boxes.get(i));
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.blockAccess = iba;
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		
		TilePaintableLB t = (TilePaintableLB)iba.getTileEntity(x, y, z);
		
		if(t == null || t.isInvalid()) return false;
		
		blockP = (BlockPaintableLB)b;
		
		Paint[] p = new Paint[6];
		for(int i = 0; i < 6; i++)
			p[i] = t.getPaint(i);
		
		boxes0.clear();
		blockP.addRenderBoxes(boxes0, iba, x, y, z, -1);
		
		for(int i = 0; i < boxes0.size(); i++)
			IPaintable.Renderer.renderCube(iba, renderBlocks, p, base, x, y, z, boxes0.get(i));
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}