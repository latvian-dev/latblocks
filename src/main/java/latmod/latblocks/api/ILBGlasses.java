package latmod.latblocks.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ILBGlasses
{
	public boolean areLBGlassesActive(ItemStack is, EntityPlayer ep);
}