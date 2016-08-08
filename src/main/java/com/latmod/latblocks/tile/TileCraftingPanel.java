package com.latmod.latblocks.tile;

import com.feed_the_beast.ftbl.api.tile.TileInvLM;

/**
 * Created by LatvianModder on 05.08.2016.
 */
public class TileCraftingPanel extends TileInvLM
{
    public TileCraftingPanel()
    {
        super(9);
    }

    @Override
    public void markDirty()
    {
        sendDirtyUpdate();
    }
}