package latmod.latblocks.client.render.world;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public class BlockRendererLM implements ISimpleBlockRenderingHandler
{
	public RenderBlocksCustom renderBlocks = new RenderBlocksCustom();
	
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		
	}
	
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}
	
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}
	
	public int getRenderId()
	{
		return 0;
	}
	
	public static class BlockCustom extends Block
	{
		protected BlockCustom()
		{
			super(Material.glass);
		}
	}
	
	public static class RenderBlocksCustom extends RenderBlocks
	{
		public int currentSide = -1;
		public AxisAlignedBB fullBlock;
		
		public void setCustomColor(Object customColor)
		{
		}
		
		public void setRenderBounds(AxisAlignedBB b)
		{
		}
		
		public void setInst(IBlockAccess inst)
		{
		}
		
		public void setFaceBounds(int s, AxisAlignedBB b)
		{
		}
	}
}
