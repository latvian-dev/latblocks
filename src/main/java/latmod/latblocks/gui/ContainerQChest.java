package latmod.latblocks.gui;

import ftb.lib.api.gui.ContainerLM;
import ftb.lib.api.item.SlotLM;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerQChest extends ContainerLM
{
	public ContainerQChest(EntityPlayer ep, TileQChest t)
	{
		super(ep, t);
		
		for(int y = 0; y < TileQChest.INV_H; y++)
			for(int x = 0; x < TileQChest.INV_W; x++)
				addSlotToContainer(new SlotLM(t, x + y * TileQChest.INV_W, x * 18 + 8, y * 18 + 30));
		
		addPlayerSlots(44, 165);
		
		t.openInventory();
	}
	
	@Override
	public void onContainerClosed(EntityPlayer ep)
	{
		super.onContainerClosed(ep);
	}
}