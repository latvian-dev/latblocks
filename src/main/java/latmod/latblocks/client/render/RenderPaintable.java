package latmod.latblocks.client.render;
import latmod.core.client.*;
import latmod.core.tile.IPaintable.Paint;
import latmod.latblocks.tile.TilePaintable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderPaintable extends BlockRendererLM
{
	public static final RenderPaintable instance = new RenderPaintable();
	
	public Block renderBlock = new BlockCustom()
	{
		public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
		{ return true; }
		
		public int getRenderBlockPass()
		{ return 0; }
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = false;
		renderBlocks.setRenderBounds(renderBlocks.fullBlock);
		renderBlocks.setCustomColor(null);
		renderBlocks.customMetadata = 0;
		renderBlocks.setOverrideBlockTexture(b.getIcon(0, 0));
		renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.blockAccess = iba;
		renderBlocks.setRenderBounds(renderBlocks.fullBlock);
		renderBlocks.setCustomColor(null);
		
		TilePaintable t = (TilePaintable)iba.getTileEntity(x, y, z);
		
		double d0 = 0D;
		double d1 = 1D - d0 + 0.001D;
		
		IIcon defIcon = b.getIcon(0, 0);
		Paint[] p = t.currentPaint();
		
		renderBlocks.setRenderBounds(0D, d0, 0D, 1D, d0, 1D);
		renderFace(ForgeDirection.DOWN, p, defIcon, x, y, z);
		
		renderBlocks.setRenderBounds(0D, d1, 0D, 1D, d1, 1D);
		renderFace(ForgeDirection.UP, p, defIcon, x, y, z);
		
		renderBlocks.setRenderBounds(0D, 0D, d0, 1D, 1D, d0);
		renderFace(ForgeDirection.NORTH, p, defIcon, x, y, z);
		
		renderBlocks.setRenderBounds(0D, 0D, d1, 1D, 1D, d1);
		renderFace(ForgeDirection.SOUTH, p, defIcon, x, y, z);
		
		renderBlocks.setRenderBounds(d0, 0D, 0D, d0, 1D, 1D);
		renderFace(ForgeDirection.WEST, p, defIcon, x, y, z);
		
		renderBlocks.setRenderBounds(d1, 0D, 0D, d1, 1D, 1D);
		renderFace(ForgeDirection.EAST, p, defIcon, x, y, z);
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
	
	public void renderFace(ForgeDirection face, Paint[] p, IIcon defIcon, int x, int y, int z)
	{
		int id = face.ordinal();
		
		if(p[id] != null)
		{
			//renderBlocks.setOverrideBlockTexture(p[id].block.getIcon(renderBlocks.blockAccess, x, y, z, id));
			renderBlocks.setOverrideBlockTexture(p[id].block.getIcon(id, p[id].meta));
			renderBlocks.setCustomColor(p[id].block.getRenderColor(p[id].meta));
			renderBlocks.customMetadata = p[id].meta;
		}
		else
		{
			renderBlocks.setCustomColor(null);
			renderBlocks.setOverrideBlockTexture(defIcon);
			renderBlocks.customMetadata = null;
		}
		
		renderBlocks.renderStandardBlock(Blocks.stained_glass, x, y, z);
	}
}