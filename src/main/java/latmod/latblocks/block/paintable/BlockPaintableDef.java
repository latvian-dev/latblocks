package latmod.latblocks.block.paintable;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.paintable.TilePaintableDef;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPaintableDef extends BlockPaintableSided
{
	public BlockPaintableDef(String s)
	{
		super(s);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		LatCoreMC.addOreDictionary(ODItems.PAINTABLE_BLOCK, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this, 16), "WWW", "WPW", "WWW",
				'W', new ItemStack(Blocks.wool, 1, LatCoreMC.ANY),
				'P', Blocks.quartz_block);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePaintableDef(); }
}