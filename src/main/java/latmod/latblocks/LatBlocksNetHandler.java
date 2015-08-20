package latmod.latblocks;

import latmod.ftbu.core.*;
import latmod.ftbu.core.gui.ContainerEmpty;
import latmod.ftbu.core.net.CustomActionFromClient;
import latmod.ftbu.core.paint.Paint;
import latmod.ftbu.core.tile.TileLM;
import latmod.ftbu.core.util.MathHelperLM;
import latmod.ftbu.core.world.*;
import latmod.latblocks.gui.*;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.*;
import net.minecraft.init.Blocks;
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
		else
		{
			int ai[] = new int[12];
			for(int i = 0; i < 6; i++)
			{
				if(currentPaint[i] != null)
				{
					ai[i * 2 + 0] = Block.getIdFromBlock(currentPaint[i].block);
					ai[i * 2 + 1] = currentPaint[i].meta;
				}
			}
			
			data.setIntArray("P", ai);
		}
	}
	
	public void readFromClient(EntityPlayerMP ep, NBTTagCompound data)
	{
		if(data.hasKey("G"))
			openGui(ep, DEF_PAINT, null);
		else
		{
			LMPlayerServer p = LMWorldServer.inst.getPlayer(ep);
			p.commonPrivateData.setIntArray(DEF_PAINT_TAG, data.getIntArray("P"));
			p.sendUpdate(true);
		}
	}
	
	public static void setDefPaint(TileLM t, EntityPlayer ep, Paint[] paint)
	{
		if(ep.worldObj.isRemote) return;
		LMPlayerServer player = LMWorldServer.inst.getPlayer(ep);
		Paint[] p = new Paint[6];
		int[] ai = player.commonPrivateData.getIntArray(LatBlocksNetHandler.DEF_PAINT_TAG);
		if(ai.length != 12) return;
		
		for(int i = 0; i < 6; i++)
		{
			Block b = Block.getBlockById(ai[i * 2 + 0]);
			if(b != Blocks.air) p[i] = new Paint(b, ai[i * 2 + 1]);
		}
		
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
				
				if(sd != SidedDirection.NONE && p[sd.ID] != null)
					paint[f] = p[sd.ID].clone();
			}
		}
	}
}