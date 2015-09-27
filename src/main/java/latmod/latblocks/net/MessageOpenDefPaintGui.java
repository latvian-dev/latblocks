package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.*;
import latmod.core.util.ByteIOStream;
import latmod.ftbu.net.MessageLM;
import latmod.latblocks.LatBlocksGuiHandler;

public class MessageOpenDefPaintGui extends MessageLM<MessageOpenDefPaintGui>
{
	public void readData(ByteIOStream io) throws Exception
	{
	}
	
	public void writeData(ByteIOStream io) throws Exception
	{
	}
	
	public IMessage onMessage(MessageOpenDefPaintGui m, MessageContext ctx)
	{ LatBlocksGuiHandler.instance.openGui(ctx.getServerHandler().playerEntity, LatBlocksGuiHandler.DEF_PAINT, null); return null; }
}