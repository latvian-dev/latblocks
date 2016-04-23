package latmod.latblocks.block.paintable;

import ftb.lib.api.item.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPaintableGS extends BlockPaintableSided
{
	public BlockPaintableGS(String s)
	{
		super(s);
		setBlockTextureName("paintable_gs");
		setLightLevel(1F);
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
		getMod().recipes.addRecipe(new ItemStack(this), " P ", "PGP", " P ", 'P', LatBlocksItems.b_cover, 'G', Blocks.glowstone);
	}
	
	@Override
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePaintableGS(); }
	
	public static class TilePaintableGS extends TileSidedPaintable
	{ }
}