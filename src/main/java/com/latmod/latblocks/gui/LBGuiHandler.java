package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.client.gui.LMGuiHandler;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.item.LBItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class LBGuiHandler extends LMGuiHandler
{
    public static final LMGuiHandler INSTANCE = new LBGuiHandler();

    public static final int BAG = 0;

    private LBGuiHandler()
    {
        super(LatBlocks.MOD_ID);
    }

    @Override
    public Container getContainer(@Nonnull EntityPlayer ep, int id, @Nullable NBTTagCompound data)
    {
        switch(id)
        {
            case BAG:
            {
                ItemStack is = ep.getHeldItem(EnumHand.MAIN_HAND);

                if(is != null && is.hasCapability(LBCapabilities.BAG, null))
                {
                    return new ContainerBag(ep, is.getCapability(LBCapabilities.BAG, null));
                }

                break;
            }
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(@Nonnull EntityPlayer ep, int id, @Nullable NBTTagCompound data)
    {
        switch(id)
        {
            case BAG:
            {
                ItemStack is = ep.getHeldItem(EnumHand.MAIN_HAND);

                if(is != null && is.getItem() == LBItems.BAG && is.hasCapability(LBCapabilities.BAG, null))
                {
                    return new GuiBag(new ContainerBag(ep, is.getCapability(LBCapabilities.BAG, null))).getWrapper();
                }

                break;
            }
        }

        return null;
    }
}
