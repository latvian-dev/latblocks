package latmod.latblocks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.EnumMCColor;
import ftb.lib.api.item.LMInvUtils;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.api.Paint;
import latmod.latblocks.client.render.world.RenderGlowiumBlocks;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileGlowium;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public abstract class BlockGlowium extends BlockLB
{
	public static final String ORE_NAME = "blockGlowium";
	public static final int DEF_DMG = EnumMCColor.YELLOW.ID;
	
	public static final int[] brightColors = {0xFF2D2D2D, // Black
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
			getMod().addTile(TileGlowium.class, "glowium");
		}
		
		@Override
		public void loadRecipes()
		{
			super.loadRecipes();
			
			getMod().recipes.addRecipe(new ItemStack(this, 1, DEF_DMG), "GG", "GG", 'G', ItemMaterialsLB.GEM_GLOWIUM_Y);
			
			getMod().recipes.addRecipe(ItemMaterialsLB.GEM_GLOWIUM_Y.getStack(4), "G", 'G', new ItemStack(this, 1, DEF_DMG));
			
			LatBlocksItems.i_hammer.addRecipe(new ItemStack(this, 1, DEF_DMG), ORE_NAME);
			
			getMod().recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG", 'G', new ItemStack(LatBlocksItems.b_glowium.get(4), 4, DEF_DMG));
		}
	}
	
	public static class BGTile extends BlockGlowium
	{
		public BGTile(String s)
		{ super(s, "tile"); }
		
		@Override
		public void loadRecipes()
		{
			super.loadRecipes();
			
			getMod().recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG", 'G', new ItemStack(LatBlocksItems.b_glowium.get(0), 4, DEF_DMG));
		}
	}
	
	public static class BGBrick extends BlockGlowium
	{
		public BGBrick(String s)
		{ super(s, "brick"); }
		
		@Override
		public void loadRecipes()
		{
			super.loadRecipes();
			
			getMod().recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG", 'G', new ItemStack(LatBlocksItems.b_glowium.get(1), 4, DEF_DMG));
		}
	}
	
	public static class BGBrickSmall extends BlockGlowium
	{
		public BGBrickSmall(String s)
		{ super(s, "small"); }
		
		@Override
		public void loadRecipes()
		{
			super.loadRecipes();
			
			getMod().recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG", 'G', new ItemStack(LatBlocksItems.b_glowium.get(2), 4, DEF_DMG));
		}
	}
	
	public static class BGBrickChiseled extends BlockGlowium
	{
		public BGBrickChiseled(String s)
		{ super(s, "chiseled"); }
		
		@Override
		public void loadRecipes()
		{
			super.loadRecipes();
			
			getMod().recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG", 'G', new ItemStack(LatBlocksItems.b_glowium.get(3), 4, DEF_DMG));
		}
	}
	
	public final String name;
	
	@SideOnly(Side.CLIENT)
	private IIcon icon_glow;
	
	public BlockGlowium(String s, String s1)
	{
		super(s, Material.rock);
		name = s1;
		setHardness(1.5F);
		setResistance(10F);
		setCreativeTab(LatBlocks.tabGlowium);
	}
	
	@Override
	public String getUnlocalizedName()
	{ return getMod().getBlockName("glowium." + name); }
	
	@Override
	public void onPostLoaded()
	{
		ODItems.add(ORE_NAME, new ItemStack(this, 1, ODItems.ANY));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs c, List l)
	{
		for(int i = 0; i < 16; i++)
		{
			l.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void loadRecipes()
	{
		for(int i = 0; i < 16; i++)
			getMod().recipes.addRecipe(new ItemStack(this, 4, i), " G ", "GCG", " G ", 'G', new ItemStack(this, 1, DEF_DMG), 'C', EnumMCColor.VALUES[i].dyeName);
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileGlowium(); }
	
	@Override
	public int damageDropped(int m)
	{ return m; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(getMod().lowerCaseModID + ":glowium/" + name);
		icon_glow = ir.registerIcon(getMod().lowerCaseModID + ":glowium/" + name + "_glow");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderGlowiumBlocks.instance.getRenderId(); }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@Override
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
	{
		Block b1 = iba.getBlock(x, y, z);
		if(b1 instanceof BlockGlowium) return false;
		return b1 == Blocks.air || !b1.renderAsNormalBlock() || !b1.isOpaqueCube();
	}
	
	@Override
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
	
	@Override
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
					for(int i = 0; i < LatBlocksItems.b_glowium.size(); i++)
					{
						if(this == LatBlocksItems.b_glowium.get(i))
							b = LatBlocksItems.b_glowium.get((i + 1) % LatBlocksItems.b_glowium.size());
					}
				}
				else
				{
					for(int i = 0; i < LatBlocksItems.b_glowium.size(); i++)
					{
						if(this == LatBlocksItems.b_glowium.get(i))
						{
							int j = i - 1;
							if(j < 0) j = LatBlocksItems.b_glowium.size() - 1;
							b = LatBlocksItems.b_glowium.get(j);
						}
					}
				}
				
				if(b != null)
				{
					TileGlowium t = (TileGlowium) getTile(w, x, y, z);
					Paint[] prevPaint = t.paint.clone();
					
					w.setBlock(x, y, z, b, meta, 3);
					
					t = (TileGlowium) getTile(w, x, y, z);
					
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int m)
	{ return brightColors[m]; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iba, int x, int y, int z)
	{ return getRenderColor(iba.getBlockMetadata(x, y, z)); }
	
	public boolean hasPaint(IBlockAccess iba, int x, int y, int z, int s)
	{ return false; }
	
	public void addInfo(ItemStack is, EntityPlayer ep, List<String> l)
	{ l.add(EnumMCColor.VALUES[is.getItemDamage()].toString()); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getGlowItemIcon()
	{ return icon_glow; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getGlowIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return icon_glow; }
	
	@Override
	public boolean canCreatureSpawn(EnumCreatureType t, IBlockAccess iba, int x, int y, int z)
	{ return t.getPeacefulCreature(); }
	
	@Override
	public MapColor getMapColor(int m)
	{ return MapColor.getMapColorForBlockColored(BlockColored.func_150032_b(m)); }
}