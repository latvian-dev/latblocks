package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.ForgeWorldMP;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.capabilities.Bag;
import com.latmod.latblocks.capabilities.IBag;
import com.latmod.latblocks.capabilities.LBCapabilities;
import com.latmod.latblocks.gui.LBGuiHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 16.05.2016.
 */
public class ItemBag extends ItemLB
{
    public final int tier;
    public ItemBag nextTierBag;

    public ItemBag(int t)
    {
        tier = t;
        setMaxDamage(0);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new Bag(tier);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(LatBlocks.MOD_ID, "bag"), "inventory"));
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return 10000000;
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        return false;
    }

    @Override
    public void loadRecipes()
    {
        if(tier == 1)
        {
            getMod().recipes.addRecipe(new ItemStack(this, 1, 0),
                    "DSD", "WCW", "WQW",
                    'W', ODItems.WOOL,
                    'S', ODItems.STRING,
                    'C', ODItems.CHEST_WOOD,
                    'D', ODItems.DIAMOND,
                    'Q', ODItems.QUARTZ_BLOCK);
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack is, World w, EntityPlayer ep, EnumHand hand)
    {
        if(!w.isRemote)
        {
            IBag bag = is.getCapability(LBCapabilities.BAG, null);

            if(bag != null)
            {
                if(bag.getOwner() == null)
                {
                    bag.setOwner(ep.getGameProfile().getId());
                }

                if(bag.getPrivacyLevel().canInteract(ForgeWorldMP.inst.getPlayer(bag.getOwner()), ForgeWorldMP.inst.getPlayer(ep)))
                {
                    LBGuiHandler.INSTANCE.openGui(ep, hand == EnumHand.MAIN_HAND ? LBGuiHandler.BAG_MAIN_HAND : LBGuiHandler.BAG_OFF_HAND, null);
                }
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, is);
    }
}