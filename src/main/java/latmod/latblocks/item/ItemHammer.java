package latmod.latblocks.item;
import latmod.core.ODItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHammer extends ItemLB
{
	public ItemHammer(String s)
	{
		super(s);
		setMaxStackSize(1);
		setFull3D();
		setHarvestLevel("wrench", 0);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "IGI", " S ", " S ",
				'I', ODItems.IRON,
				'G', ItemMaterialsLB.GEM_GLOWIUM,
				'S', ODItems.STICK);
	}
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{
		return true;
	}
}