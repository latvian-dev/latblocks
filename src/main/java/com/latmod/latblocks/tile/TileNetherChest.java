package com.latmod.latblocks.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 16.07.2016.
 */
public class TileNetherChest extends TileEntity
{
    private static class UnstackableItemHandler implements IItemHandlerModifiable, INBTSerializable<NBTTagList>
    {
        private final List<ItemStack> items = new ArrayList<>();

        @Override
        public NBTTagList serializeNBT()
        {
            NBTTagList list = new NBTTagList();

            for(ItemStack is : items)
            {
                NBTTagCompound tag = new NBTTagCompound();
                is.writeToNBT(tag);
                list.appendTag(tag);
            }

            return list;
        }

        @Override
        public void deserializeNBT(NBTTagList nbt)
        {
            items.clear();

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
            if(stack != null && stack.stackSize == 1 && !stack.isStackable())
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

    private final UnstackableItemHandler itemHandler;

    public TileNetherChest()
    {
        itemHandler = new UnstackableItemHandler();
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
            return (T) itemHandler;
        }

        return super.getCapability(capability, side);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        itemHandler.deserializeNBT((NBTTagList) compound.getTag("Inv"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound tag = super.writeToNBT(compound);
        tag.setTag("Inv", itemHandler.serializeNBT());
        return tag;
    }
}