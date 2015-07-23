package latmod.latblocks.tile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public interface IQuartzNetTile
{
	public String getQTitle();
	public int getQColor();
	public ItemStack getQIcon();
	public void onQClicked(EntityPlayerMP ep, int button);
}