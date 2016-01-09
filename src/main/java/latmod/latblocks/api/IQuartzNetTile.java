package latmod.latblocks.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IQuartzNetTile
{
	ItemStack getQIconItem();
	void onQClicked(EntityPlayer ep, int button);
}