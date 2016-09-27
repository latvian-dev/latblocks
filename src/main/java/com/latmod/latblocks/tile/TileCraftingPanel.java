package com.latmod.latblocks.tile;

import com.feed_the_beast.ftbl.lib.tile.TileInvLM;
import net.minecraft.util.ITickable;

/**
 * Created by LatvianModder on 05.08.2016.
 */
public class TileCraftingPanel extends TileInvLM implements ITickable
{
    public TileCraftingPanel()
    {
        super(9);
    }

    @Override
    public void update()
    {
        checkIfDirty();
    }
}