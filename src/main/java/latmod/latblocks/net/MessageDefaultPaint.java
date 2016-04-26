package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ftb.lib.api.net.LMNetworkWrapper;
import ftb.lib.api.net.MessageLM_IO;
import latmod.latblocks.LatBlockEventHandler;
import latmod.latblocks.api.Paint;
import latmod.lib.ByteCount;
import net.minecraft.block.Block;

public class MessageDefaultPaint extends MessageLM_IO
{
	public MessageDefaultPaint() { super(ByteCount.BYTE); }
	
	public MessageDefaultPaint(Paint[] p)
	{
		this();
		
		short a[] = new short[12];
		
		for(int i = 0; i < 6; i++)
		{
			a[i * 2 + 0] = (short) (Math.max(0, (p[i] == null) ? 0 : Block.getIdFromBlock(p[i].block)));
			a[i * 2 + 1] = (short) ((p[i] == null) ? 0 : p[i].meta);
		}
		
		for(int i = 0; i < 12; i++)
			io.writeShort(a[i]);
	}
	
	@Override
	public LMNetworkWrapper getWrapper()
	{ return LatBlocksNetHandler.NET; }
	
	@Override
	public IMessage onMessage(MessageContext ctx)
	{
		short a[] = new short[12];
		
		for(int i = 0; i < a.length; i++)
			a[i] = io.readShort();
		
		LatBlockEventHandler.LatBlockProperties props = LatBlockEventHandler.LatBlockProperties.get(ctx.getServerHandler().playerEntity);
		
		for(int i = 0; i < 6; i++)
		{
			props.paint[i] = (a[i * 2 + 0] == 0) ? null : new Paint(Block.getBlockById(a[i * 2 + 0]), a[i * 2 + 1]);
		}
		
		return null;
	}
}