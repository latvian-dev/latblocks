package latmod.latblocks.client;
import latmod.core.*;
import latmod.latblocks.client.render.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LMProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		LatCoreMC.addEventHandler(LatBlockClientEventHandler.instance, true, false, false);
		RenderFountain.instance.register();
		RenderPaintable.instance.register();
		RenderTank.instance.register();
		RenderGlowiumBlocks.instance.register();
	}
}