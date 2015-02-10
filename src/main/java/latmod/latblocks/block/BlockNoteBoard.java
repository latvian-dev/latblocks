package latmod.latblocks.block;

import latmod.core.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.paintable.BlockPCover;
import latmod.latblocks.tile.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockNoteBoard extends BlockPaintableSingle
{
	public BlockNoteBoard(String s)
	{
		super(s, 1F / 16F);
		setBlockTextureName("noteBoard");
	}
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this),
				LatBlocksItems.b_cover,
				Items.item_frame);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPCover.ORE_NAME, new ItemStack(this));
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TileNoteBoard(); }
	
	public void setBlockBoundsForItemRender()
	{ setBlockBounds(0.5F - height, 0F, 0F, 0.5F + height, 1F, 1F); }
}