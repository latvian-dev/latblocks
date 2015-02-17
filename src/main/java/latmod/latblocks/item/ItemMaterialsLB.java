package latmod.latblocks.item;
import latmod.core.*;
import latmod.core.item.ItemMaterials;
import latmod.core.recipes.LMRecipes;
import latmod.latblocks.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemMaterialsLB extends ItemMaterials
{
	public static final ItemStack GEMS_GLOWIUM[] = new ItemStack[5];
	public static final ItemStack DUSTS_GLOWIUM[] = new ItemStack[5];
	
	public static ItemStack LENS;
	public static ItemStack ROD;
	public static ItemStack STAR_DUST;
	
	public ItemMaterialsLB(String s)
	{ super(s); }
	
	public String[] getNames()
	{
		return new String[]
		{
			"gemGlowium.yellow",
			"dustGlowium.yellow",
			"lens",
			"rod",
			"dustStar",
			"gemGlowium.red",
			"gemGlowium.green",
			"gemGlowium.blue",
			"gemGlowium.dark",
			"dustGlowium.red",
			"dustGlowium.green",
			"dustGlowium.blue",
			"dustGlowium.dark",
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
		
		ODItems.add("gemGlowium", GEMS_GLOWIUM[0] = new ItemStack(this, 1, 0));
		ODItems.add("dustGlowium", DUSTS_GLOWIUM[0] = new ItemStack(this, 1, 1));
		
		LENS = new ItemStack(this, 1, 2);
		ODItems.add("rodPaintable", ROD = new ItemStack(this, 1, 3));
		STAR_DUST = new ItemStack(this, 1, 4);
		
		for(int i = 1; i <= 4; i++)
		{
			GEMS_GLOWIUM[i] = new ItemStack(this, 1, 4 + i);
			DUSTS_GLOWIUM[i] = new ItemStack(this, 1, 8 + i);
		}
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfig.Crafting.glowiumGems)
		{
			mod.recipes.addRecipe(GEMS_GLOWIUM[0], " G ", "GQG", " G ",
					'G', ODItems.GLOWSTONE,
					'Q', ODItems.QUARTZ);
			
			mod.recipes.addRecipe(GEMS_GLOWIUM[1], " D ", "DGD", " D ",
					'D', EnumDyeColor.RED.dyeName,
					'G', GEMS_GLOWIUM[0]);
			
			mod.recipes.addRecipe(GEMS_GLOWIUM[2], " D ", "DGD", " D ",
					'D', EnumDyeColor.GREEN.dyeName,
					'G', GEMS_GLOWIUM[0]);
			
			mod.recipes.addRecipe(GEMS_GLOWIUM[3], " D ", "DGD", " D ",
					'D', EnumDyeColor.BLUE.dyeName,
					'G', GEMS_GLOWIUM[0]);
			
			mod.recipes.addRecipe(GEMS_GLOWIUM[4], " 1 ", "2O3", " 4 ",
					'O', ODItems.OBSIDIAN,
					'1', GEMS_GLOWIUM[0],
					'2', GEMS_GLOWIUM[1],
					'3', GEMS_GLOWIUM[2],
					'4', GEMS_GLOWIUM[3]);
		}
		
		if(LatBlocksConfig.Crafting.glowiumDusts)
			for(int i = 0; i < GEMS_GLOWIUM.length; i++)
				LatBlocksItems.i_hammer.addRecipe(DUSTS_GLOWIUM[i], GEMS_GLOWIUM[i]);
		
		LatBlocksItems.i_hammer.addRecipe(LENS, ODItems.GLASS);
		LatBlocksItems.i_hammer.addRecipe(LMRecipes.size(STAR_DUST, 8), Items.nether_star);
		
		mod.recipes.addRecipe(LMRecipes.size(ROD, 4), "P", "P",
				'P', LatBlocksItems.b_paintable);
	}
}