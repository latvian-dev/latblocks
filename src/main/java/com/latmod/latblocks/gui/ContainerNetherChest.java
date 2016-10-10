package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.gui.GuiHandler;
import com.feed_the_beast.ftbl.api.gui.IGuiHandler;
import com.feed_the_beast.ftbl.lib.gui.ContainerLM;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.feed_the_beast.ftbl.lib.util.LMInvUtils;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public class ContainerNetherChest extends ContainerLM
{
    public static final ResourceLocation ID = new ResourceLocation(LatBlocks.MOD_ID, "nether_chest");

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
            return (te instanceof TileNetherChest) ? new ContainerNetherChest(player, (TileNetherChest) te) : null;
        }

        @Override
        @Nullable
        public Object getGui(EntityPlayer player, @Nullable NBTTagCompound data)
        {
            TileEntity te = GuiHelper.getTile(player, data);
            return (te instanceof TileNetherChest) ? new GuiNetherChest(new ContainerNetherChest(player, (TileNetherChest) te)) : null;
        }
    };

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
            return tile.currentPage * TileNetherChest.SLOTS + getSlotIndex();
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return stack != null && TileNetherChest.isValidItem(stack);
        }

        @Override
        public ItemStack getStack()
        {
            return tile.getItemStack(getItemIndex());
        }

        @Override
        public void putStack(@Nullable ItemStack stack)
        {
            tile.setItemStack(getItemIndex(), stack);
            onSlotChanged();
        }

        @Override
        public void onSlotChanged()
        {
            //tile.markDirty();
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
            return amount == 1 ? tile.setItemStack(getItemIndex(), null) : null;
        }

        @Override
        public boolean isSameInventory(Slot other)
        {
            return other instanceof SlotNetherChest && ((SlotNetherChest) other).tile == tile;
        }
    }

    public final TileNetherChest tile;
    private short lastPage = -1;
    private short maxPages = -1;

    public ContainerNetherChest(EntityPlayer ep, TileNetherChest c)
    {
        super(ep);
        tile = c;

        for(int y = 0; y < TileNetherChest.HEIGHT; y++)
        {
            for(int x = 0; x < TileNetherChest.WIDTH; x++)
            {
                addSlotToContainer(new SlotNetherChest(tile, x + y * TileNetherChest.WIDTH, 7 + x * 18, 7 + y * 18));
            }
        }

        addPlayerSlots(7, 106, false);
    }

    @Override
    public boolean enchantItem(EntityPlayer ep, int id)
    {
        if(!ep.worldObj.isRemote)
        {
            if(id == 0)
            {
                tile.currentPage--;
            }
            else if(id == 1)
            {
                tile.currentPage++;
            }

            tile.currentPage %= tile.maxPages;

            while(tile.currentPage < 0)
            {
                tile.currentPage += tile.maxPages;
            }

            detectAndSendChanges();
            return true;
        }

        return false;
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
        return TileNetherChest.SLOTS;
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        tile.updateMaxPages();

        for(IContainerListener l : listeners)
        {
            if(lastPage != tile.currentPage)
            {
                l.sendProgressBarUpdate(this, 0, tile.currentPage);
            }

            if(maxPages != tile.maxPages)
            {
                l.sendProgressBarUpdate(this, 1, tile.maxPages);
            }
        }

        lastPage = tile.currentPage;
        maxPages = tile.maxPages;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        switch(id)
        {
            case 0:
                tile.currentPage = (short) data;
            case 1:
                tile.maxPages = (short) data;
        }
    }
}