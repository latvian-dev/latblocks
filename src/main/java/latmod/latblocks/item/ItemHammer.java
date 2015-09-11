package latmod.latblocks.item;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.item.Tool;
import latmod.ftbu.core.util.FastList;
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
		setMaxDamage(0);
		setHarvestLevel(Tool.Type.WRENCH, Tool.Level.BASIC);
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfig.Crafting.hammer > 0)
			mod.recipes.addRecipe(new ItemStack(this), "OGO", " I ", " I ",
				'I', ODItems.IRON,
				'O', ODItems.OBSIDIAN,
				'G', (LatBlocksConfig.Crafting.hammer == 1 ? ItemMaterialsLB.GEM_GLOWIUM_D : ODItems.DIAMOND));
	}
	
	public int getDamage(ItemStack is)
	{ return 0; }
	
	public ItemStack getContainerItem(ItemStack is)
	{ return new ItemStack(this, 1, 0); }
	
	public boolean hasContainerItem(ItemStack is)
	{ return true; }
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack is)
	{ return false; }
	
	public void addRecipe(ItemStack out, Object in, Object... extra)
	{
		FastList<Object> l = new FastList<Object>();
		l.add(new ItemStack(this, 1, ODItems.ANY));
		l.add(in);
		l.addAll(extra);
		mod.recipes.addShapelessRecipe(out, l.toArray());
	}
	
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{ return true; }
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{ return false; }
}