package latmod.latblocks.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public interface IPainterItem
{
	ItemStack getPaintItem(ItemStack is);
	boolean canPaintBlock(ItemStack is);
	void damagePainter(ItemStack is, EntityPlayer ep);
}
