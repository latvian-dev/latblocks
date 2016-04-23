package latmod.latblocks.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnderBag extends ItemLB
{
	public ItemEnderBag(String s)
	{
		super(s);
		setMaxStackSize(1);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "LSL", "LCL", "LLL", 'L', Items.leather, 'S', Items.string, 'C', Blocks.ender_chest);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote) ep.displayGUIChest(ep.getInventoryEnderChest());
		return is;
	}
}