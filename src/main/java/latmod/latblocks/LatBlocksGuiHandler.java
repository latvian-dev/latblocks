package latmod.latblocks;

import latmod.ftbu.core.LMGuiHandler;
import latmod.ftbu.core.gui.ContainerEmpty;
import latmod.latblocks.gui.*;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.*;

public class LatBlocksGuiHandler extends LMGuiHandler
{
	public static final int COLOR_PAINTER = 1;
	public static final int QUARTZ_NET = 2;
	//public static final int QUARTZ_BAG = 3;
	
	public static final LatBlocksGuiHandler instance = new LatBlocksGuiHandler(LatBlocks.MOD_ID);
	
	public LatBlocksGuiHandler(String s)
	{ super(s); }
	
	public Container getContainer(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER)
			return new ContainerEmpty(ep, null);
		else if(id == QUARTZ_NET)
		{
			int x = data.getInteger("X");
			int y = data.getInteger("Y");
			int z = data.getInteger("Z");
			
			TileEntity te = ep.worldObj.getTileEntity(x, y, z);
			if(te != null && te instanceof TileQChest)
				return new ContainerQChestNet(ep, (TileQChest)te);
		}
		//else if(id == QUARTZ_BAG)
		//	return new ContainerQuartzBag(new InvQBag(ep, data.getInteger("ID")));
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER)
			return new GuiColorPainter(ep);
		else if(id == QUARTZ_NET)
			return new GuiQChestNet((ContainerQChestNet)getContainer(ep, id, data));
		//else if(id == QUARTZ_BAG)
		//	return new GuiQuartzBag((ContainerQuartzBag)getContainer(ep, id, data));
		
		return null;
	}
}