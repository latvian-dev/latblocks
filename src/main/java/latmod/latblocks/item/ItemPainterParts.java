package latmod.latblocks.item;
import latmod.ftbu.core.*;
import latmod.ftbu.core.client.LatCoreMCClient;
import latmod.ftbu.core.recipes.LMRecipes;
import latmod.latblocks.LatBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.*;

public class ItemPainterParts extends ItemLB
{
	public static final String[] names =
	{
		"rod",
		"basic",
		"dmd",
		"color",
		"multi",
	};
	
	public static ItemStack PAINT_ROLLER_ROD;
	public static ItemStack PAINT_ROLLER;
	public static ItemStack PAINT_ROLLER_DMD;
	public static ItemStack PAINT_ROLLER_COLOR;
	public static ItemStack PAINT_ROLLER_MULTI;
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public ItemPainterParts(String s)
	{ super(s); }
	
	public String getPrefix()
	{ return "paint_roller"; }
	
	public LMMod getMod()
	{ return LatBlocks.mod; }
	
	public CreativeTabs getCreativeTab()
	{ return LatBlocks.tab; }
	
	public void onPostLoaded()
	{
		addAllDamages(names.length);
		
		PAINT_ROLLER_ROD = new ItemStack(this, 1, 0);
		PAINT_ROLLER = new ItemStack(this, 1, 1);
		PAINT_ROLLER_DMD = new ItemStack(this, 1, 2);
		PAINT_ROLLER_COLOR = new ItemStack(this, 1, 3);
		PAINT_ROLLER_MULTI = new ItemStack(this, 1, 4);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(PAINT_ROLLER_ROD, "  I", " SI", "S  ",
				'I', ODItems.IRON,
				'S', ItemMaterialsLB.ROD);
		
		mod.recipes.addRecipe(LMRecipes.size(PAINT_ROLLER, 6), "WWW", "III", "WWW",
				'W', ODItems.WOOL_WHITE,
				'I', ODItems.IRON);
		
		mod.recipes.addRecipe(PAINT_ROLLER_DMD, "DDD", "DRD", "DDD",
				'D', ODItems.DIAMOND,
				'R', PAINT_ROLLER);
		
		mod.recipes.addRecipe(PAINT_ROLLER_COLOR, "123", "8R4", "765",
				'R', PAINT_ROLLER_DMD,
				'1', EnumDyeColor.BLUE.dyeName,
				'2', EnumDyeColor.CYAN.dyeName,
				'3', EnumDyeColor.GREEN.dyeName,
				'4', EnumDyeColor.LIME.dyeName,
				'5', EnumDyeColor.YELLOW.dyeName,
				'6', EnumDyeColor.ORANGE.dyeName,
				'7', EnumDyeColor.RED.dyeName,
				'8', EnumDyeColor.PURPLE.dyeName);
		
		//mod.recipes.addShapelessRecipe(PAINT_ROLLER_MULTI, PAINT_ROLLER_DMD, PAINT_ROLLER_COLOR);
	}
	
	public String getUnlocalizedName(ItemStack is)
	{ return mod.getItemName("roller_" + names[is.getItemDamage()]); }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < names.length; i++)
			icons[i] = ir.registerIcon(mod.assets + "paint_roller/" + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int m, int r)
	{ return (m >= 0 && m < icons.length) ? icons[m] : LatCoreMCClient.unknownItemIcon; }
}