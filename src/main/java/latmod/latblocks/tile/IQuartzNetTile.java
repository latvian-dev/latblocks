package latmod.latblocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IQuartzNetTile
{
	public ItemStack getQIconItem();
	public void onQClicked(EntityPlayer ep, int button);
}