package latmod.latblocks.client;
import latmod.core.LMProxy;
import latmod.latblocks.client.render.*;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LMProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new LatBlockClientEventHandler());
		
		RenderFountain.instance.register();
		RenderPaintable.instance.register();
	}
}