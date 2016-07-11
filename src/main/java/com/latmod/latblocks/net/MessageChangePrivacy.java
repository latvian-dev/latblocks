package com.latmod.latblocks.net;

import com.feed_the_beast.ftbl.api.net.LMNetworkWrapper;
import com.feed_the_beast.ftbl.api.net.MessageToServer;
import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.latmod.latblocks.gui.ContainerBag;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class MessageChangePrivacy extends MessageToServer<MessageChangePrivacy>
{
    public int level;

    @Override
    public LMNetworkWrapper getWrapper()
    {
        return LBNetHandler.NET;
    }

    @Override
    public void toBytes(ByteBuf io)
    {
        io.writeByte(level);
    }

    @Override
    public void fromBytes(ByteBuf io)
    {
        level = io.readUnsignedByte();
    }

    @Override
    public void onMessage(MessageChangePrivacy m, EntityPlayerMP mp)
    {
        if(mp.openContainer instanceof ContainerBag)
        {
            ((ContainerBag) mp.openContainer).bag.setPrivacyLevel(EnumPrivacyLevel.VALUES[m.level]);
            //new MessageUpdateHeldItem(mp, ((ContainerBag) mp.openContainer).hand).sendTo(mp);
            mp.openContainer.detectAndSendChanges();
        }
    }
}