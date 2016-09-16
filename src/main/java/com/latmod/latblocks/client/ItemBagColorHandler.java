package com.latmod.latblocks.client;

import com.feed_the_beast.ftbl.client.FTBLibColors;
import com.latmod.latblocks.capabilities.LBCapabilities;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 11.07.2016.
 */
@SideOnly(Side.CLIENT)
public class ItemBagColorHandler implements IItemColor
{
    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex)
    {
        if(stack.hasCapability(LBCapabilities.BAG, null))
        {
            return 0xFF | FTBLibColors.get(stack.getCapability(LBCapabilities.BAG, null).getColorID());
        }

        return 0xFFFFFFFF;
    }
}