package latmod.latblocks.block;

import cpw.mods.fml.relauncher.*;
import ftb.lib.item.ODItems;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.client.render.world.RenderQCable;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.IQuartzNetTile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

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
		if(LatBlocksConfigCrafting.qNetBlocks.get())
			mod.recipes.addRecipe(new ItemStack(this, 8), "SSS", "GDG", "SSS",
				'S', Blocks.wooden_slab,
				'G', ODItems.GLOWSTONE,
				'D', ItemMaterialsLB.DUST_GLOWIUM_B);
	}
	
	public void setBlockBoundsForItemRender()
	{
		float s = border;
		setBlockBounds(0F, s, s, 1F, 1F - s, 1F - s);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		float s = border - (1F / 32F);// - 1 / 16F;
		
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
		Block b = iba.getBlock(x, y, z);
		if(b == LatBlocksItems.b_qcable
		|| b == LatBlocksItems.b_qterminal
		|| b == LatBlocksItems.b_qchest
		|| b == LatBlocksItems.b_qfurnace
		|| b == LatBlocksItems.b_tank
		|| b == LatBlocksItems.b_tank_void
		|| b == LatBlocksItems.b_tank_water) return true;
		if(!b.hasTileEntity(iba.getBlockMetadata(x, y, z))) return false;
		return iba.getTileEntity(x, y, z) instanceof IQuartzNetTile;
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