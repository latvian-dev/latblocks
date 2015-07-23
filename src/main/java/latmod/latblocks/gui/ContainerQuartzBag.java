package latmod.latblocks.gui;
import latmod.ftbu.core.gui.ContainerLM;
import latmod.ftbu.core.inv.SlotLM;
import latmod.latblocks.item.bag.*;

public class ContainerQuartzBag extends ContainerLM
{
	public ContainerQuartzBag(InvQBag inv)
	{
		super(inv.player, inv);
		
		for(int y = 0; y < InvQItems.INV_H; y++) for(int x = 0; x < InvQItems.INV_W; x++)
			addSlotToContainer(new SlotLM(inv, x + y * InvQItems.INV_W, x * 18 + 8, y * 18 + 30));
		
		addPlayerSlots(35, 147, true);
	}
}