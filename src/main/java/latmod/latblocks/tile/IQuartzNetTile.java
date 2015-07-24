package latmod.latblocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IQuartzNetTile
{
	public String getQTitle();
	public int getQColor();
	public ItemStack getQIcon();
	public void onQClicked(EntityPlayer ep, int button);
}