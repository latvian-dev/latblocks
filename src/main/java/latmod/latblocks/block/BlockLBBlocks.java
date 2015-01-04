package latmod.latblocks.block;

import latmod.core.recipes.LMRecipes;
import latmod.core.tile.TileLM;
import latmod.latblocks.item.ItemMaterialsLB;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockLBBlocks extends BlockLB
{
	public static final String[] names =
	{
		"glowiumBlock",
		"glowiumTile",
		"glowiumBrick",
		"glowiumBrickSmall",
		"glowiumBrickChiseled",
	};
	
	public static ItemStack BLOCK;
	public static ItemStack TILE;
	public static ItemStack BRICK;
	public static ItemStack BRICK_SMALL;
	public static ItemStack BRICK_CHISELED;
	
	@SideOnly(Side.CLIENT)
	public IIcon icons[];
	
	public BlockLBBlocks(String s)
	{
		super(s, Material.rock);
		isBlockContainer = false;
		setHardness(1.5F);
		setResistance(10F);
	}
	
	public String getUnlocalizedName(int m)
	{ return mod.getBlockName(names[m]); }
	
	public void onPostLoaded()
	{
		addAllDamages(names.length);
		
		BLOCK = new ItemStack(this, 1, 0);
		TILE = new ItemStack(this, 1, 1);
		BRICK = new ItemStack(this, 1, 2);
		BRICK_SMALL = new ItemStack(this, 1, 3);
		BRICK_CHISELED = new ItemStack(this, 1, 4);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(BLOCK, "GG", "GG",
				'G', ItemMaterialsLB.GEM_GLOWIUM);
		
		mod.recipes.addRecipe(LMRecipes.size(ItemMaterialsLB.GEM_GLOWIUM, 4), "G",
				'G', BLOCK);
		
		mod.recipes.addRecipe(LMRecipes.size(TILE, 4), "GG", "GG",
				'G', BLOCK);
		
		mod.recipes.addRecipe(LMRecipes.size(BRICK, 4), "GG", "GG",
				'G', TILE);
		
		mod.recipes.addRecipe(LMRecipes.size(BRICK_SMALL, 4), "GG", "GG",
				'G', BRICK);
		
		mod.recipes.addRecipe(LMRecipes.size(BLOCK, 4), "GG", "GG",
				'G', BRICK_SMALL);
		
		mod.recipes.addRecipe(LMRecipes.size(BRICK_CHISELED, 8), "GGG", "G G", "GGG",
				'G', TILE);
		
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return icons[m]; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < icons.length; i++)
			icons[i] = ir.registerIcon(mod.assets + names[i]);
	}
}