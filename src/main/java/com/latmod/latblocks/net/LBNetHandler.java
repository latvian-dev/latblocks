package com.latmod.latblocks.net;

import com.feed_the_beast.ftbl.lib.net.LMNetworkWrapper;
import com.latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class LBNetHandler
{
    static final LMNetworkWrapper NET = LMNetworkWrapper.newWrapper(LatBlocks.MOD_ID);

    public static void init()
    {
        NET.register(1, new MessageChangeBagColor());
    }
}
