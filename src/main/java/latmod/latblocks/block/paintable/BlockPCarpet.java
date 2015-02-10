package latmod.latblocks.block.paintable;

import latmod.core.ODItems;
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
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 3), "PPP",
				'P', LatBlocksItems.b_cover);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPCover.ORE_NAME, new ItemStack(this));
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePCarpet(); }
	
	public static class TilePCarpet extends TileSinglePaintable
	{
	}
}