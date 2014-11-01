package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.tile.TileRSPaintable;
import latmod.latcore.LC;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockRSPaintable extends BlockPaintable
{
	public BlockRSPaintable(String s)
	{
		super(s);
	}
	
	public void loadRecipes()
	{
		LC.mod.recipes().addRecipe(new ItemStack(this, 8), "PPP", "PRP", "PPP",
				'P', LatBlocksItems.b_paintable,
				'R', ODItems.REDSTONE);
		
		LC.mod.recipes().addRecipe(new ItemStack(LatBlocksItems.b_paintable), "F",
				'F', LatBlocksItems.b_rs_paintable);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileRSPaintable(); }
}