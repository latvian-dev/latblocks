package com.latmod.latblocks.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 16.07.2016.
 */
public class TileNetherChest extends TileLM implements IItemHandlerModifiable
{
    public final List<ItemStack> items;
    public short currentPage = 0;

    public TileNetherChest()
    {
        items = new ArrayList<>();
    }

    public static boolean isValidItem(@Nonnull ItemStack is)
    {
        return is.stackSize == 1 && !is.isStackable();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing side)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }

        return super.hasCapability(capability, side);
    }

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing side)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) this;
        }

        return super.getCapability(capability, side);
    }

    @Override
    public void readTileData(@Nonnull NBTTagCompound tag)
    {
        items.clear();

        NBTTagList nbt = (NBTTagList) tag.getTag("Inv");

        if(nbt != null && nbt.tagCount() > 0)
        {
            for(int i = 0; i < nbt.tagCount(); i++)
            {
                ItemStack is = ItemStack.loadItemStackFromNBT(nbt.getCompoundTagAt(i));

                if(is != null)
                {
                    items.add(is);
                }
            }
        }

        currentPage = tag.getShort("Page");
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound tag)
    {
        NBTTagList list = new NBTTagList();

        for(ItemStack is : items)
        {
            NBTTagCompound tag1 = new NBTTagCompound();
            is.writeToNBT(tag1);
            list.appendTag(tag1);
        }

        tag.setTag("Inv", list);
        tag.setShort("Page", currentPage);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        if(slot == 0)
        {
            if(stack != null && stack.stackSize == 1 && !stack.isStackable())
            {
                items.add(stack);
                markDirty();
            }
        }
        else
        {
            if(stack == null || stack.stackSize <= 0)
            {
                items.remove(slot - 1);
                markDirty();
            }
            else
            {
                items.set(slot - 1, stack);
                markDirty();
            }
        }
    }

    @Override
    public void markDirty()
    {
        sendDirtyUpdate();
    }

    @Override
    public int getSlots()
    {
        return items.size() + 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return (slot == 0) ? null : items.get(slot - 1);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        if(stack != null && isValidItem(stack))
        {
            if(!simulate)
            {
                items.add(stack);
                markDirty();
            }

            return null;
        }

        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if(slot == 0 || amount < 1)
        {
            return null;
        }

        ItemStack is = items.get(slot - 1);

        if(!simulate)
        {
            items.remove(slot - 1);
            markDirty();
        }

        return is;
    }

    @Override
    public void onPlacedBy(@Nonnull EntityLivingBase el, @Nonnull ItemStack is, @Nonnull IBlockState state)
    {
        super.onPlacedBy(el, is, state);

        if(is.hasTagCompound() && is.getTagCompound().hasKey("NetherChestData"))
        {
            readTileData(is.getTagCompound().getCompoundTag("NetherChestData"));
            markDirty();
        }
    }
}