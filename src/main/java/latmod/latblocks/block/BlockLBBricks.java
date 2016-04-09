package latmod.latblocks.block;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockLBBricks extends BlockLB
{
	public static final String[] names = {"gravel_brick", "lapis_brick", "tiny_gravel", "tiny_stonebrick", "tiny_glowstone", "gravel_tile"};
	
	@SideOnly(Side.CLIENT)
	private IIcon icons[];
	
	public BlockLBBricks(String s)
	{
		super(s, Material.rock);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs c, List l)
	{
		for(int i = 0; i < names.length; i++)
			l.add(new ItemStack(this, 1, i));
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 4, 0), "BB", "BB", 'B', new ItemStack(this, 1, 5));
		getMod().recipes.addRecipe(new ItemStack(this, 4, 1), "BB", "BB", 'B', Blocks.lapis_block);
		getMod().recipes.addRecipe(new ItemStack(this, 4, 2), "BB", "BB", 'B', Blocks.gravel);
		getMod().recipes.addRecipe(new ItemStack(this, 4, 3), "BB", "BB", 'B', Blocks.stonebrick);
		getMod().recipes.addRecipe(new ItemStack(this, 4, 4), "BB", "BB", 'B', Blocks.glowstone);
		getMod().recipes.addRecipe(new ItemStack(this, 4, 5), "BB", "BB", 'B', new ItemStack(this, 1, 2));
	}
	
	public String getUnlocalizedName(int i)
	{ return getMod().getBlockName("brick." + names[i]); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < names.length; i++)
			icons[i] = ir.registerIcon(getMod().lowerCaseModID + ":bricks/" + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return icons[m]; }
	
	public int getLightValue(IBlockAccess iba, int x, int y, int z)
	{
		if(iba.getBlockMetadata(x, y, z) == 4) return 15;
		return 0;
	}
}