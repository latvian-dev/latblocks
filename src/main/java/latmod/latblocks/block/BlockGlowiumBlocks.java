package latmod.latblocks.block;

import latmod.core.recipes.LMRecipes;
import latmod.core.tile.TileLM;
import latmod.latblocks.client.render.RenderGlowiumBlocks;
import latmod.latblocks.item.ItemMaterialsLB;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockGlowiumBlocks extends BlockLB
{
	public static final String[] names =
	{
		"block",
		"tile",
		"brick",
		"small",
		"chiseled",
	};
	
	public static ItemStack BLOCK;
	public static ItemStack TILE;
	public static ItemStack BRICK;
	public static ItemStack SMALL;
	public static ItemStack CHISELED;
	
	@SideOnly(Side.CLIENT)
	public IIcon icons[], icons_glow[];
	
	public BlockGlowiumBlocks(String s)
	{
		super(s, Material.rock);
		isBlockContainer = false;
		setHardness(1.5F);
		setResistance(10F);
	}
	
	public String getUnlocalizedName(int m)
	{ return mod.getBlockName("glowium." + names[m]); }
	
	public void onPostLoaded()
	{
		addAllDamages(names.length);
		
		BLOCK = new ItemStack(this, 1, 0);
		TILE = new ItemStack(this, 1, 1);
		BRICK = new ItemStack(this, 1, 2);
		SMALL = new ItemStack(this, 1, 3);
		CHISELED = new ItemStack(this, 1, 4);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(BLOCK, "GG", "GG",
				'G', ItemMaterialsLB.GEM_GLOWIUM);
		
		mod.recipes.addRecipe(LMRecipes.size(ItemMaterialsLB.GEM_GLOWIUM, 4), "G",
				'G', BLOCK);
		
		for(int i = 0; i < names.length; i++)
			mod.recipes.addRecipe(new ItemStack(this, 4, (i + 1) % names.length), "GG", "GG",
					'G', new ItemStack(this, 1, i));
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
		icons_glow = new IIcon[names.length];
		
		for(int i = 0; i < icons.length; i++)
		{
			icons[i] = ir.registerIcon(mod.assets + "glowium/" + names[i]);
			icons_glow[i] = ir.registerIcon(mod.assets + "glowium/" + names[i] + "_glow");
		}
	}
	
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess iba, int x, int y, int z)
	{ return type != EnumCreatureType.monster; }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderGlowiumBlocks.instance.getRenderId(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    { return true; }
}