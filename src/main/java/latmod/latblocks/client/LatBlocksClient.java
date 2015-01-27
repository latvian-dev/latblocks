package latmod.latblocks.client;
import latmod.core.LatCoreMC;
import latmod.core.gui.ContainerEmpty;
import latmod.latblocks.LatBlocksCommon;
import latmod.latblocks.client.render.*;
import latmod.latblocks.gui.GuiColorPainter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LatBlocksCommon
{
	public void preInit(FMLPreInitializationEvent e)
	{
		LatCoreMC.addEventHandler(LatBlockClientEventHandler.instance, true, false, false);
		RenderFountain.instance.register();
		RenderPaintable.instance.register();
		RenderTank.instance.register();
		RenderGlowiumBlocks.instance.register();
	}
	
	public void openColorPainterGUI(EntityPlayer ep)
	{ Minecraft.getMinecraft().displayGuiScreen(new GuiColorPainter(new ContainerEmpty(ep, null))); }
}