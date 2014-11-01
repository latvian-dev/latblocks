package latmod.latblocks;
import latmod.core.LMProxy;
import latmod.core.client.LatCoreMCClient;
import latmod.latblocks.block.BlockPaintable;
import latmod.latblocks.client.render.RenderPaintable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LMProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		BlockPaintable.renderID = LatCoreMCClient.getNewBlockRenderID();
		LatCoreMCClient.addBlockRenderer(BlockPaintable.renderID, new RenderPaintable());
	}
}