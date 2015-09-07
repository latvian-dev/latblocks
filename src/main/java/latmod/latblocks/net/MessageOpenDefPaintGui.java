package latmod.latblocks.net;

import io.netty.buffer.ByteBuf;
import latmod.ftbu.core.net.MessageLM;
import latmod.latblocks.LatBlocksGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.*;

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