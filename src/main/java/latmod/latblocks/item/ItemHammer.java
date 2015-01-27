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
		setMaxDamage(512);
		setHarvestLevel("wrench", 0);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "IGI", " S ", " S ",
				'I', ODItems.IRON,
				'G', ItemMaterialsLB.GEM_GLOWIUM,
				'S', ODItems.STICK);
	}
	
	public ItemStack getContainerItem(ItemStack is)
	{
		if(is.getItemDamage() > getMaxDamage())
			return null;
		return new ItemStack(this, 1, is.getItemDamage() + 1);
	}
	
	public boolean hasContainerItem(ItemStack is)
	{ return true; }
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack is)
	{ return false; }
	
	public void addRecipe(ItemStack out, Object in)
	{ mod.recipes.addShapelessRecipe(out, new ItemStack(this, 1, ODItems.ANY), in); }
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{
		return false;
	}
}