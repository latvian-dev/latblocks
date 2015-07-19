package latmod.latblocks.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.*;

public interface IQuartzInventory extends IInventory
{
	public String getTitle();
	public int getColor();
	public ItemStack getIcon();
	
	@SideOnly(Side.CLIENT)
	public void openChestGui(EntityPlayer ep);
}