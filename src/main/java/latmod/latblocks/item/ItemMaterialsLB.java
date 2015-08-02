package latmod.latblocks.item;
import latmod.ftbu.core.*;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.item.*;
import latmod.latblocks.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
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
	public static MaterialItem DUST_STAR;
	
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
		add(GEM_GLOWIUM_Y = new MaterialItem(this, 0, "gem_yellow")
		{
			public void onPostLoaded()
			{ ODItems.add("gemGlowium", getStack()); }
			
			public void loadRecipes()
			{
				mod.recipes.addRecipe(getStack(), "QG",
						'G', ODItems.GLOWSTONE,
						'Q', ODItems.QUARTZ);
			}
		});
		
		add(GEM_GLOWIUM_R = new MaterialItem(this, 5, "gem_red")
		{
			public void loadRecipes()
			{ mod.recipes.addShapelessRecipe(getStack(), GEM_GLOWIUM_Y, EnumDyeColor.RED.dyeName); }
		});
		
		add(GEM_GLOWIUM_G = new MaterialItem(this, 6, "gem_green")
		{
			public void loadRecipes()
			{ mod.recipes.addShapelessRecipe(getStack(), GEM_GLOWIUM_Y, EnumDyeColor.GREEN.dyeName); }
		});
		
		add(GEM_GLOWIUM_B = new MaterialItem(this, 7, "gem_blue")
		{
			public void loadRecipes()
			{ mod.recipes.addShapelessRecipe(getStack(), GEM_GLOWIUM_Y, EnumDyeColor.BLUE.dyeName); }
		});
		
		add(GEM_GLOWIUM_D = new MaterialItem(this, 8, "gem_dark")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(getStack(), " 1 ", "2O3", " 4 ",
						'O', ODItems.OBSIDIAN,
						'1', GEM_GLOWIUM_Y,
						'2', GEM_GLOWIUM_R,
						'3', GEM_GLOWIUM_G,
						'4', GEM_GLOWIUM_B);
			}
		});
		
		add(DUST_GLOWIUM_Y = new MaterialItem(this, 1, "dust_yellow")
		{
			public void onPostLoaded()
			{ ODItems.add("dustGlowium", getStack()); }
			
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_Y); }
		});
		
		add(DUST_GLOWIUM_R = new MaterialItem(this, 9, "dust_red")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_R); }
		});
		
		add(DUST_GLOWIUM_G = new MaterialItem(this, 10, "dust_green")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_G); }
		});
		
		add(DUST_GLOWIUM_B = new MaterialItem(this, 11, "dust_blue")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_B); }
		});
		
		add(DUST_GLOWIUM_D = new MaterialItem(this, 12, "dust_dark")
		{
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_D); }
		});
		
		add(LENS = new MaterialItem(this, 2, "lens")
		{
			public void loadRecipes()
			{
				LatBlocksItems.i_hammer.addRecipe(getStack(), ODItems.GLASS);
			}
		});
		
		add(ROD = new MaterialItem(this, 3, "rod")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(getStack(4), "P", "P",
						'P', LatBlocksItems.b_paintable);
			}
		});
		
		add(DUST_STAR = new MaterialItem(this, 4, "dust_star")
		{
			public void onPostLoaded()
			{ ODItems.add("dustLMStar", getStack()); }
			
			public void loadRecipes()
			{
				LatBlocksItems.i_hammer.addRecipe(getStack(8), Items.nether_star, ODItems.GLOWSTONE);
				
				if(LatBlocksConfig.Crafting.easyStartDust) LatBlocksItems.i_hammer.addRecipe(getStack(8),
						ODItems.DIAMOND, ODItems.DIAMOND, ODItems.DIAMOND, ODItems.GLOWSTONE);
			}
		});
		
		add(PAINT_ROLLER_ROD = new MaterialItem(this, 13, "painter_rod")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(getStack(), "  I", " SI", "S  ",
						'I', ODItems.IRON,
						'S', ROD);
			}
		});
		
		add(PAINT_ROLLER = new MaterialItem(this, 14, "paint_roller_basic")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(getStack(), "WWW", "PIP", "WWW",
						'W', new ItemStack(Blocks.carpet, 1, 0),
						'I', ODItems.IRON,
						'P', Items.paper);
			}
		});
		
		add(PAINT_ROLLER_DMD = new MaterialItem(this, 15, "paint_roller_dmd")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(getStack(), "D", "R", "D",
						'D', ODItems.DIAMOND,
						'R', PAINT_ROLLER);
			}
		});
		
		add(PAINT_ROLLER_COLOR = new MaterialItem(this, 16, "paint_roller_color")
		{
			public void loadRecipes()
			{
				mod.recipes.addRecipe(getStack(), "123", "8R4", "765",
						'R', PAINT_ROLLER_DMD,
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