package latmod.latblocks.gui;
import latmod.core.gui.ContainerLM;
import latmod.latblocks.tile.TileLatChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerLatChest extends ContainerLM
{
	public ContainerLatChest(EntityPlayer ep, TileLatChest t)
	{
		super(ep, t);
		
		for(int y = 0; y < 6; y++) for(int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(t, x + y * 9, x * 18 + 8, y * 18 + 30));
		
		addPlayerSlots(152);
	}
}