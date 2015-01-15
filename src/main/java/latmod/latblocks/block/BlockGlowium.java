package latmod.latblocks.block;

import latmod.core.*;
import latmod.core.recipes.LMRecipes;
import latmod.core.tile.*;
import latmod.latblocks.LatBlocksConfig;
import latmod.latblocks.client.render.RenderGlowiumBlocks;
import latmod.latblocks.item.ItemMaterialsLB;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockGlowium extends BlockLB implements IPaintable.INoPaint
{
	public static final String BLOCK = "blockGlowium";
	
	public static BlockGlowium b_block;
	public static BlockGlowium b_tile;
	public static BlockGlowium b_brick;
	public static BlockGlowium b_small;
	public static BlockGlowium b_chiseled;
	
	public static final int DEF_DMG = EnumDyeColor.YELLOW.ID;
	
	public static void init()
	{
		b_block = new BlockGlowium("glowiumBlock", "block")
		{
			public void loadRecipes()
			{
				super.loadRecipes();
				
				mod.recipes.addRecipe(new ItemStack(this, 1, DEF_DMG), "G",
						'G', BLOCK);
				
				mod.recipes.addRecipe(new ItemStack(this, 4, DEF_DMG), "GG", "GG",
						'G', ItemMaterialsLB.GEM_GLOWIUM);
				
				mod.recipes.addRecipe(LMRecipes.size(ItemMaterialsLB.GEM_GLOWIUM, 4), "G",
						'G', new ItemStack(this, 1, EnumDyeColor.YELLOW.ID));
				
				mod.recipes.addRecipe(new ItemStack(this, 4), "GG", "GG",
						'G', b_chiseled);
			}
		}.register();
		
		b_tile = new BlockGlowium("glowiumTile", "tile")
		{
			public void loadRecipes()
			{
				super.loadRecipes();
				
				mod.recipes.addRecipe(new ItemStack(this, 4), "GG", "GG",
						'G', b_block);
			}
		}.register();
		
		b_brick = new BlockGlowium("glowiumBrick", "brick")
		{
			public void loadRecipes()
			{
				super.loadRecipes();
				
				mod.recipes.addRecipe(new ItemStack(this, 4), "GG", "GG",
						'G', b_tile);
			}
		}.register();
		
		b_small = new BlockGlowium("glowiumSmallBrick", "small")
		{
			public void loadRecipes()
			{
				super.loadRecipes();
				
				mod.recipes.addRecipe(new ItemStack(this, 4), "GG", "GG",
						'G', b_brick);
			}
		}.register();
		
		b_chiseled = new BlockGlowium("glowiumChiseledBrick", "chiseled")
		{
			public void loadRecipes()
			{
				super.loadRecipes();
				
				mod.recipes.addRecipe(new ItemStack(this, 4), "GG", "GG",
						'G', b_small);
			}
		}.register();
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
		if(LatBlocksConfig.General.addAllColorBlocks)
			addAllDamages(16);
		else
			blocksAdded.add(new ItemStack(this, 1, DEF_DMG));
		
		ODItems.add(BLOCK, new ItemStack(this, 1, ODItems.ANY));
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
	{ return EnumDyeColor.VALUES[m].color; }
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess iba, int x, int y, int z)
	{ return getRenderColor(iba.getBlockMetadata(x, y, z)); }

	public boolean hasPaint(IBlockAccess iba, int x, int y, int z, int s)
	{ return false; }
	
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{ l.add(EnumDyeColor.VALUES[is.getItemDamage()].toString()); }
}