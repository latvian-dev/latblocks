package com.latmod.latblocks.client;

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
            return stack.getCapability(LBCapabilities.BAG, null).getColor();
        }

        return 0xFFFFFFFF;
    }
}