package latmod.latblocks.block;

import java.util.List;

import latmod.ftbu.core.EnumDyeColor;
import latmod.ftbu.core.inv.*;
import latmod.ftbu.core.recipes.LMRecipes;
import latmod.ftbu.core.tile.*;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.*;
import latmod.latblocks.client.render.world.RenderGlowiumBlocks;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileGlowium;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public abstract class BlockGlowium extends BlockLB
{
	public static final String ORE_NAME = "blockGlowium";
	public static final int DEF_DMG = EnumDyeColor.YELLOW.ID;
	
	public static final int[] brightColors =
	{
		0xFF2D2D2D, // Black
		0xFFFF0000, // Red
		0xFF00D615, // Green
		0xFF9B3E00, // Brown
		0xFF0049FF, // Blue
		0xFFB43DFF, // Purple
		0xFF00FFFF, // Cyan
		0xFFCECECE, // LightGray
		0xFF494949, // Gray
		0xFFFF9999, // Pink
		0xFF00F321, // Lime
		0xFFFFD800, // Yellow
		0xFF56B8FF, // LightBlue
		0xFFFF22DC, // Magenta
		0xFFFF8900, // Orange
		0xFFFFFFFF, // White
	};
	
	public static class BGBlock extends BlockGlowium
	{
		public BGBlock(String s)
		{
			super(s, "block");
			mod.addTile(TileGlowium.class, "glowium");
		}
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 1, DEF_DMG), "GG", "GG",
					'G', ItemMaterialsLB.GEM_GLOWIUM_Y.stack);
			
			mod.recipes.addRecipe(LMRecipes.size(ItemMaterialsLB.GEM_GLOWIUM_Y.stack, 4), "G",
					'G', new ItemStack(this, 1, DEF_DMG));
			
			LatBlocksItems.i_hammer.addRecipe(new ItemStack(this, 1, DEF_DMG), ORE_NAME);
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium[4], 4, DEF_DMG));
		}
	}
	
	public static class BGTile extends BlockGlowium
	{
		public BGTile(String s)
		{ super(s, "tile"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium[0], 4, DEF_DMG));
		}
	}
	
	public static class BGBrick extends BlockGlowium
	{
		public BGBrick(String s)
		{ super(s, "brick"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium[1], 4, DEF_DMG));
		}
	}
	
	public static class BGBrickSmall extends BlockGlowium
	{
		public BGBrickSmall(String s)
		{ super(s, "small"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium[2], 4, DEF_DMG));
		}
	}
	
	public static class BGBrickChiseled extends BlockGlowium
	{
		public BGBrickChiseled(String s)
		{ super(s, "chiseled"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium[3], 4, DEF_DMG));
		}
	}
	
	public final String name;
	
	@SideOnly(Side.CLIENT)
	private IIcon icon_glow;
	
	public BlockGlowium(String s, String s1)
	{
		super(s, Material.rock);
		name = s1;
		isBlockContainer = true;
		setHardness(1.5F);
		setResistance(10F);
	}
	
	public String getUnlocalizedName(int m)
	{ return mod.getBlockName("glowium." + name); }
	
	public void onPostLoaded()
	{
		blocksAdded.add(new ItemStack(this, 1, DEF_DMG));
		ODItems.add(ORE_NAME, new ItemStack(this, 1, ODItems.ANY));
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item j, CreativeTabs c, List l)
	{
		l.add(new ItemStack(this, 1, DEF_DMG));
	}
	
	public void loadRecipes()
	{
		for(int i = 0; i < 16; i++) mod.recipes.addRecipe(new ItemStack(this, 4, i), " G ", "GCG", " G ",
				'G', new ItemStack(this, 1, DEF_DMG),
				'C', EnumDyeColor.VALUES[i].dyeName);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileGlowium(); }
	
	public int damageDropped(int m)
	{ return m; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return LatBlocks.tabGlowium; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "glowium/" + name);
		icon_glow = ir.registerIcon(mod.assets + "glowium/" + name + "_glow");
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderGlowiumBlocks.instance.getRenderId(); }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    {
		Block b1 = iba.getBlock(x, y, z);
		if(b1 instanceof BlockGlowium) return false;
		return b1 == Blocks.air || !b1.renderAsNormalBlock() || !b1.isOpaqueCube();
	}
	
	public boolean recolourBlock(World w, int x, int y, int z, ForgeDirection side, int col)
	{
		int meta = BlockColored.func_150031_c(col);
		
		if(w.getBlockMetadata(x, y, z) != meta)
		{
			w.setBlockMetadataWithNotify(x, y, z, meta, 3);
			return true;
		}
		
		return false;
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		if(LMInvUtils.isWrench(ep.getHeldItem()))
		{
			if(!w.isRemote)
			{
				BlockGlowium b = null;
				int meta = w.getBlockMetadata(x, y, z);
				
				if(ep.isSneaking())
				{
					for(int i = 0; i < LatBlocksItems.b_glowium.length; i++)
					{
						if(this == LatBlocksItems.b_glowium[i])
							b = LatBlocksItems.b_glowium[(i + 1) % LatBlocksItems.b_glowium.length];
					}
				}
				else
				{
					for(int i = 0; i < LatBlocksItems.b_glowium.length; i++)
					{
						if(this == LatBlocksItems.b_glowium[i])
						{
							int j = i - 1;
							if(j < 0) j = LatBlocksItems.b_glowium.length - 1;
							b = LatBlocksItems.b_glowium[j];
						}
					}
				}
				
				if(b != null)
				{
					TileGlowium t = getTile(TileGlowium.class, w, x, y, z);
					IPaintable.Paint[] prevPaint = t.paint.clone();
					
					w.setBlock(x, y, z, b, meta, 3);
					
					t = getTile(TileGlowium.class, w, x, y, z);
					
					if(t != null)
					{
						for(int i = 0; i < t.paint.length; i++)
							t.paint[i] = prevPaint[i];
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int m)
	{ return brightColors[m]; }
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iba, int x, int y, int z)
	{ return getRenderColor(iba.getBlockMetadata(x, y, z)); }

	public boolean hasPaint(IBlockAccess iba, int x, int y, int z, int s)
	{ return false; }
	
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{ l.add(EnumDyeColor.VALUES[is.getItemDamage()].toString()); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getGlowItemIcon()
	{ return icon_glow; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getGlowIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return icon_glow; }
}