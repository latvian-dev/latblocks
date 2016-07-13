package com.latmod.latblocks.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;

/**
 * Created by LatvianModder on 13.07.2016.
 */
public class TileMultiFurnace extends TileLM
{
    public class FurnPart
    {
        public boolean isActive()
        {
            return true;
        }
    }

    public FurnPart[] parts;

    public TileMultiFurnace()
    {
        parts = new FurnPart[4];

        for(int i = 0; i < parts.length; i++)
        {
            parts[i] = new FurnPart();
        }
    }
}