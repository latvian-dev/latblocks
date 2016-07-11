package com.latmod.latblocks.client;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.latmod.latblocks.LatBlocksCommon;
import com.latmod.latblocks.item.LBItems;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 15.05.2016.
 */
@SideOnly(Side.CLIENT)
public class LatBlocksClient extends LatBlocksCommon
{
    @Override
    public void preInit()
    {
        //ModelLoaderRegistry.registerLoader(new PaintableBlockModelLoader());
    }

    @Override
    public void postInit()
    {
        FTBLibClient.mc().getItemColors().registerItemColorHandler(new ItemBagColorHandler(), LBItems.BAG);
    }
}