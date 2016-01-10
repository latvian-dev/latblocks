package latmod.latblocks.gui;

import ftb.lib.gui.ContainerLM;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDefaultPaint extends ContainerLM
{
	public ContainerDefaultPaint(EntityPlayer ep)
	{
		super(ep, null);
		addPlayerSlots(84);
	}
}