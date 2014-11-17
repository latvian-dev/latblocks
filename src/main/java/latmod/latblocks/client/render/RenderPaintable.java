package latmod.latblocks.client.render;
import latmod.core.client.*;
import latmod.core.tile.IPaintable.Paint;
import latmod.core.util.FastList;
import latmod.latblocks.block.BlockPaintableLB;
import latmod.latblocks.tile.TilePaintableLB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
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
		renderBlocks.setOverrideBlockTexture(b.getIcon(0, 0));
		
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
		IIcon defIcon = b.getIcon(0, t.iconMeta());
		
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		((BlockPaintableLB)b).addRenderBoxes(boxes, iba.getBlockMetadata(x, y, z));
		
		for(int i = 0; i < boxes.size(); i++)
			renderCube(renderBlocks, p, defIcon, x, y, z, boxes.get(i));
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
	
	public static void renderCube(RenderBlocksCustom rb, Paint[] p, IIcon defIcon, int x, int y, int z, AxisAlignedBB aabb)
	{
		double d0 = 0D;
		double d1 = 0.0001D;
		
		rb.setRenderBounds(aabb.minX, aabb.minY + d0, aabb.minZ, aabb.maxX, aabb.minY + d0, aabb.maxZ);
		renderFace(rb, ForgeDirection.DOWN, p, defIcon, x, y, z);
		
		rb.setRenderBounds(aabb.minX, aabb.maxY + d1, aabb.minZ, aabb.maxX, aabb.maxY + d1, aabb.maxZ);
		renderFace(rb, ForgeDirection.UP, p, defIcon, x, y, z);
		
		rb.setRenderBounds(aabb.minX, aabb.minY, aabb.minZ + d0, aabb.maxX, aabb.maxY, aabb.minZ + d0);
		renderFace(rb, ForgeDirection.NORTH, p, defIcon, x, y, z);
		
		rb.setRenderBounds(aabb.minX, aabb.minY, aabb.maxZ + d1, aabb.maxX, aabb.maxY, aabb.maxZ + d1);
		renderFace(rb, ForgeDirection.SOUTH, p, defIcon, x, y, z);
		
		rb.setRenderBounds(aabb.minX + d0, aabb.minY, aabb.minZ, aabb.minX + d0, aabb.maxY, aabb.maxZ);
		renderFace(rb, ForgeDirection.WEST, p, defIcon, x, y, z);
		
		rb.setRenderBounds(aabb.maxX + d1, aabb.minY, aabb.minZ, aabb.maxX + d1, aabb.maxY, aabb.maxZ);
		renderFace(rb, ForgeDirection.EAST, p, defIcon, x, y, z);
	}
	
	public static void renderFace(RenderBlocksCustom rb, ForgeDirection face, Paint[] p, IIcon defIcon, int x, int y, int z)
	{
		int id = face.ordinal();
		
		if(p[id] != null)
		{
			//renderBlocks.setOverrideBlockTexture(p[id].block.getIcon(renderBlocks.blockAccess, x, y, z, id));
			rb.setOverrideBlockTexture(p[id].block.getIcon(id, p[id].meta));
			rb.setCustomColor(p[id].block.getRenderColor(p[id].meta));
			rb.customMetadata = p[id].meta;
		}
		else
		{
			rb.setCustomColor(null);
			rb.setOverrideBlockTexture(defIcon);
			rb.customMetadata = null;
		}
		
		rb.renderStandardBlock(Blocks.stained_glass, x, y, z);
	}
}