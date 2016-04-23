package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ftb.lib.api.net.LMNetworkWrapper;
import ftb.lib.api.net.MessageLM;
import latmod.latblocks.LatBlocksGuiHandler;

public class MessageOpenDefPaintGui extends MessageLM
{
	public MessageOpenDefPaintGui() { super(null); }
	
	@Override
	public LMNetworkWrapper getWrapper()
	{ return LatBlocksNetHandler.NET; }
	
	@Override
	public IMessage onMessage(MessageContext ctx)
	{
		LatBlocksGuiHandler.instance.openGui(ctx.getServerHandler().playerEntity, LatBlocksGuiHandler.DEF_PAINT, null);
		return null;
	}
}