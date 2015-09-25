package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import latmod.ftbu.net.MessageLM;
import latmod.ftbu.paint.Paint;
import latmod.ftbu.world.*;
import latmod.latblocks.LatBlocksGuiHandler;
import net.minecraft.block.Block;

public class MessageDefaultPaint extends MessageLM<MessageDefaultPaint>
{
	public final int[] paintIntArray;
	
	public MessageDefaultPaint()
	{ paintIntArray = new int[12]; }
	
	public MessageDefaultPaint(Paint[] p)
	{
		this();
		
		for(int i = 0; i < 6; i++)
		{
			paintIntArray[i * 2 + 0] = Math.max(0, (p[i] == null) ? 0 : Block.getIdFromBlock(p[i].block));
			paintIntArray[i * 2 + 1] = (p[i] == null) ? 0 : p[i].meta;
		}
	}
	
	public void fromBytes(ByteBuf bb)
	{
		for(int i = 0; i < paintIntArray.length; i++)
			paintIntArray[i] = bb.readShort();
	}
	
	public void toBytes(ByteBuf bb)
	{
		for(int i = 0; i < paintIntArray.length; i++)
			bb.writeShort(paintIntArray[i]);
	}
	
	public IMessage onMessage(MessageDefaultPaint m, MessageContext ctx)
	{
		LMPlayerServer p = LMWorldServer.inst.getPlayer(ctx.getServerHandler().playerEntity);
		p.commonPrivateData.setIntArray(LatBlocksGuiHandler.DEF_PAINT_TAG, m.paintIntArray);
		p.sendUpdate(true);
		return null;
	}
}