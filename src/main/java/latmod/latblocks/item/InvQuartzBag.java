package latmod.latblocks.item;

import latmod.ftbu.core.InvUtils;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InvQuartzBag implements IInventory
{
	public static final int INV_W = 12;
	public static final int INV_H = 6;
	
	public final EntityPlayer player;
	public final ItemStack[] items;
	
	public InvQuartzBag(EntityPlayer ep)
	{
		player = ep;
		items = new ItemStack[INV_W * INV_H];
		
		NBTTagCompound tag = ItemQuartzBag.getData(getItem());
		if(tag != null) InvUtils.readItemsFromNBT(items, tag, ItemQuartzBag.TAG_INV);
	}
	
	public ItemStack getItem()
	{
		ItemStack is = player.inventory.getCurrentItem();
		return (is != null && is.getItem() == LatBlocksItems.i_qbag && is.hasTagCompound() && is.getTagCompound().hasKey(ItemQuartzBag.TAG_DATA)) ? is : null;
	}
	
	public int getSizeInventory()
	{ return items.length; }
	
	public ItemStack getStackInSlot(int i)
	{ return items[i]; }
	
	public ItemStack decrStackSize(int slot, int amt)
	{ return InvUtils.decrStackSize(this, slot, amt); }
	
	public ItemStack getStackInSlotOnClosing(int i)
	{ return InvUtils.getStackInSlotOnClosing(this, i); }
	
	public void setInventorySlotContents(int i, ItemStack is)
	{ items[i] = is; markDirty(); }
	
	public String getInventoryName()
	{ return getItem().getDisplayName(); }
	
	public boolean hasCustomInventoryName()
	{ return true; }
	
	public int getInventoryStackLimit()
	{ return 64; }
	
	public void markDirty()
	{
		ItemStack parent = getItem();
		if(parent == null) return;
		NBTTagCompound tag = ItemQuartzBag.getData(parent);
		if(tag == null) return;
		InvUtils.writeItemsToNBT(items, tag, ItemQuartzBag.TAG_INV);
		ItemQuartzBag.setData(parent, tag);
	}
	
	public boolean isUseableByPlayer(EntityPlayer ep)
	{
		ItemStack parent = getItem();
		if(parent == null) return true;
		return ItemQuartzBag.getSecurity(parent).canInteract(ep);
	}
	
	public void openInventory() { }
	
	public void closeInventory()
	{ markDirty(); }
	
	public boolean isItemValidForSlot(int i, ItemStack is)
	{ return is.getItem() != LatBlocksItems.i_qbag; }
}