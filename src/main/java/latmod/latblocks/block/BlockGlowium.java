package latmod.latblocks.block;

import java.util.List;

import latmod.ftbu.FTBUConfig;
import latmod.ftbu.core.*;
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

public abstract class BlockGlowium extends BlockLB implements IPaintable.INoPaint
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
	
	public static void postLoaded()
	{
		if(ChiselHelper.isInstalled())
		{
			ChiselHelper.register(new GroupGlowium(LatBlocksItems.b_glowium_block));
			ChiselHelper.register(new GroupGlowium(LatBlocksItems.b_glowium_tile));
			ChiselHelper.register(new GroupGlowium(LatBlocksItems.b_glowium_brick));
			ChiselHelper.register(new GroupGlowium(LatBlocksItems.b_glowium_small));
			ChiselHelper.register(new GroupGlowium(LatBlocksItems.b_glowium_chiseled));
			ChiselHelper.register(new GroupGlowium(LatBlocksItems.b_lined_block));
		}
	}
	
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
			
			if(LatBlocksConfig.Crafting.glowiumBlocks)
			{
				mod.recipes.addRecipe(new ItemStack(this, 1, DEF_DMG), "GG", "GG",
						'G', ItemMaterialsLB.GEMS_GLOWIUM[0]);
				
				mod.recipes.addRecipe(LMRecipes.size(ItemMaterialsLB.GEMS_GLOWIUM[0], 4), "G",
						'G', new ItemStack(this, 1, DEF_DMG));
				
				if(!ChiselHelper.isInstalled())
					LatBlocksItems.i_hammer.addRecipe(new ItemStack(this, 1, DEF_DMG), ORE_NAME);
				
				mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
						'G', new ItemStack(LatBlocksItems.b_glowium_chiseled, 4, DEF_DMG));
			}
		}
	}
	
	public static class BGTile extends BlockGlowium
	{
		public BGTile(String s)
		{ super(s, "tile"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			if(LatBlocksConfig.Crafting.glowiumBlocks)
				mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium_block, 4, DEF_DMG));
		}
	}
	
	public static class BGBrick extends BlockGlowium
	{
		public BGBrick(String s)
		{ super(s, "brick"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			if(LatBlocksConfig.Crafting.glowiumBlocks)
				mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium_tile, 4, DEF_DMG));
		}
	}
	
	public static class BGBrickSmall extends BlockGlowium
	{
		public BGBrickSmall(String s)
		{ super(s, "small"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			if(LatBlocksConfig.Crafting.glowiumBlocks)
				mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium_brick, 4, DEF_DMG));
		}
	}
	
	public static class BGBrickChiseled extends BlockGlowium
	{
		public BGBrickChiseled(String s)
		{ super(s, "chiseled"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			if(LatBlocksConfig.Crafting.glowiumBlocks)
				mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_glowium_small, 4, DEF_DMG));
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
		if(!ChiselHelper.isInstalled())
			ODItems.add(ORE_NAME, new ItemStack(this, 1, ODItems.ANY));
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item j, CreativeTabs c, List l)
	{
		if(FTBUConfig.Client.addAllColorItems)
		{
			for(int i = 0; i < 16; i++)
				l.add(new ItemStack(this, 1, i));
		}
		else l.add(new ItemStack(this, 1, DEF_DMG));
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfig.Crafting.glowiumBlocks && !ChiselHelper.isInstalled())
		{
			for(int i = 0; i < 16; i++)
				mod.recipes.addRecipe(new ItemStack(this, 4, i), " G ", "GCG", " G ",
						'G', new ItemStack(this, 1, DEF_DMG),
						'C', EnumDyeColor.VALUES[i].dyeName);
		}
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileGlowium(); }
	
	public int damageDropped(int m)
	{ return m; }
	
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
		Block b1 = iba.getBlock(x + Facing.offsetsXForSide[s], y + Facing.offsetsYForSide[s], z + Facing.offsetsZForSide[s]);
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
		if(InvUtils.isWrench(ep.getHeldItem()))
		{
			if(!w.isRemote)
			{
				BlockGlowium b = null;
				int meta = w.getBlockMetadata(x, y, z);
				
				if(ep.isSneaking())
				{
					if(this == LatBlocksItems.b_glowium_block) b = LatBlocksItems.b_glowium_chiseled;
					if(this == LatBlocksItems.b_glowium_tile) b = LatBlocksItems.b_glowium_block;
					if(this == LatBlocksItems.b_glowium_brick) b = LatBlocksItems.b_glowium_tile;
					if(this == LatBlocksItems.b_glowium_small) b = LatBlocksItems.b_glowium_brick;
					if(this == LatBlocksItems.b_glowium_chiseled) b = LatBlocksItems.b_glowium_small;
				}
				else
				{
					if(this == LatBlocksItems.b_glowium_block) b = LatBlocksItems.b_glowium_tile;
					if(this == LatBlocksItems.b_glowium_tile) b = LatBlocksItems.b_glowium_brick;
					if(this == LatBlocksItems.b_glowium_brick) b = LatBlocksItems.b_glowium_small;
					if(this == LatBlocksItems.b_glowium_small) b = LatBlocksItems.b_glowium_chiseled;
					if(this == LatBlocksItems.b_glowium_chiseled) b = LatBlocksItems.b_glowium_block;
				}
				
				if(b != null)
				{
					TileGlowium t = (TileGlowium)w.getTileEntity(x, y, z);
					IPaintable.Paint[] prevPaint = t.paint.clone();
					
					w.setBlock(x, y, z, b, meta, 3);
					
					t = (TileGlowium)w.getTileEntity(x, y, z);
					if(t != null && t.isValid())
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
	
	public static class GroupGlowium extends ChiselHelper.Group
	{
		public GroupGlowium(BlockGlowium b)
		{
			super(b);
			
			name = b.blockName;
			
			/*
			for(int i = 0; i < allBlocks.size(); i++)
			{
				BlockGlowium b1 = allBlocks.get(i);
				if(b1 != b) addVariation(b1, DEF_DMG);
			}*/
			
			for(int i = 0; i < 16; i++)
				addVariation(b, i);
		}
	}
}