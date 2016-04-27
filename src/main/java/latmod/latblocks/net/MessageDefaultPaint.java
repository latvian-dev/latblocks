package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ftb.lib.api.net.LMNetworkWrapper;
import ftb.lib.api.net.MessageLM;
import io.netty.buffer.ByteBuf;
import latmod.latblocks.LatBlockEventHandler;
import latmod.latblocks.api.Paint;
import net.minecraft.block.Block;

public class MessageDefaultPaint extends MessageLM<MessageDefaultPaint>
{
	public int[] data;
	
	public MessageDefaultPaint() { }
	
	public MessageDefaultPaint(Paint[] p)
	{
		data = new int[12];
		
		for(int i = 0; i < 6; i++)
		{
			data[i * 2 + 0] = (short) (Math.max(0, (p[i] == null) ? 0 : Block.getIdFromBlock(p[i].block)));
			data[i * 2 + 1] = (short) ((p[i] == null) ? 0 : p[i].meta);
		}
	}
	
	@Override
	public LMNetworkWrapper getWrapper()
	{ return LatBlocksNetHandler.NET; }
	
	@Override
	public void fromBytes(ByteBuf io)
	{
		data = new int[12];
		
		for(int i = 0; i < data.length; i++)
		{
			data[i] = io.readUnsignedShort();
		}
	}
	
	@Override
	public void toBytes(ByteBuf io)
	{
		for(int i = 0; i < data.length; i++)
		{
			io.writeShort(data[i]);
		}
	}
	
	@Override
	public IMessage onMessage(MessageDefaultPaint m, MessageContext ctx)
	{
		LatBlockEventHandler.LatBlockProperties props = LatBlockEventHandler.LatBlockProperties.get(ctx.getServerHandler().playerEntity);
		
		for(int i = 0; i < 6; i++)
		{
			props.paint[i] = (m.data[i * 2 + 0] == 0) ? null : new Paint(Block.getBlockById(m.data[i * 2 + 0]), m.data[i * 2 + 1]);
		}
		
		return null;
	}
}