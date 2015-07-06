package latmod.latblocks.block;

import latmod.ftbu.core.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.paintable.BlockPCover;
import latmod.latblocks.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockNoteBoard extends BlockPaintableSingle
{
	public BlockNoteBoard(String s)
	{
		super(s, 1F / 16F);
		setBlockTextureName("note_board");
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
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		int i = super.onBlockPlaced(w, ep, mop, m);
		if(i == Placement.D_DOWN || i == Placement.D_UP) return -1;
		return i;
	}
}