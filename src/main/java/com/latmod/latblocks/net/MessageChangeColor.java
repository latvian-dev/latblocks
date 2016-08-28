package com.latmod.latblocks.net;

import com.feed_the_beast.ftbl.api.net.LMNetworkWrapper;
import com.feed_the_beast.ftbl.api.net.MessageToServer;
import com.latmod.latblocks.gui.ContainerBag;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class MessageChangeColor extends MessageToServer<MessageChangeColor>
{
    private int color;

    public MessageChangeColor()
    {
    }

    public MessageChangeColor(int c)
    {
        color = c;
    }

    @Override
    public LMNetworkWrapper getWrapper()
    {
        return LBNetHandler.NET;
    }

    @Override
    public void toBytes(ByteBuf io)
    {
        io.writeInt(color);
    }

    @Override
    public void fromBytes(ByteBuf io)
    {
        color = io.readInt();
    }

    @Override
    public void onMessage(MessageChangeColor m, EntityPlayerMP player)
    {
        if(player.openContainer instanceof ContainerBag)
        {
            ((ContainerBag) player.openContainer).bag.setColor(m.color);
        }
    }
}