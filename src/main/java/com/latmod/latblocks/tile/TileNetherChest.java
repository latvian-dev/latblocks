package com.latmod.latblocks.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
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
public class TileNetherChest extends TileEntity implements IItemHandlerModifiable
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
    public void readFromNBT(@Nonnull NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        items.clear();

        NBTTagList nbt = (NBTTagList) compound.getTag("Inv");

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

        currentPage = compound.getShort("Page");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound tag = super.writeToNBT(compound);

        NBTTagList list = new NBTTagList();

        for(ItemStack is : items)
        {
            NBTTagCompound tag1 = new NBTTagCompound();
            is.writeToNBT(tag1);
            list.appendTag(tag1);
        }

        tag.setTag("Inv", list);

        tag.setShort("Page", currentPage);
        return tag;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        if(slot == 0)
        {
            if(stack != null && stack.stackSize == 1 && !stack.isStackable())
            {
                items.add(stack);
            }
        }
        else
        {
            if(stack == null || stack.stackSize <= 0)
            {
                items.remove(slot - 1);
            }
            else
            {
                items.set(slot - 1, stack);
            }
        }
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
        }

        return is;
    }
}