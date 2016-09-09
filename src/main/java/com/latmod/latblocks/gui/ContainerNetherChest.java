package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.gui.ContainerLM;
import com.latmod.latblocks.tile.TileNetherChest;
import com.latmod.lib.util.LMInvUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class ContainerNetherChest extends ContainerLM
{
    private static class SlotNetherChest extends Slot
    {
        public final TileNetherChest tile;

        private SlotNetherChest(TileNetherChest t, int index, int xPosition, int yPosition)
        {
            super(LMInvUtils.EMPTY_INVENTORY, index, xPosition, yPosition);
            tile = t;
        }

        public int getItemIndex()
        {
            return tile.currentPage * 40 + getSlotIndex();
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return stack != null && TileNetherChest.isValidItem(stack);
        }

        @Override
        public ItemStack getStack()
        {
            int index = getItemIndex();
            return (index >= 0 && index < tile.items.size()) ? tile.items.get(index) : null;
        }

        @Override
        public void putStack(ItemStack stack)
        {
            int index = getItemIndex();

            if(stack == null || stack.stackSize <= 0)
            {
                if(index >= 0 && index < tile.items.size())
                {
                    tile.items.remove(index);
                    onSlotChanged();
                }
            }
            else
            {
                if(index >= 0 && index < tile.items.size())
                {
                    tile.items.set(index, stack);
                    onSlotChanged();
                }
                else
                {
                    tile.items.add(stack);
                    onSlotChanged();
                }
            }
        }

        @Override
        public void onSlotChanged()
        {
            tile.markDirty();
        }

        @Override
        public void onSlotChange(ItemStack is1, ItemStack is2)
        {
            onSlotChanged();
        }

        @Override
        public int getItemStackLimit(ItemStack stack)
        {
            return 1;
        }

        @Override
        public boolean canTakeStack(EntityPlayer playerIn)
        {
            return true;
        }

        @Override
        public ItemStack decrStackSize(int amount)
        {
            int index = getItemIndex();

            if(index >= 0 && index < tile.items.size() && amount == 1)
            {
                ItemStack is = tile.items.get(index);
                tile.items.remove(index);
                tile.markDirty();
                return is;
            }

            return null;
        }

        @Override
        public boolean isSameInventory(Slot other)
        {
            return other instanceof SlotNetherChest && ((SlotNetherChest) other).tile == tile;
        }
    }

    public final TileNetherChest tile;
    private int lastPage = -1;

    public ContainerNetherChest(EntityPlayer ep, TileNetherChest c)
    {
        super(ep);
        tile = c;

        for(int y = 0; y < 5; y++)
        {
            for(int x = 0; x < 8; x++)
            {
                addSlotToContainer(new SlotNetherChest(tile, x + y * 8, 7 + x * 18, 7 + y * 18));
            }
        }

        addPlayerSlots(7, 106, false);
    }

    @Override
    public boolean enchantItem(EntityPlayer ep, int id)
    {
        if(!ep.worldObj.isRemote)
        {
            int ppage = tile.currentPage;

            if(id == 0)
            {
                tile.currentPage--;
            }
            else if(id == 1)
            {
                tile.currentPage++;
            }

            int max = getMaxPages();
            tile.currentPage %= max;

            if(tile.currentPage < 0)
            {
                tile.currentPage += max;
            }

            if(tile.currentPage != ppage)
            {
                tile.markDirty();
                return true;
            }
        }

        return false;
    }

    public int getMaxPages()
    {
        return MathHelper.ceiling_float_int((tile.items.size() + 1F) / 40F);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return tile;
    }

    @Override
    protected int getNonPlayerSlots()
    {
        return 40;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(IContainerListener l : listeners)
        {
            if(lastPage != tile.currentPage)
            {
                l.sendProgressBarUpdate(this, 0, tile.currentPage);
            }
        }

        lastPage = tile.currentPage;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        switch(id)
        {
            case 0:
                tile.currentPage = (short) data;
        }
    }
}