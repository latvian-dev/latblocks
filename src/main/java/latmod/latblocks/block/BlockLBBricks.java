package latmod.latblocks.block;

import latmod.ftbu.core.tile.TileLM;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockLBBricks extends BlockLB
{
	public static final String[] names =
	{
		"gravel_brick",
		"lapis_brick",
		"tiny_gravel",
		"tiny_stonebrick",
		"tiny_glowstone"
	};
	
	@SideOnly(Side.CLIENT)
	private IIcon icons[];
	
	public BlockLBBricks(String s)
	{
		super(s, Material.rock);
		isBlockContainer = false;
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public void onPostLoaded()
	{
		for(int i = 0; i < names.length; i++)
			blocksAdded.add(new ItemStack(this, 1, i));
	}
	
	public void loadRecipes()
	{
	}
	
	public String getUnlocalizedName(int i)
	{ return mod.getBlockName("brick." + names[i]); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[names.length];
		for(int i = 0; i < names.length; i++)
			icons[i] = ir.registerIcon(mod.assets + "bricks/" + names[i]);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return icons[m]; }
	
	public int getLightValue(IBlockAccess iba, int x, int y, int z)
	{ if(iba.getBlockMetadata(x, y, z) == 4) return 15; return 0; }
}