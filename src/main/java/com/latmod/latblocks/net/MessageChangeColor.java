package com.latmod.latblocks.net;

import com.feed_the_beast.ftbl.api.net.LMNetworkWrapper;
import com.feed_the_beast.ftbl.api.net.MessageToServer;
import com.latmod.latblocks.gui.ContainerBag;
import com.latmod.latblocks.gui.LBGuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class MessageChangeColor extends MessageToServer<MessageChangeColor>
{
    public int color;

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
    public void onMessage(MessageChangeColor m, EntityPlayerMP ep)
    {
        if(ep.openContainer instanceof ContainerBag)
        {
            ((ContainerBag) ep.openContainer).bag.setColor(m.color);
            LBGuiHandler.INSTANCE.openGui(ep, LBGuiHandler.BAG, null);
        }
    }
}