package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.gui.ContainerLM;
import com.feed_the_beast.ftbl.api.gui.GuiHandler;
import com.feed_the_beast.ftbl.api.gui.GuiHelper;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.lib.util.LMInvUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 05.08.2016.
 */
public class ContainerCraftingPanel extends ContainerLM
{
    public static final ResourceLocation ID = new ResourceLocation(LatBlocks.MOD_ID, "crafting_panel");

    @GuiHandler
    public static final IGuiHandler HANDLER = new IGuiHandler()
    {
        @Override
        public ResourceLocation getID()
        {
            return ID;
        }

        @Override
        @Nullable
        public Container getContainer(EntityPlayer player, @Nullable NBTTagCompound data)
        {
            TileEntity te = GuiHelper.getTile(player, data);
            return (te instanceof TileCraftingPanel) ? new ContainerCraftingPanel(player, (TileCraftingPanel) te) : null;
        }

        @Override
        @Nullable
        public Object getGui(EntityPlayer player, @Nullable NBTTagCompound data)
        {
            TileEntity te = GuiHelper.getTile(player, data);
            return (te instanceof TileCraftingPanel) ? new GuiCraftingPanel(new ContainerCraftingPanel(player, (TileCraftingPanel) te)) : null;
        }
    };

    private class InventoryCraftingCP extends InventoryCrafting
    {
        private InventoryCraftingCP()
        {
            super(ContainerCraftingPanel.this, 3, 3);
        }

        @Override
        public int getSizeInventory()
        {
            return 9;
        }

        @Nullable
        @Override
        public ItemStack getStackInSlot(int index)
        {
            return tile.itemHandler.getStackInSlot(index);
        }

        @Nullable
        @Override
        public ItemStack removeStackFromSlot(int index)
        {
            return LMInvUtils.getAndRemove(tile.itemHandler, index);
        }

        @Nullable
        @Override
        public ItemStack decrStackSize(int index, int count)
        {
            ItemStack itemstack = LMInvUtils.getAndSplit(tile.itemHandler, index, count);

            if(itemstack != null)
            {
                onCraftMatrixChanged(this);
            }

            return itemstack;
        }

        @Override
        public void setInventorySlotContents(int index, @Nullable ItemStack stack)
        {
            tile.itemHandler.setStackInSlot(index, stack);
            onCraftMatrixChanged(this);
        }

        @Override
        public void clear()
        {
            LMInvUtils.clear(tile.itemHandler);
            tile.markDirty();
        }
    }

    private final TileCraftingPanel tile;
    private InventoryCraftingCP craftMatrix;
    private IInventory craftResult;

    public ContainerCraftingPanel(EntityPlayer ep, TileCraftingPanel t)
    {
        super(ep);
        tile = t;

        craftMatrix = new InventoryCraftingCP();
        craftResult = new InventoryCraftResult();

        addSlotToContainer(new SlotCrafting(ep, craftMatrix, craftResult, 0, 124, 35));

        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 3; x++)
            {
                addSlotToContainer(new Slot(craftMatrix, x + y * 3, 30 + x * 18, 17 + y * 18));
            }
        }

        addPlayerSlots(8, 84, false);
        onCraftMatrixChanged(craftMatrix);
    }

    @Nullable
    @Override
    public IItemHandler getItemHandler()
    {
        return tile.itemHandler;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, tile.getWorld()));
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = inventorySlots.get(index);

        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index == 0)
            {
                if(!mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if(index >= 10 && index < 37)
            {
                if(!mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if(index >= 37 && index < 46)
            {
                if(!mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if(!mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if(itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if(itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return slotIn.inventory != craftResult && super.canMergeSlot(stack, slotIn);
    }
}