package latmod.latblocks.api;

import cpw.mods.fml.relauncher.*;
import latmod.latblocks.client.render.world.BlockRendererLM;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

/**
 * Created by LatvianModder on 29.01.2016.
 */
@SideOnly(Side.CLIENT)
public class PaintableRenderer
{
	public static Paint currentPaint;
	
	public static Paint[] to6(Paint paint)
	{ return new Paint[] {paint, paint, paint, paint, paint, paint}; }
	
	public static void renderCube(IBlockAccess iba, BlockRendererLM.RenderBlocksCustom renderBlocks, Paint[] paint, BlockRendererLM.BlockCustom base, int xCoord, int yCoord, int zCoord, AxisAlignedBB box) {}
	
	public static void renderFace(IBlockAccess iba, BlockRendererLM.RenderBlocksCustom renderBlocks, int s, Paint p, BlockRendererLM.BlockCustom base, int x, int y, int z) {}
}
