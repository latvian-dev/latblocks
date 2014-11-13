package latmod.latblocks;
import latmod.latblocks.client.render.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LatBlocksCommon
{
	public void preInit(FMLPreInitializationEvent e)
	{
		RenderPaintable.instance.register();
		RenderFountain.instance.register();
	}
}