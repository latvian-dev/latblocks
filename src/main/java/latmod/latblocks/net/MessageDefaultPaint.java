package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.*;
import ftb.lib.api.*;
import latmod.ftbu.api.paint.Paint;
import latmod.ftbu.world.*;
import latmod.latblocks.LatBlocksGuiHandler;
import latmod.lib.Converter;
import net.minecraft.block.Block;

public class MessageDefaultPaint extends MessageLM
{
	public MessageDefaultPaint() { super(DATA_SHORT); }
	
	public MessageDefaultPaint(Paint[] p)
	{
		this();
		
		short a[] = new short[12];
		
		for(int i = 0; i < 6; i++)
		{
			a[i * 2 + 0] = (short)(Math.max(0, (p[i] == null) ? 0 : Block.getIdFromBlock(p[i].block)));
			a[i * 2 + 1] = (short)((p[i] == null) ? 0 : p[i].meta);
		}
		
		for(int i = 0; i < 12; i++)
			io.writeShort(a[i]);
	}
	
	public LMNetworkWrapper getWrapper()
	{ return LatBlocksNetHandler.NET; }
	
	public IMessage onMessage(MessageContext ctx)
	{
		short a[] = new short[12];
		
		for(int i = 0; i < a.length; i++)
			a[i] = io.readShort();
		
		LMPlayerServer p = LMWorldServer.inst.getPlayer(ctx.getServerHandler().playerEntity);
		p.commonPrivateData.setIntArray(LatBlocksGuiHandler.DEF_PAINT_TAG, Converter.toInts(a));
		p.sendUpdate();
		return null;
	}
}