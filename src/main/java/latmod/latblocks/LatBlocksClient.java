package latmod.latblocks;
import net.minecraftforge.common.MinecraftForge;
import latmod.core.LMProxy;
import latmod.latblocks.client.LatBlockClientEventHandler;
import latmod.latblocks.client.render.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LMProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new LatBlockClientEventHandler());
		
		RenderPaintable.instance.register();
		RenderFountain.instance.register();
		RenderCarpet.instance.register();
	}
}