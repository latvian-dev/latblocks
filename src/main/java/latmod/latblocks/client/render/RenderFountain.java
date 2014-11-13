package latmod.latblocks.client.render;
import latmod.core.client.BlockRendererLM;
import latmod.core.util.FastList;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.tile.TileFountain;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderFountain extends BlockRendererLM
{
	public static final RenderFountain instance = new RenderFountain();
	
	private static final FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
	private static final FastList<AxisAlignedBB> water_boxes = new FastList<AxisAlignedBB>();
	
	static { refreshBoxes(); }
	
	private static void refreshBoxes()
	{
		boxes.clear();
		water_boxes.clear();
		
		//double p = 1D / 8D;
		//boxes.add(AxisAlignedBB.getBoundingBox(0.5D - p, 0D, 0.5D - p, 0.5D + p, 1D, 0.5D + p));
		
		{
			double ob = 0D;
			double b = 1D / 16D;
			double s = 0D;
			
			double ob1 = 1D - ob;
			double sh = s + 0.15D;
			
			boxes.add(AxisAlignedBB.getBoundingBox(ob, s, ob, ob1, sh, ob1));
			
			water_boxes.add(AxisAlignedBB.getBoundingBox(b, sh, b, ob1 - b, sh + b, ob1 - b));
			
			boxes.add(AxisAlignedBB.getBoundingBox(ob, sh, ob, ob1, sh + b * 2D, b));
			boxes.add(AxisAlignedBB.getBoundingBox(ob, sh, ob1 - b, ob1, sh + b * 2D, ob1));
			
			boxes.add(AxisAlignedBB.getBoundingBox(ob, sh, ob, b, sh + b * 2D, ob1));
			boxes.add(AxisAlignedBB.getBoundingBox(1D - b, sh, ob, ob1, sh + b * 2D, ob1));
		}
		/*
		{
			double ob = 0.25D;
			double b = 1D / 16D;
			double s = 0.35D;
			
			double ob1 = 1D - ob;
			double sh = s + 0.1D;
			
			boxes.add(AxisAlignedBB.getBoundingBox(ob, s, ob, ob1, sh, ob1));
			
			water_boxes.add(AxisAlignedBB.getBoundingBox(b, sh, b, ob1 - b, sh + b, ob1 - b));
			
			boxes.add(AxisAlignedBB.getBoundingBox(ob, sh, ob, ob1, sh + b * 2D, b));
			boxes.add(AxisAlignedBB.getBoundingBox(ob, sh, ob1 - b, ob1, sh + b * 2D, ob1));
			
			boxes.add(AxisAlignedBB.getBoundingBox(ob, sh, ob, b, sh + b * 2D, ob1));
			boxes.add(AxisAlignedBB.getBoundingBox(1D - b, sh, ob, ob1, sh + b * 2D, ob1));
		}
		*/
	}
	
	public Block renderBlock = new BlockCustom()
	{
		public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
		{ return true; }
		
		public int getRenderBlockPass()
		{ return 0; }
	};
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.customMetadata = 0;
		renderBlocks.setCustomColor(null);
		
		for(int i = 0; i < boxes.size(); i++)
		{
			renderBlocks.setRenderBounds(boxes.get(i));
			renderBlocks.renderBlockAsItem(LatBlocksItems.b_fountain, 0, 1F);
		}
		
		for(int i = 0; i < water_boxes.size(); i++)
		{
			renderBlocks.setRenderBounds(water_boxes.get(i));
			renderBlocks.renderBlockAsItem(Blocks.flowing_water, 0, 1F);
		}
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.blockAccess = iba;
		
		TileFountain t = (TileFountain)iba.getTileEntity(x, y, z);
		if(t == null || t.isInvalid()) return false;
		
		refreshBoxes();
		
		for(int i = 0; i < boxes.size(); i++)
		{
			renderBlocks.setRenderBounds(boxes.get(i));
			renderBlock(t);
		}
		
		if(t.tank.hasFluid())
		{
			for(int i = 0; i < water_boxes.size(); i++)
			{
				renderBlocks.setRenderBounds(water_boxes.get(i));
				renderWaterBlock(t);
			}
		}
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
	
	public void renderBlock(TileFountain t)
	{
		if(t.paint[0] != null)
		{
			renderBlocks.setCustomColor(t.paint[0].block.colorMultiplier(t.getWorldObj(), t.xCoord, t.yCoord, t.zCoord));
			renderBlocks.clearOverrideBlockTexture();
			renderBlocks.renderStandardBlock(t.paint[0].block, t.xCoord, t.yCoord, t.zCoord);
		}
		else
		{
			renderBlocks.setCustomColor(null);
			renderBlocks.clearOverrideBlockTexture();
			renderBlocks.renderStandardBlock(t.getBlockType(), t.xCoord, t.yCoord, t.zCoord);
		}
	}
	
	public void renderWaterBlock(TileFountain t)
	{
		renderBlocks.setCustomColor(Blocks.flowing_water.colorMultiplier(t.getWorldObj(), t.xCoord, t.yCoord, t.zCoord));
		renderBlocks.clearOverrideBlockTexture();
		renderBlocks.renderStandardBlock(Blocks.flowing_water, t.xCoord, t.yCoord, t.zCoord);
	}
}