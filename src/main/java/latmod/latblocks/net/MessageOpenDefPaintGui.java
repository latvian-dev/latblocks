package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.*;
import ftb.lib.api.*;
import latmod.latblocks.LatBlocksGuiHandler;

public class MessageOpenDefPaintGui extends MessageLM
{
	public MessageOpenDefPaintGui()
	{ super(DATA_NONE); }
	
	public LMNetworkWrapper getWrapper()
	{ return LatBlocksNetHandler.NET; }

	public IMessage onMessage(MessageContext ctx)
	{
		LatBlocksGuiHandler.instance.openGui(ctx.getServerHandler().playerEntity, LatBlocksGuiHandler.DEF_PAINT, null);
		return null;
	}
}