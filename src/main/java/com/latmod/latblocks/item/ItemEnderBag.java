package com.latmod.latblocks.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 16.05.2016.
 */
public class ItemEnderBag extends ItemLB
{
    public ItemEnderBag()
    {
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack is, World w, EntityPlayer ep, EnumHand hand)
    {
        if(!w.isRemote)
        {
            ep.displayGUIChest(ep.getInventoryEnderChest());
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, is);
    }
}