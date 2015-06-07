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
		
		for(int y = 0; y < 7; y++) for(int x = 0; x < 13; x++)
			addSlotToContainer(new Slot(t, x + y * 13, x * 18 + 8, y * 18 + 30));
		
		addPlayerSlots(44, 165);
	}
}