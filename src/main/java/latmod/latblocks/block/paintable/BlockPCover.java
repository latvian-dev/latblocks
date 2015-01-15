package latmod.latblocks.block.paintable;

import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.paintable.TilePCover;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPCover extends BlockPaintableSingle
{
	public BlockPCover(String s)
	{
		super(s, Material.wood, 1F / 8F);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 16), "PP", "PP",
				'P', LatBlocksItems.b_paintable);
		
		mod.recipes.addRecipe(new ItemStack(LatBlocksItems.b_paintable), "PP", "PP",
				'P', this);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add("paintableCover", new ItemStack(this));
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePCover(); }
	
	public void setBlockBoundsForItemRender()
	{ setBlockBounds(0.5F - height / 2F, 0F, 0F, 0.5F + height / 2F, 1F, 1F); }
}