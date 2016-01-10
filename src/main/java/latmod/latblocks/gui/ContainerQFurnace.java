package latmod.latblocks.gui;

import ftb.lib.gui.ContainerLM;
import ftb.lib.item.SlotLM;
import latmod.latblocks.tile.TileQFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerQFurnace extends ContainerLM
{
	public ContainerQFurnace(EntityPlayer ep, TileQFurnace t)
	{
		super(ep, t);
		addSlotToContainer(new SlotLM(t, TileQFurnace.SLOT_INPUT, 56, 17));
		addSlotToContainer(new SlotLM(t, TileQFurnace.SLOT_FUEL, 56, 53));
		addSlotToContainer(new SlotFurnace(ep, t, TileQFurnace.SLOT_OUTPUT, 116, 35));
		addPlayerSlots(84);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer ep, int i)
	{
		ItemStack is = null;
		Slot slot = (Slot) inventorySlots.get(i);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack is1 = slot.getStack();
			is = is1.copy();
			
			if(i == 2)
			{
				if(!mergeItemStack(is1, 3, 39, true)) return null;
				slot.onSlotChange(is1, is);
			}
			else if(i != 1 && i != 0)
			{
				if(FurnaceRecipes.smelting().getSmeltingResult(is1) != null)
				{ if(!mergeItemStack(is1, 0, 1, false)) return null; }
				else if(TileEntityFurnace.isItemFuel(is1))
				{ if(!mergeItemStack(is1, 1, 2, false)) return null; }
				else if(i >= 3 && i < 30)
				{ if(!mergeItemStack(is1, 30, 39, false)) return null; }
				else if(i >= 30 && i < 39 && !mergeItemStack(is1, 3, 30, false)) return null;
			}
			else if(!mergeItemStack(is1, 3, 39, false)) return null;
			
			if(is1.stackSize == 0) slot.putStack((ItemStack) null);
			else slot.onSlotChanged();
			if(is1.stackSize == is.stackSize) return null;
			slot.onPickupFromSlot(ep, is1);
		}
		
		return is;
	}
}