package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.lib.block.BlockLM;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockLB extends BlockLM
{
    public BlockLB(Material m)
    {
        super(m);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return LatBlocks.INST.tab;
    }
}