package latmod.latblocks.item;

import ftb.lib.EnumMCColor;
import ftb.lib.LMMod;
import ftb.lib.api.item.ItemMaterialsLM;
import ftb.lib.api.item.MaterialItem;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
	{
		super(s);
		setCreativeTab(LatBlocks.tab);
	}
	
	@Override
	public LMMod getMod()
	{ return LatBlocks.mod; }
	
	@Override
	public void onPostLoaded() // LastID = 16
	{
		add(GEM_GLOWIUM_Y = new MaterialItem(this, 0, "gem_yellow")
		{
			@Override
			public void onPostLoaded()
			{ ODItems.add("gemGlowium", getStack()); }
			
			@Override
			public void loadRecipes()
			{
				getMod().recipes.addRecipe(getStack(), "QG", 'G', ODItems.GLOWSTONE, 'Q', ODItems.QUARTZ);
			}
		});
		
		add(GEM_GLOWIUM_R = new MaterialItem(this, 5, "gem_red")
		{
			@Override
			public void loadRecipes()
			{ getMod().recipes.addShapelessRecipe(getStack(), GEM_GLOWIUM_Y, EnumMCColor.RED.dyeName); }
		});
		
		add(GEM_GLOWIUM_G = new MaterialItem(this, 6, "gem_green")
		{
			@Override
			public void loadRecipes()
			{ getMod().recipes.addShapelessRecipe(getStack(), GEM_GLOWIUM_Y, EnumMCColor.GREEN.dyeName); }
		});
		
		add(GEM_GLOWIUM_B = new MaterialItem(this, 7, "gem_blue")
		{
			@Override
			public void loadRecipes()
			{ getMod().recipes.addShapelessRecipe(getStack(), GEM_GLOWIUM_Y, EnumMCColor.BLUE.dyeName); }
		});
		
		add(GEM_GLOWIUM_D = new MaterialItem(this, 8, "gem_dark")
		{
			@Override
			public void loadRecipes()
			{
				getMod().recipes.addRecipe(getStack(), " 1 ", "2O3", " 4 ", 'O', ODItems.OBSIDIAN, '1', GEM_GLOWIUM_Y, '2', GEM_GLOWIUM_R, '3', GEM_GLOWIUM_G, '4', GEM_GLOWIUM_B);
			}
		});
		
		add(DUST_GLOWIUM_Y = new MaterialItem(this, 1, "dust_yellow")
		{
			@Override
			public void onPostLoaded()
			{ ODItems.add("dustGlowium", getStack()); }
			
			@Override
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_Y); }
		});
		
		add(DUST_GLOWIUM_R = new MaterialItem(this, 9, "dust_red")
		{
			@Override
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_R); }
		});
		
		add(DUST_GLOWIUM_G = new MaterialItem(this, 10, "dust_green")
		{
			@Override
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_G); }
		});
		
		add(DUST_GLOWIUM_B = new MaterialItem(this, 11, "dust_blue")
		{
			@Override
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_B); }
		});
		
		add(DUST_GLOWIUM_D = new MaterialItem(this, 12, "dust_dark")
		{
			@Override
			public void loadRecipes()
			{ LatBlocksItems.i_hammer.addRecipe(getStack(), GEM_GLOWIUM_D); }
		});
		
		add(LENS = new MaterialItem(this, 2, "lens")
		{
			@Override
			public void loadRecipes()
			{
				LatBlocksItems.i_hammer.addRecipe(getStack(), ODItems.GLASS);
			}
		});
		
		add(ROD = new MaterialItem(this, 3, "rod")
		{
			@Override
			public void loadRecipes()
			{
				getMod().recipes.addRecipe(getStack(4), "P", "P", 'P', LatBlocksItems.b_paintable);
			}
		});
		
		add(DUST_STAR = new MaterialItem(this, 4, "dust_star")
		{
			@Override
			public void onPostLoaded()
			{ ODItems.add("dustLMStar", getStack()); }
			
			@Override
			public void loadRecipes()
			{
				LatBlocksItems.i_hammer.addRecipe(getStack(8), Items.nether_star, ODItems.GLOWSTONE);
				
				if(LatBlocksConfigCrafting.easyStartDust.getAsBoolean())
					LatBlocksItems.i_hammer.addRecipe(getStack(8), ODItems.DIAMOND, ODItems.DIAMOND, ODItems.DIAMOND, ODItems.GLOWSTONE);
			}
		});
		
		add(PAINT_ROLLER_ROD = new MaterialItem(this, 13, "painter_rod")
		{
			@Override
			public void loadRecipes()
			{
				getMod().recipes.addRecipe(getStack(), "  I", " SI", "S  ", 'I', ODItems.IRON, 'S', ROD);
			}
		});
		
		add(PAINT_ROLLER = new MaterialItem(this, 14, "paint_roller_basic")
		{
			@Override
			public void loadRecipes()
			{
				getMod().recipes.addRecipe(getStack(), "WWW", "PIP", "WWW", 'W', new ItemStack(Blocks.carpet, 1, 0), 'I', ODItems.IRON, 'P', Items.paper);
			}
		});
		
		add(PAINT_ROLLER_DMD = new MaterialItem(this, 15, "paint_roller_dmd")
		{
			@Override
			public void loadRecipes()
			{
				getMod().recipes.addRecipe(getStack(), "D", "R", "D", 'D', ODItems.DIAMOND, 'R', PAINT_ROLLER);
			}
		});
		
		add(PAINT_ROLLER_COLOR = new MaterialItem(this, 16, "paint_roller_color")
		{
			@Override
			public void loadRecipes()
			{
				getMod().recipes.addRecipe(getStack(), "123", "8R4", "765", 'R', PAINT_ROLLER_DMD, '1', EnumMCColor.BLUE.dyeName, '2', EnumMCColor.CYAN.dyeName, '3', EnumMCColor.GREEN.dyeName, '4', EnumMCColor.LIME.dyeName, '5', EnumMCColor.YELLOW.dyeName, '6', EnumMCColor.ORANGE.dyeName, '7', EnumMCColor.RED.dyeName, '8', EnumMCColor.PURPLE.dyeName);
			}
		});
		
		super.onPostLoaded();
	}
}