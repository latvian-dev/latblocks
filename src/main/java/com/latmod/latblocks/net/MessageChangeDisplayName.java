package com.latmod.latblocks.net;

import com.feed_the_beast.ftbl.api.net.LMNetworkWrapper;
import com.feed_the_beast.ftbl.api.net.MessageToServer;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.gui.ContainerBag;
import com.latmod.latblocks.gui.LBGuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class MessageChangeDisplayName extends MessageToServer<MessageChangeDisplayName>
{
    public String name;

    @Override
    public LMNetworkWrapper getWrapper()
    {
        return LBNetHandler.NET;
    }

    @Override
    public void toBytes(ByteBuf io)
    {
        writeString(io, name);
    }

    @Override
    public void fromBytes(ByteBuf io)
    {
        name = readString(io);
    }

    @Override
    public void onMessage(MessageChangeDisplayName m, EntityPlayerMP ep)
    {
        if(ep.openContainer instanceof ContainerBag)
        {
            ItemStack is = ep.getHeldItem(EnumHand.MAIN_HAND);

            if(is != null && is.hasCapability(LBCapabilities.BAG, null))
            {
                is.setStackDisplayName(m.name);
                LBGuiHandler.INSTANCE.openGui(ep, LBGuiHandler.BAG, null);
            }
        }
    }
}