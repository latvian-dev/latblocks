package latmod.latblocks.block.paintable;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.paintable.TilePaintableGS;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPaintableGS extends BlockPaintableSided
{
	public BlockPaintableGS(String s)
	{
		super(s);
		setBlockTextureName("paintableGS");
		setLightLevel(1F);
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this), " P ", "PGP", " P ",
				'P', LatBlocksItems.b_cover,
				'G', Blocks.glowstone);
		
		mod.recipes().addShapelessRecipe(new ItemStack(LatBlocksItems.b_paintable), this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePaintableGS(); }
}