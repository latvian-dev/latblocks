package latmod.latblocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.*;

public interface IQuartzNetTile
{
	public String getQTitle();
	public int getQColor();
	public ItemStack getQIcon();
	
	@SideOnly(Side.CLIENT)
	public void openQGui(EntityPlayer ep);
}