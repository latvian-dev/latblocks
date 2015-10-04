package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import latmod.core.util.Converter;
import latmod.ftbu.net.MessageLM;
import latmod.ftbu.paint.Paint;
import latmod.ftbu.world.*;
import latmod.latblocks.LatBlocksGuiHandler;
import net.minecraft.block.Block;

public class MessageDefaultPaint extends MessageLM<MessageDefaultPaint>
{
	public final short[] paintIntArray;
	
	public MessageDefaultPaint()
	{ paintIntArray = new short[12]; }
	
	public MessageDefaultPaint(Paint[] p)
	{
		this();
		
		for(int i = 0; i < 6; i++)
		{
			paintIntArray[i * 2 + 0] = (short)(Math.max(0, (p[i] == null) ? 0 : Block.getIdFromBlock(p[i].block)));
			paintIntArray[i * 2 + 1] = (short)((p[i] == null) ? 0 : p[i].meta);
		}
	}
	
	public void fromBytes(ByteBuf io)
	{
		for(int i = 0; i < paintIntArray.length; i++)
			paintIntArray[i] = io.readShort();
	}
	
	public void toBytes(ByteBuf io)
	{
		for(int i = 0; i < paintIntArray.length; i++)
			io.writeShort(paintIntArray[i]);
	}
	
	public IMessage onMessage(MessageDefaultPaint m, MessageContext ctx)
	{
		LMPlayerServer p = LMWorldServer.inst.getPlayer(ctx.getServerHandler().playerEntity);
		p.commonPrivateData.setIntArray(LatBlocksGuiHandler.DEF_PAINT_TAG, Converter.toInts(m.paintIntArray));
		p.sendUpdate();
		return null;
	}
}