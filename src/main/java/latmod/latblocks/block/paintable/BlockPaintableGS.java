package latmod.latblocks.block.paintable;
import ftb.lib.item.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPaintableGS extends BlockPaintableSided
{
	public BlockPaintableGS(String s)
	{
		super(s);
		setBlockTextureName("paintable_gs");
		setLightLevel(1F);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPaintableDef.ORE_NAME, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), " P ", "PGP", " P ",
				'P', LatBlocksItems.b_cover,
				'G', Blocks.glowstone);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePaintableGS(); }
	
	public static class TilePaintableGS extends TileSidedPaintable
	{
	}
}