package latmod.latblocks;
import latmod.core.LMProxy;
import latmod.latblocks.client.render.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LMProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		RenderPaintable.instance.register();
		RenderFountain.instance.register();
		RenderCarpet.instance.register();
	}
}