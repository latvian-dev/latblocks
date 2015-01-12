package latmod.latblocks.item;
import latmod.core.*;
import latmod.core.item.ItemMaterials;
import latmod.latblocks.LatBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemMaterialsLB extends ItemMaterials
{
	public static ItemStack GEM_GLOWIUM;
	public static ItemStack DUST_GLOWIUM;
	public static ItemStack GEM_RED_GLOWIUM;
	
	public ItemMaterialsLB(String s)
	{ super(s); }
	
	public String[] getNames()
	{
		return new String[]
		{
			"gemGlowium",
			"dustGlowium",
			//"gemRedGlowium",
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
		
		LatCoreMC.addOreDictionary("gemGlowium", GEM_GLOWIUM = new ItemStack(this, 1, 0));
		LatCoreMC.addOreDictionary("dustGlowium", DUST_GLOWIUM = new ItemStack(this, 1, 1));
		//LatCoreMC.addOreDictionary("gemRedGlowium", GEM_RED_GLOWIUM = new ItemStack(this, 1, 2));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(GEM_GLOWIUM, " G ", "GQG", " G ",
				'G', ODItems.GLOWSTONE,
				'Q', ODItems.QUARTZ);
		
		mod.recipes.addShapelessRecipe(DUST_GLOWIUM, GEM_GLOWIUM);
		
		//mod.recipes.addRecipe(GEM_RED_GLOWIUM, " R ", "RGR", " R ",
		//		'G', GEM_GLOWIUM,
		//		'R', ODItems.REDSTONE);
	}
}