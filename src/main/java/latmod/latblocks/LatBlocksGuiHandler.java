package latmod.latblocks;

import latmod.core.ILMGuiHandler;
import latmod.core.gui.ContainerEmpty;
import latmod.latblocks.gui.GuiColorPainter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class LatBlocksGuiHandler implements ILMGuiHandler
{
	public static final String COLOR_PAINTER = "latblocks.color";
	public static final LatBlocksGuiHandler instance = new LatBlocksGuiHandler();
	
	public Container getContainer(EntityPlayer ep, String id, NBTTagCompound data)
	{ return new ContainerEmpty(ep, null); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, String id, NBTTagCompound data)
	{ return new GuiColorPainter(ep); }
}