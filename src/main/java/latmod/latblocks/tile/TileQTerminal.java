package latmod.latblocks.tile;

import cpw.mods.fml.relauncher.*;
import ftb.lib.FTBLib;
import ftb.lib.api.gui.IGuiTile;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.api.IQuartzNetTile;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileQTerminal extends TileLM implements IGuiTile
{
	public static final String BUTTON_QNET = "qnet.open";
	
	public int sorting;
	
	public void readTileData(NBTTagCompound tag)
	{
		sorting = tag.getByte("Sorting");
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		if(sorting > 0) tag.setByte("Sorting", (byte)sorting);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{ if(isServer()) FTBLib.openGui(ep, this, null); return true; }
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerQNet(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiQNet(new ContainerQNet(ep, this)); }
	
	public void handleButton(String button, int mouseButton, NBTTagCompound data, EntityPlayerMP ep)
	{
		if(button.equals(BUTTON_QNET))
		{
			int[] ai = data.getIntArray("Data");
			TileEntity te = worldObj.getTileEntity(ai[0], ai[1], ai[2]);
			if(te != null && te instanceof IQuartzNetTile)
				((IQuartzNetTile)te).onQClicked(ep, ai[3]);
		}
	}
}