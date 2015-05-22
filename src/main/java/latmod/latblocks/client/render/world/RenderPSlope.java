package latmod.latblocks.client.render.world;
import latmod.core.LatCoreMC;
import latmod.core.client.BlockRendererLM;
import latmod.core.tile.*;
import latmod.core.tile.IPaintable.Paint;
import latmod.latblocks.block.paintable.BlockPSlope;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderPSlope extends BlockRendererLM
{
	public static final RenderPSlope instance = new RenderPSlope();
	
	public RenderPSlope()
	{
	}
	
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = false;
		renderBlocks.setCustomColor(null);
		renderBlocks.setOverrideBlockTexture(b.getBlockTextureFromSide(1));
		
		GL11.glPushMatrix();
		rotateBlocks();
		
		for(int i = 0; i < 6; i++)
		{
			if(i != 0 && i != LatCoreMC.FRONT)
			{
				GL11.glPushMatrix();
				renderBlocks.setRenderBounds(renderBlocks.fullBlock);
				renderBlocks.renderBlockAsItem(Blocks.stone, 0, 1F);
				GL11.glPopMatrix();
			}
		}
		
		GL11.glPopMatrix();
	}
	
	public boolean renderWorldBlock(IBlockAccess iba, int x, int y, int z, Block b, int modelID, RenderBlocks rb)
	{
		renderBlocks.renderAllFaces = true;
		renderBlocks.blockAccess = iba;
		renderBlocks.setRenderBoundsFromBlock(b);
		renderBlocks.setCustomColor(null);
		
		BlockPSlope.TilePSlope t = (BlockPSlope.TilePSlope) iba.getTileEntity(x, y, z);
		
		if(t == null || t.isInvalid()) return false;
		
		Paint[] p = new Paint[6];
		IIcon[] defIcon = new IIcon[6];
		for(int i = 0; i < 6; i++)
		{
			boolean isSolid = t.isSolid(i);
			defIcon[i] = isSolid ? b.getBlockTextureFromSide(1) : LatCoreMC.Client.blockNullIcon;
			p[i] = isSolid ? t.getPaint(i) : null;
		}
		
		IPaintable.Renderer.renderCube(iba, renderBlocks, p, defIcon, x, y, z, renderBlocks.fullBlock);
		
		return true;
	}
	
	public boolean shouldRender3DInInventory(int renderID)
	{ return true; }
}