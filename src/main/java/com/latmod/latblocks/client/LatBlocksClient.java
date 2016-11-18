package com.latmod.latblocks.client;

import com.latmod.latblocks.LatBlocksCommon;
import com.latmod.latblocks.block.LBBlocks;
import com.latmod.latblocks.item.LBItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
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
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LBBlocks.nether_chest), 0, new ModelResourceLocation(LBBlocks.nether_chest.getRegistryName(), "normal"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LBBlocks.crafting_panel), 0, new ModelResourceLocation(LBBlocks.crafting_panel.getRegistryName(), "facing=down"));

        for(Item item : LBItems.ITEMS.values())
        {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }

    @Override
    public void postInit()
    {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemBagColorHandler(), LBItems.bag);
    }
}