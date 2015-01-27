package latmod.latblocks.block.paintable;
import latmod.core.ODItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.*;
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
		ODItems.add("blockPaintable", new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 16), "WWW", "WPW", "WWW",
				'W', new ItemStack(Blocks.wool, 1, ODItems.ANY),
				'P', Blocks.quartz_block);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePaintableDef(); }
	
	public static class TilePaintableDef extends TileSidedPaintable
	{
	}
}