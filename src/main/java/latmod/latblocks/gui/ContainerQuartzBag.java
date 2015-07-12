package latmod.latblocks.gui;
import latmod.ftbu.core.gui.ContainerLM;
import latmod.latblocks.item.InvQuartzBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerQuartzBag extends ContainerLM
{
	public ContainerQuartzBag(InvQuartzBag inv)
	{
		super(inv.player, inv);
		
		for(int y = 0; y < InvQuartzBag.INV_H; y++) for(int x = 0; x < InvQuartzBag.INV_W; x++)
			addSlotToContainer(new Slot(inv, x + y * InvQuartzBag.INV_W, x * 18 + 8, y * 18 + 30));
		
		addPlayerSlots(35, 147, true);
	}
	
	public void onContainerClosed(EntityPlayer ep)
	{
		super.onContainerClosed(ep);
		((InvQuartzBag)inv).closeInventory();
    }
}