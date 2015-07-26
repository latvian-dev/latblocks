package latmod.latblocks.item;
import latmod.ftbu.core.*;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.item.*;
import latmod.ftbu.core.recipes.LMRecipes;
import latmod.latblocks.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import cpw.mods.fml.relauncher.*;

public class ItemMaterialsLB extends ItemMaterialsLM
{
	public static MaterialItem GEM_GLOWIUM_Y;
	public static MaterialItem GEM_GLOWIUM_R;
	public static MaterialItem GEM_GLOWIUM_G;
	public static MaterialItem GEM_GLOWIUM_B;
	public static MaterialItem GEM_GLOWIUM_D;
	
	public static MaterialItem DUST_GLOWIUM_Y;
	public static MaterialItem DUST_GLOWIUM_R;
	public static MaterialItem DUST_GLOWIUM_G;
	public static MaterialItem DUST_GLOWIUM_B;
	public static MaterialItem DUST_GLOWIUM_D;
	
	public static MaterialItem LENS;
	public static MaterialItem ROD;
	public static MaterialItem STAR_DUST;
	
	public static MaterialItem PAINT_ROLLER_ROD;
	public static MaterialItem PAINT_ROLLER;
	public static MaterialItem PAINT_ROLLER_DMD;
	public static MaterialItem PAINT_ROLLER_COLOR;
	
	public ItemMaterialsLB(String s)
	{ super(s); }
	
	public LMMod getMod()
	{ return LatBlocks.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return LatBlocks.tab; }
	
	public void onPostLoaded() // LastID = 16
	{
		add(GEM_GLOWIUM_Y = new MaterialItem(0, "gem_yellow")
		{
			public void onPostLoaded()
			{ ODItems.add("gemGlowium", stack); }
			
			public void loadRecipes()
			{
				mod.recipes.addRecipe(stack, " G ", "GQG", " G ",
						'G', ODItems.GLOWSTONE,
						'Q', ODItems.QUARTZ);
			}
		});
		
		add(GEM_GLOWIUM_R = new MaterialItem(5, "gem_red")
		{
			public void loadRecipes()
			{ mod.recipes.addShapelessRecipe(stack, GEM_GLOWIUM_Y.stack, EnumDyeColor.RED.dyeName); }
		});
		
		add(GEM_GLOWIUM_G = new MaterialItem(6, "gem_green")
		{
			public void loadRecipes()
			{ mod.recipes.addShapelessRecipe(stack, GEM_GLOWIUM_Y.stack, EnumDyeColor.GREEN.dyeName); }
		});
		
		add(GEM_GLOWIUM_B = new MaterialItem(7, "gem_blue")
		{
			public void loadRecipes()
			{ mod.recipes.addShapelessRecipe(stack, GEM_GLOWIUM_Y.stack, EnumDyeColor.BLUE.dyeName); }
		});
		
		add(GEM_GLOWIUM_D = new MaterialItem(8, "gem_dark")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(stack, " 1 ", "2O3", " 4 ",
						'O', ODItems.OBSIDIAN,
						'1', GEM_GLOWIUM_Y.stack,
						'2', GEM_GLOWIUM_R.stack,
						'3', GEM_GLOWIUM_G.stack,
						'4', GEM_GLOWIUM_B.stack);
			}
		});
		
		add(DUST_GLOWIUM_Y = new MaterialItem(1, "dust_yellow")
		{
			public void onPostLoaded()
			{ ODItems.add("dustGlowium", stack); }
			
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(stack, GEM_GLOWIUM_Y.stack); }
		});
		
		add(DUST_GLOWIUM_R = new MaterialItem(9, "dust_red")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(stack, GEM_GLOWIUM_R.stack); }
		});
		
		add(DUST_GLOWIUM_G = new MaterialItem(10, "dust_green")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(stack, GEM_GLOWIUM_G.stack); }
		});
		
		add(DUST_GLOWIUM_B = new MaterialItem(11, "dust_blue")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(stack, GEM_GLOWIUM_B.stack); }
		});
		
		add(DUST_GLOWIUM_D = new MaterialItem(12, "dust_dark")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(stack, GEM_GLOWIUM_D.stack); }
		});
		
		add(LENS = new MaterialItem(2, "lens")
		{
			public void onPostLoaded()
			{ ODItems.add("dustGlowium", stack); }
			
			public void loadRecipes()
			{
				LatBlocksItems.i_hammer.addRecipe(stack, ODItems.GLASS);
			}
		});
		
		add(ROD = new MaterialItem(3, "rod")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(LMRecipes.size(stack, 4), "P", "P",
						'P', LatBlocksItems.b_paintable);
			}
		});
		
		add(STAR_DUST = new MaterialItem(4, "dust_star")
		{
			public void onPostLoaded()
			{ ODItems.add("dustLMStar", stack); }
			
			public void loadRecipes()
			{
				LatBlocksItems.i_hammer.addRecipe(LMRecipes.size(stack, 8), Items.nether_star, ODItems.GLOWSTONE);
				
				if(LatBlocksConfig.Crafting.easyStartDust) LatBlocksItems.i_hammer.addRecipe(LMRecipes.size(stack, 8),
						ODItems.DIAMOND, ODItems.DIAMOND, ODItems.DIAMOND, ODItems.GLOWSTONE);
			}
		});
		
		add(PAINT_ROLLER_ROD = new MaterialItem(13, "painter_rod")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(stack, "  I", " SI", "S  ",
						'I', ODItems.IRON,
						'S', ROD.stack);
			}
		});
		
		add(PAINT_ROLLER = new MaterialItem(14, "paint_roller_basic")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(LMRecipes.size(stack, 6), "WWW", "III", "WWW",
						'W', ODItems.WOOL_WHITE,
						'I', ODItems.IRON);
			}
		});
		
		add(PAINT_ROLLER_DMD = new MaterialItem(15, "paint_roller_dmd")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(stack, "DDD", "DRD", "DDD",
						'D', ODItems.DIAMOND,
						'R', PAINT_ROLLER.stack);
			}
		});
		
		add(PAINT_ROLLER_COLOR = new MaterialItem(16, "paint_roller_color")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(stack, "123", "8R4", "765",
						'R', PAINT_ROLLER_DMD.stack,
						'1', EnumDyeColor.BLUE.dyeName,
						'2', EnumDyeColor.CYAN.dyeName,
						'3', EnumDyeColor.GREEN.dyeName,
						'4', EnumDyeColor.LIME.dyeName,
						'5', EnumDyeColor.YELLOW.dyeName,
						'6', EnumDyeColor.ORANGE.dyeName,
						'7', EnumDyeColor.RED.dyeName,
						'8', EnumDyeColor.PURPLE.dyeName);
			}
		});
		
		super.onPostLoaded();
	}
}