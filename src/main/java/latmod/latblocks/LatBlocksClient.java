package latmod.latblocks;
import latmod.core.client.LatCoreMCClient;
import latmod.latblocks.block.BlockPaintable;
import latmod.latblocks.client.render.RenderPaintable;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LatBlocksCommon
{
	public void preInit()
	{
		BlockPaintable.renderID = LatCoreMCClient.getNewBlockRenderID();
		LatCoreMCClient.addBlockRenderer(BlockPaintable.renderID, new RenderPaintable());
	}

	public void init()
	{
	}

	public void postInit()
	{
	}
}