package latmod.latblocks.block;

import java.util.List;

import latmod.core.*;
import latmod.core.mod.LCConfig;
import latmod.core.recipes.LMRecipes;
import latmod.core.tile.*;
import latmod.latblocks.*;
import latmod.latblocks.client.render.RenderGlowiumBlocks;
import latmod.latblocks.item.ItemMaterialsLB;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public abstract class BlockGlowium extends BlockLB implements IPaintable.INoPaint
{
	public static int[] colors =
	{
		0xFF262626, // Black
		0xFFE01414, // Red
		0xFF00980E, // Green
		0xFF934E23, // Brown
		0xFF004CC4, // Blue
		0xFF9A41E2, // Purple
		0xFF00AEFF, // Cyan
		0xFFC0C0C0, // LightGray
		0xFF636363, // Gray
		0xFFFF7F7F, // Pink
		0xFF2BE541, // Lime
		0xFFFFD500, // Yellow
		0xFF63BEFF, // LightBlue
		0xFFFF00DC, // Magenta
		0xFFFF952B, // Orange
		0xFFEFEFEF, // White
	};
	
	public static final String BLOCK = "blockGlowium";
	
	public static final int DEF_DMG = EnumDyeColor.YELLOW.ID;
	
	public static class BGBlock extends BlockGlowium
	{
		public BGBlock()
		{ super("glowiumBlock", "block"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 1, DEF_DMG), "G",
					'G', BLOCK);
			
			mod.recipes.addRecipe(new ItemStack(this, 1, DEF_DMG), "GG", "GG",
					'G', ItemMaterialsLB.GEM_GLOWIUM);
			
			mod.recipes.addRecipe(LMRecipes.size(ItemMaterialsLB.GEM_GLOWIUM, 4), "G",
					'G', new ItemStack(this, 1, DEF_DMG));
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_chiseled, 4, DEF_DMG));
		}
	}
	
	public static class BGTile extends BlockGlowium
	{
		public BGTile()
		{ super("glowiumTile", "tile"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_block, 4, DEF_DMG));
		}
	}
	
	public static class BGBrick extends BlockGlowium
	{
		public BGBrick()
		{ super("glowiumBrick", "brick"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_tile, 4, DEF_DMG));
		}
	}
	
	public static class BGBrickSmall extends BlockGlowium
	{
		public BGBrickSmall()
		{ super("glowiumSmallBrick", "small"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_brick, 4, DEF_DMG));
		}
	}
	
	public static class BGBrickChiseled extends BlockGlowium
	{
		public BGBrickChiseled()
		{ super("glowiumChiseledBrick", "chiseled"); }
		
		public void loadRecipes()
		{
			super.loadRecipes();
			
			mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
					'G', new ItemStack(LatBlocksItems.b_small, 4, DEF_DMG));
		}
	}
	
	public final String name;
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_glow;
	
	public BlockGlowium(String s, String s1)
	{
		super(s, Material.rock);
		name = s1;
		isBlockContainer = false;
		setHardness(1.5F);
		setResistance(10F);
	}
	
	public String getUnlocalizedName(int m)
	{ return mod.getBlockName("glowium." + name); }
	
	public void onPostLoaded()
	{
		blocksAdded.add(new ItemStack(this, 1, DEF_DMG));
		
		ODItems.add(BLOCK, new ItemStack(this, 1, ODItems.ANY));
	}
	
	@SuppressWarnings("all")
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item j, CreativeTabs c, List l)
	{
		if(LCConfig.Client.addAllColorBlocks)
		{
			for(int i = 0; i < 16; i++)
				l.add(new ItemStack(this, 1, i));
		}
		else l.add(new ItemStack(this, 1, DEF_DMG));
	}
	
	public void loadRecipes()
	{
		for(int i = 0; i < 16; i++)
			mod.recipes.addRecipe(new ItemStack(this, 4, i), " G ", "GCG", " G ",
					'G', new ItemStack(this, 1, DEF_DMG),
					'C', EnumDyeColor.VALUES[i].dyeName);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
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
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    { return true; }
	
	public boolean recolourBlock(World w, int x, int y, int z, ForgeDirection side, int col)
	{
		int meta = (col + 1) % 16;
		
		if(w.getBlockMetadata(x, y, z) != meta)
		{
			w.setBlockMetadataWithNotify(x, y, z, meta, 3);
			return true;
		}
		
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int m)
	{ return colors[m]; }
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iba, int x, int y, int z)
	{ return getRenderColor(iba.getBlockMetadata(x, y, z)); }

	public boolean hasPaint(IBlockAccess iba, int x, int y, int z, int s)
	{ return false; }
	
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{ l.add(EnumDyeColor.VALUES[is.getItemDamage()].toString()); }
}