package latmod.latblocks.block.paintable;

import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPSlab extends BlockPaintableSingle
{
	public BlockPSlab(String s)
	{
		super(s, 1F / 2F);
		setHardness(1.5F);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 6), "PPP", 'P', LatBlocksItems.b_paintable);
		
		getMod().recipes.addShapelessRecipe(new ItemStack(LatBlocksItems.b_paintable), this, this);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePSlab(); }
	
	public static class TilePSlab extends TileSinglePaintable
	{ }
}