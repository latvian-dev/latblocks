package latmod.latblocks.item;
import latmod.core.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNote extends ItemLB
{
	public ItemNote(String s)
	{
		super(s);
		setMaxStackSize(1);
	}
	
	public void loadRecipes()
	{
		mod.recipes().addShapelessRecipe(new ItemStack(this),
				Items.paper,
				ODItems.SLIMEBALL,
				EnumDyeColor.BLACK.dyeName);
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		return is;
	}
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{
		return false;
	}
}