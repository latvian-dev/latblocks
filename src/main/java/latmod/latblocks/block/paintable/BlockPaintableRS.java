package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class BlockPaintableRS extends BlockPaintableSided
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_on;
	
	public BlockPaintableRS(String s)
	{
		super(s);
		setBlockTextureName("paintable_rs");
	}
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPaintableDef.ORE_NAME, new ItemStack(this));
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " P ", "PRP", " P ", 'P', LatBlocksItems.b_cover, 'R', Blocks.redstone_block);
	}
	
	@Override
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePaintableRS(); }
	
	@Override
	public boolean canConnectRedstone(IBlockAccess iba, int x, int y, int z, int side)
	{ return true; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		super.registerBlockIcons(ir);
		icon_on = ir.registerIcon(getTextureName() + "_on");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getDefaultWorldIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		TilePaintableRS t = (TilePaintableRS) iba.getTileEntity(x, y, z);
		return ((t.power > 0) ? icon_on : blockIcon);
	}
}