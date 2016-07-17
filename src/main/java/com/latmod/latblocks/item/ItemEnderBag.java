package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

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
    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(LatBlocks.MOD_ID, "bag"), "inventory"));
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this, 1, 0),
                "LSL", "LCL", "LLL",
                'L', ODItems.LEATHER,
                'S', ODItems.STRING,
                'C', "chestEnder");
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack is, World w, EntityPlayer ep, EnumHand hand)
    {
        if(!w.isRemote)
        {
            ep.displayGUIChest(ep.getInventoryEnderChest());
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, is);
    }
}