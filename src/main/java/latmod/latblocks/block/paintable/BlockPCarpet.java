package latmod.latblocks.block.paintable;

import ftb.lib.api.item.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPCarpet extends BlockPaintableSingle
{
	public BlockPCarpet(String s)
	{
		super(s, 1F / 16F);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 3), "PPP", 'P', LatBlocksItems.b_cover);
	}
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPCover.ORE_NAME, new ItemStack(this));
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePCarpet(); }
	
	public static class TilePCarpet extends TileSinglePaintable
	{ }
}