package latmod.latblocks.client.render;
import latmod.core.client.BlockRendererLM;
import latmod.core.tile.IPaintable.Paint;
import latmod.latblocks.tile.TileSinglePaintable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderCarpet extends BlockRendererLM
{
	public static final RenderCarpet instance = new RenderCarpet();
	
	public RenderCarpet()
	{
		renderBlocks.fullBlock = AxisAlignedBB.getBoundingBox(0F, 0F, 0F, 1F, 0.0625F, 1F);
	}
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = false;
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		renderBlocks.customMetadata = 0;
		renderBlocks.setOverrideBlockTexture(b.getIcon(0, 0));
		renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.blockAccess = iba;
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		
		TileSinglePaintable t = (TileSinglePaintable)iba.getTileEntity(x, y, z);
		
		if(t == null || t.isInvalid()) return false;
		
		Paint[] p = { t.paint[0], t.paint[0], t.paint[0], t.paint[0], t.paint[0], t.paint[0] };
		IIcon defIcon = b.getIcon(0, t.iconMeta());
		
		RenderPaintable.renderCube(renderBlocks, p, defIcon, x, y, z, renderBlocks.fullBlock);
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}