package latmod.latblocks.gui;

import latmod.ftbu.core.gui.ContainerLM;
import latmod.latblocks.tile.TileQTerminal;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerQNet extends ContainerLM
{
	public ContainerQNet(EntityPlayer ep, TileQTerminal t)
	{
		super(ep, t);
		addPlayerSlots(7, 85);
	}
}