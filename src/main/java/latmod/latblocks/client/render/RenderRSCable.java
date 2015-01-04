package latmod.latblocks.client.render;
import latmod.core.client.BlockRendererLM;
import latmod.core.tile.IPaintable;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockRSCable;
import latmod.latblocks.tile.TileRSCable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderRSCable extends BlockRendererLM
{
	public static final RenderRSCable instance = new RenderRSCable();
	
	public AxisAlignedBB cableBoxes[] = new AxisAlignedBB[6];
	
	public RenderRSCable()
	{
		updateBoxes();
	}
	
	public void updateBoxes()
	{
		{
			double s = BlockRSCable.pipeBorder;
			
			addBox(cableBoxes, ForgeDirection.WEST, 0D, s, s, s, 1D - s, 1D - s);
			addBox(cableBoxes, ForgeDirection.EAST, 1D - s, s, s, 1D, 1D - s, 1D - s);
			addBox(cableBoxes, ForgeDirection.DOWN, s, 0D, s, 1D - s, s, 1D - s);
			addBox(cableBoxes, ForgeDirection.UP, s, 1D - s, s, 1D - s, 1D, 1D - s);
			addBox(cableBoxes, ForgeDirection.NORTH, s, s, 0D, 1D - s, 1D - s, s);
			addBox(cableBoxes, ForgeDirection.SOUTH, s, s, 1D - s, 1D - s, 1D - s, 1D);
		}
	}
	
	private void addBox(AxisAlignedBB[] b, ForgeDirection d, double x1, double y1, double z1, double x2, double y2, double z2)
	{ b[d.ordinal()] = AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2); }
	
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer0)
	{
		renderBlocks.setOverrideBlockTexture(LatBlocksItems.b_rs_cable.icon_on);
		
		double s = BlockRSCable.pipeBorder;
		renderBlocks.setRenderBounds(s, s, 0D, 1D - s, 1D - s, 1D);
		renderBlocks.renderBlockAsItem(block, metadata, 1F);
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block block, int modelId, RenderBlocks renderer0)
	{
		updateBoxes();
		
		renderBlocks.blockAccess = iba;
		
		TileRSCable t = (TileRSCable)iba.getTileEntity(x, y, z);
		
		if(t.hasCover)
			IPaintable.Renderer.renderCube(iba, renderBlocks, t.paint, IPaintable.Renderer.to6(LatBlocksItems.b_paintable.getBlockIcon()), x, y, z, renderBlocks.fullBlock);
		
		renderBlocks.renderAllFaces = true;
		
		renderBlocks.setOverrideBlockTexture(t.power ? LatBlocksItems.b_rs_cable.icon_on : LatBlocksItems.b_rs_cable.getBlockIcon());
		
		double s = BlockRSCable.pipeBorder;
		renderBlocks.setRenderBounds(s, s, s, 1F - s, 1F - s, 1F - s);
		renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
		
		for(int i = 0; i < 6; i++)
		{
			if(TileRSCable.connectCable(t, ForgeDirection.VALID_DIRECTIONS[i]))
			{
				renderBlocks.setRenderBounds(cableBoxes[i]);
				renderBlocks.renderStandardBlock(Blocks.stone, x, y, z);
			}
		}
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int modelId)
	{ return true; }
}