package latmod.latblocks.item.bag;

import latmod.ftbu.core.inv.LMInvUtils;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InvQBag implements IInventory
{
	public final EntityPlayer player;
	public final InvQItems items;
	
	public InvQBag(EntityPlayer ep, int id)
	{
		player = ep;
		items = (id == 0) ? new InvQItems(0) : QBagDataHandler.getItems(id);
	}
	
	public ItemStack getItem()
	{
		ItemStack is = player.inventory.getCurrentItem();
		return (is != null && is.getItem() == LatBlocksItems.i_qbag && is.hasTagCompound() && is.getTagCompound().hasKey(ItemQBag.TAG_DATA)) ? is : null;
	}
	
	public int getSizeInventory()
	{ return items.stacks.length; }
	
	public ItemStack getStackInSlot(int i)
	{ return items.stacks[i]; }
	
	public ItemStack decrStackSize(int slot, int amt)
	{ return LMInvUtils.decrStackSize(this, slot, amt); }
	
	public ItemStack getStackInSlotOnClosing(int i)
	{ return LMInvUtils.getStackInSlotOnClosing(this, i); }
	
	public void setInventorySlotContents(int i, ItemStack is)
	{ items.stacks[i] = is; markDirty(); }
	
	public String getInventoryName()
	{ return getItem().getDisplayName(); }
	
	public boolean hasCustomInventoryName()
	{ return true; }
	
	public int getInventoryStackLimit()
	{ return 64; }
	
	public void markDirty()
	{ items.update(); }
	
	public boolean isUseableByPlayer(EntityPlayer ep)
	{
		ItemStack parent = getItem();
		if(parent == null) return true;
		return ItemQBag.getSecurity(parent).canInteract(ep);
	}
	
	public void openInventory() { }
	
	public void closeInventory()
	{ markDirty(); }
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return is.getItem() != LatBlocksItems.i_qbag; }
}