package latmod.latblocks.block;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.api.IQuartzNetTile;
import latmod.latblocks.client.render.world.RenderQCable;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.latblocks.item.ItemMaterialsLB;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockQCable extends BlockLB
{
	public static final float border = 1F / 32F * 12F;
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_glow;
	
	public BlockQCable(String s)
	{
		super(s, Material.wood);
		setBlockBoundsForItemRender();
		setHardness(0.6F);
	}
	
	@Override
	public void loadRecipes()
	{
		if(LatBlocksConfigCrafting.qNetBlocks.getAsBoolean())
			getMod().recipes.addRecipe(new ItemStack(this, 8), "SSS", "GDG", "SSS", 'S', Blocks.wooden_slab, 'G', ODItems.GLOWSTONE, 'D', ItemMaterialsLB.DUST_GLOWIUM_B);
	}
	
	@Override
	public void setBlockBoundsForItemRender()
	{
		float s = border;
		setBlockBounds(0F, s, s, 1F, 1F - s, 1F - s);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		float s = border - (1F / 32F);// - 1 / 16F;
		
		boolean x0 = connects(iba, x - 1, y, z);
		boolean x1 = connects(iba, x + 1, y, z);
		boolean y0 = connects(iba, x, y - 1, z);
		boolean y1 = connects(iba, x, y + 1, z);
		boolean z0 = connects(iba, x, y, z - 1);
		boolean z1 = connects(iba, x, y, z + 1);
		
		setBlockBounds(x0 ? 0F : s, y0 ? 0F : s, z0 ? 0F : s, x1 ? 1F : 1F - s, y1 ? 1F : 1F - s, z1 ? 1F : 1F - s);
	}
	
	public static boolean connects(IBlockAccess iba, int x, int y, int z)
	{
		Block b = iba.getBlock(x, y, z);
		if(b == LatBlocksItems.b_qcable || b == LatBlocksItems.b_qterminal || b == LatBlocksItems.b_qchest || b == LatBlocksItems.b_qfurnace || b == LatBlocksItems.b_tank || b == LatBlocksItems.b_tank_void || b == LatBlocksItems.b_tank_water)
			return true;
		if(!b.hasTileEntity(iba.getBlockMetadata(x, y, z))) return false;
		return iba.getTileEntity(x, y, z) instanceof IQuartzNetTile;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(getMod().lowerCaseModID + ":cable");
		icon_glow = ir.registerIcon(getMod().lowerCaseModID + ":cable_glow");
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@Override
	public boolean isOpaqueCube()
	{ return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderQCable.instance.getRenderId(); }
}