package com.latmod.latblocks.client;

import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.LatBlocksCommon;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 15.05.2016.
 */
@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LatBlocksCommon
{
    @Override
    public void postInit()
    {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemBagColorHandler(), LatBlocks.Items.bag);
    }
}