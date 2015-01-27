package latmod.latblocks.item;
import latmod.core.*;
import latmod.core.item.ItemMaterials;
import latmod.latblocks.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemMaterialsLB extends ItemMaterials
{
	public static ItemStack GEM_GLOWIUM;
	public static ItemStack DUST_GLOWIUM;
	public static ItemStack LENS;
	
	public ItemMaterialsLB(String s)
	{ super(s); }
	
	public String[] getNames()
	{
		return new String[]
		{
			"gemGlowium",
			"dustGlowium",
			"lens",
		};
	}
	
	public String getPrefix()
	{ return null; }
	
	public LMMod getMod()
	{ return LatBlocks.mod; }
	
	public CreativeTabs getCreativeTab()
	{ return LatBlocks.tab; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		ODItems.add("gemGlowium", GEM_GLOWIUM = new ItemStack(this, 1, 0));
		ODItems.add("dustGlowium", DUST_GLOWIUM = new ItemStack(this, 1, 1));
		LENS = new ItemStack(this, 1, 2);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(GEM_GLOWIUM, " G ", "GQG", " G ",
				'G', ODItems.GLOWSTONE,
				'Q', ODItems.QUARTZ);
		
		LatBlocksItems.i_hammer.addRecipe(DUST_GLOWIUM, GEM_GLOWIUM);
		LatBlocksItems.i_hammer.addRecipe(LENS, ODItems.GLASS);
	}
}