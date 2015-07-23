package latmod.latblocks.item.bag;

import net.minecraft.item.ItemStack;

public class InvQItems
{
	public static final int INV_W = 12;
	public static final int INV_H = 6;
	
	public final int bagID;
	public final ItemStack[] stacks;
	
	public InvQItems(int id)
	{
		bagID = id;
		stacks = new ItemStack[INV_W * INV_H];
	}
	
	public void update()
	{ QBagDataHandler.set(bagID, this); }
}