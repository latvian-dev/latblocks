package latmod.latblocks.block;

import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.client.render.world.RenderQCable;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.IQuartzNetTile;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockQCable extends BlockLB
{
	public static final float border = 1F / 32F * 12F;
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_glow;
	
	public BlockQCable(String s)
	{
		super(s, Material.wood);
		isBlockContainer = false;
		setBlockBoundsForItemRender();
		setHardness(0.6F);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 8), "SSS", "GDG", "SSS",
				'S', Blocks.wooden_slab,
				'G', ODItems.GLOWSTONE,
				'D', ItemMaterialsLB.STAR_DUST.stack);
	}
	
	public void setBlockBoundsForItemRender()
	{
		float s = border;
		setBlockBounds(0F, s, s, 1F, 1F - s, 1F - s);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		float s = border;// - 1 / 16F;
		
		boolean x0 = connects(iba, x - 1, y, z);
		boolean x1 = connects(iba, x + 1, y, z);
		boolean y0 = connects(iba, x, y - 1, z);
		boolean y1 = connects(iba, x, y + 1, z);
		boolean z0 = connects(iba, x, y, z - 1);
		boolean z1 = connects(iba, x, y, z + 1);
		
		setBlockBounds(x0 ? 0F : s, y0 ? 0F : s, z0 ? 0F: s, x1 ? 1F : 1F - s, y1 ? 1F: 1F - s, z1 ? 1F : 1F - s);
	}
	
	public static boolean connects(IBlockAccess iba, int x, int y, int z)
	{
		if(iba.getBlock(x, y, z) == LatBlocksItems.b_qcable) return true;
		TileEntity te = iba.getTileEntity(x, y, z);
		return (te != null && te instanceof IQuartzNetTile);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "cable");
		icon_glow = ir.registerIcon(mod.assets + "cable_glow");
	}
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderQCable.instance.getRenderId(); }
}