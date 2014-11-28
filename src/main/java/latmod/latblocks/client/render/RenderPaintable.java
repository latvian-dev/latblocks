package latmod.latblocks.client.render;
import latmod.core.client.BlockRendererLM;
import latmod.core.tile.*;
import latmod.core.tile.IPaintable.Paint;
import latmod.core.util.FastList;
import latmod.latblocks.block.BlockPaintableLB;
import latmod.latblocks.tile.TilePaintableLB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderPaintable extends BlockRendererLM
{
	public static final RenderPaintable instance = new RenderPaintable();
	
	public RenderPaintable()
	{
	}
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = false;
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		((BlockPaintableLB)b).addItemRenderBoxes(boxes);
		renderBlocks.setCustomColor(null);
		renderBlocks.customMetadata = 0;
		renderBlocks.setOverrideBlockTexture(((BlockPaintableLB)b).getDefaultItemIcon());
		
		for(int i = 0; i < boxes.size(); i++)
		{
			renderBlocks.setRenderBounds(boxes.get(i));
			renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
		}
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.blockAccess = iba;
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		
		TilePaintableLB t = (TilePaintableLB)iba.getTileEntity(x, y, z);
		
		if(t == null || t.isInvalid()) return false;
		
		Paint[] p = t.getPaint();
		IIcon[] defIcon = new IIcon[6];
		for(int i = 0; i < 6; i++)
			defIcon[i] = ((BlockPaintableLB)b).getDefaultWorldIcon(iba, x, y, z, i);
		
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		((BlockPaintableLB)b).addRenderBoxes(boxes, iba.getBlockMetadata(x, y, z));
		
		for(int i = 0; i < boxes.size(); i++)
			IPaintable.Renderer.renderCube(renderBlocks, p, defIcon, x, y, z, boxes.get(i));
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}