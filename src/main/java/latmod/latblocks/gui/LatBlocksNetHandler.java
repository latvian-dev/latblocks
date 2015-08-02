package latmod.latblocks.gui;

import latmod.ftbu.core.*;
import latmod.ftbu.core.gui.ContainerEmpty;
import latmod.ftbu.core.net.CustomActionFromClient;
import latmod.ftbu.core.paint.Paint;
import latmod.ftbu.core.tile.TileLM;
import latmod.ftbu.core.util.MathHelperLM;
import latmod.ftbu.core.world.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class LatBlocksNetHandler extends LMGuiHandler implements CustomActionFromClient
{
	public static final LatBlocksNetHandler instance = new LatBlocksNetHandler("LatBlocks");
	
	public static final int COLOR_PAINTER = 1;
	public static final int DEF_PAINT = 2;
	
	public static final String DEF_PAINT_TAG = "LB_DefPaint";
	public static boolean openDefPaintGui = false;
	public static Paint[] currentPaint = new Paint[6];
	
	public LatBlocksNetHandler(String s)
	{ super(s); }
	
	public Container getContainer(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER)
			return new ContainerEmpty(ep, null);
		else if(id == DEF_PAINT)
			return new ContainerDefaultPaint(ep);
		//else if(id == QUARTZ_BAG)
		//	return new ContainerQuartzBag(new InvQBag(ep, data.getInteger("ID")));
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int id, NBTTagCompound data)
	{
		if(id == COLOR_PAINTER)
			return new GuiColorPainter(ep);
		else if(id == DEF_PAINT)
			return new GuiDefaultPaint(new ContainerDefaultPaint(ep));
		//else if(id == QUARTZ_BAG)
		//	return new GuiQuartzBag((ContainerQuartzBag)getContainer(ep, id, data));
		
		return null;
	}
	
	public void sendToServer(EntityPlayer ep, NBTTagCompound data)
	{
		if(openDefPaintGui) data.setBoolean("G", true);
		else Paint.writeToNBT(data, "P", currentPaint);
	}
	
	public void readFromClient(EntityPlayerMP ep, NBTTagCompound data)
	{
		if(data.hasKey("G"))
			openGui(ep, DEF_PAINT, null);
		else
		{
			LMPlayerServer p = LMWorldServer.inst.getPlayer(ep);
			Paint[] paint = new Paint[6];
			Paint.readFromNBT(data, "P", paint);
			Paint.writeToNBT(p.commonData, DEF_PAINT_TAG, paint);
			p.sendUpdate(null, true);
		}
	}

	public static void setDefPaint(TileLM t, EntityPlayer ep, Paint[] paint)
	{
		LMPlayerServer player = LMWorldServer.inst.getPlayer(ep);
		Paint[] p = new Paint[6];
		Paint.readFromNBT(player.commonData, LatBlocksNetHandler.DEF_PAINT_TAG, p);
		
		if(paint.length != 6)
		{
			if(p[SidedDirection.FRONT.ID] != null)
			{
				for(int i = 0; i < paint.length; i++)
					paint[i] = p[SidedDirection.FRONT.ID].clone();
			}
		}
		else
		{
			int r3 = MathHelperLM.get3DRotation(t.getWorldObj(), t.xCoord, t.yCoord, t.zCoord, ep);
			int r2 = 0;
			
			if(r3 == 0 || r3 == 1)
				r2 = MathHelperLM.get2DRotation(ep);
			
			for(int f = 0; f < 6; f++)
			{
				SidedDirection sd = SidedDirection.get(f, r3, r2);
				//sd = SidedDirection.FRONT;
				
				if(sd != SidedDirection.NONE && p[sd.ID] != null)
					paint[f] = p[sd.ID].clone();
			}
		}
	}
}