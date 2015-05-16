package latmod.latblocks.item;
import latmod.core.ODItems;
import latmod.core.item.Tool;
import latmod.latblocks.LatBlocksConfig;
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
		setHarvestLevel(Tool.Type.WRENCH, Tool.Level.BASIC);
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfig.Crafting.hammer > 0)
			mod.recipes.addRecipe(new ItemStack(this), "IGI", " S ", " S ",
				'I', ODItems.IRON,
				'G', (LatBlocksConfig.Crafting.hammer == 1 ? ItemMaterialsLB.GEMS_GLOWIUM[4] : ODItems.DIAMOND),
				'S', ItemMaterialsLB.ROD);
	}
	
	public ItemStack getContainerItem(ItemStack is)
	{
		if(is.getItemDamage() > getMaxDamage()) return null;
		return new ItemStack(this, 1, is.getItemDamage() + 1);
	}
	
	public boolean hasContainerItem(ItemStack is)
	{ return true; }
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack is)
	{ return false; }
	
	public void addRecipe(ItemStack out, Object in)
	{ mod.recipes.addShapelessRecipe(out, new ItemStack(this, 1, ODItems.ANY), in); }
	
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{
		return true;
	}
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{
		return false;
	}
}