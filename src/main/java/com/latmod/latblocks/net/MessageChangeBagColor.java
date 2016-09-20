package com.latmod.latblocks.net;

import com.feed_the_beast.ftbl.api.net.LMNetworkWrapper;
import com.feed_the_beast.ftbl.api.net.MessageToServer;
import com.latmod.latblocks.gui.ContainerBag;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by LatvianModder on 20.09.2016.
 */
public class MessageChangeBagColor extends MessageToServer<MessageChangeBagColor>
{
    private byte colorID;

    public MessageChangeBagColor()
    {
    }

    public MessageChangeBagColor(byte b)
    {
        colorID = b;
    }

    @Override
    public LMNetworkWrapper getWrapper()
    {
        return LBNetHandler.NET;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        colorID = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(colorID);
    }

    @Override
    public void onMessage(MessageChangeBagColor m, EntityPlayerMP player)
    {
        if(player.openContainer instanceof ContainerBag)
        {
            ((ContainerBag) player.openContainer).bag.setColorID(m.colorID);
            player.openContainer.detectAndSendChanges();
        }
    }
}
