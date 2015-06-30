package latmod.latblocks.gui;
import latmod.ftbu.core.gui.ContainerLM;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerQChest extends ContainerLM
{
	public ContainerQChest(EntityPlayer ep, TileQChest t)
	{
		super(ep, t);
		
		for(int y = 0; y < TileQChest.INV_H; y++) for(int x = 0; x < TileQChest.INV_W; x++)
			addSlotToContainer(new Slot(t, x + y * TileQChest.INV_H, x * 18 + 8, y * 18 + 30));
		
		addPlayerSlots(44, 165);
		
		t.openInventory();
	}
	
	public void onContainerClosed(EntityPlayer ep)
	{
		super.onContainerClosed(ep);
		((TileQChest)inv).closeInventory();
    }
}