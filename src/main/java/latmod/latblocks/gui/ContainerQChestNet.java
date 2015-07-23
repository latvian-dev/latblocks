package latmod.latblocks.gui;

import latmod.ftbu.core.gui.ContainerLM;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerQChestNet extends ContainerLM
{
	public ContainerQChestNet(EntityPlayer ep, TileQChest t)
	{
		super(ep, t);
		addPlayerSlots(7, 85);
	}
}