package latmod.latblocks;

import latmod.ftbu.core.LMGuiHandler;
import latmod.ftbu.core.gui.ContainerEmpty;
import latmod.latblocks.gui.*;
import latmod.latblocks.item.InvQuartzBag;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class LatBlocksGuiHandler extends LMGuiHandler
{
	public static final int COLOR_PAINTER = 1;
	public static final int QUARTZ_BAG = 2;
	
	public static final LatBlocksGuiHandler instance = new LatBlocksGuiHandler(LatBlocks.MOD_ID);
	
	public LatBlocksGuiHandler(String s)
	{ super(s); }
	
	public Container getContainer(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER)
			return new ContainerEmpty(ep, null);
		else if(id == QUARTZ_BAG)
			return new ContainerQuartzBag(new InvQuartzBag(ep));
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER)
			return new GuiColorPainter(ep);
		else if(id == QUARTZ_BAG)
			return new GuiQuartzBag((ContainerQuartzBag)getContainer(ep, id, data));
		
		return null;
	}
}