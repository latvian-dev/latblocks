package latmod.latblocks;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.gui.*;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class LatBlocksGuiHandler extends LMGuiHandler
{
	public static final LatBlocksGuiHandler instance = new LatBlocksGuiHandler("LatBlocks");
	
	public static final int COLOR_PAINTER = 1;
	public static final int DEF_PAINT = 2;
	
	public static final String DEF_PAINT_TAG = "LB_DefPaint";
	
	public LatBlocksGuiHandler(String s)
	{ super(s); }
	
	public Container getContainer(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER) return new ContainerEmpty(ep, null);
		else if(id == DEF_PAINT) return new ContainerDefaultPaint(ep);
		//else if(id == QUARTZ_BAG)
		//	return new ContainerQuartzBag(new InvQBag(ep, data.getInteger("ID")));
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER) return new GuiColorPainter(ep);
		else if(id == DEF_PAINT) return new GuiDefaultPaint(new ContainerDefaultPaint(ep));
		//else if(id == QUARTZ_BAG)
		//	return new GuiQuartzBag((ContainerQuartzBag)getContainer(ep, id, data));
		
		return null;
	}
}