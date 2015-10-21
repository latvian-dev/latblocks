package latmod.latblocks.block.paintable;

import ftb.lib.item.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPCover extends BlockPaintableSingle
{
	public static final String ORE_NAME = "coverPaintable";
	
	public BlockPCover(String s)
	{
		super(s, 1F / 8F);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 16), "PP", "PP",
				'P', LatBlocksItems.b_paintable);
		
		mod.recipes.addRecipe(new ItemStack(LatBlocksItems.b_paintable), "PP", "PP",
				'P', this);
		
		mod.recipes.addShapelessRecipe(new ItemStack(this), ORE_NAME);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(ORE_NAME, new ItemStack(this));
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePCover(); }
	
	public void setBlockBoundsForItemRender()
	{ setBlockBounds(0.5F - height / 2F, 0F, 0F, 0.5F + height / 2F, 1F, 1F); }
	
	public static class TilePCover extends TileSinglePaintable
	{
	}
}