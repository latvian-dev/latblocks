package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.tile.TileRSPaintable;
import latmod.latcore.LC;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;

public class BlockRSPaintable extends BlockPaintable
{
	public BlockRSPaintable(String s)
	{
		super(s);
		setBlockTextureName("paintableRS");
	}
	
	public void loadRecipes()
	{
		LC.mod.recipes().addRecipe(new ItemStack(this), " P ", "PRP", " P ",
				'P', ODItems.FACADE_PAINTABLE,
				'R', Blocks.redstone_block);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileRSPaintable(); }
	
	public boolean canConnectRedstone(IBlockAccess iba, int x, int y, int z, int side)
	{ return true; }
}