package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import latmod.ftbu.net.MessageLM;
import latmod.latblocks.LatBlocksGuiHandler;

public class MessageOpenDefPaintGui extends MessageLM<MessageOpenDefPaintGui>
{
	public void fromBytes(ByteBuf bb)
	{
	}
	
	public void toBytes(ByteBuf bb)
	{
	}
	
	public IMessage onMessage(MessageOpenDefPaintGui m, MessageContext ctx)
	{ LatBlocksGuiHandler.instance.openGui(ctx.getServerHandler().playerEntity, LatBlocksGuiHandler.DEF_PAINT, null); return null; }
}