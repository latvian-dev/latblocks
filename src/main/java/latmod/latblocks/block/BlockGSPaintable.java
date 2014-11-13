package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.tile.TileGSPaintable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockGSPaintable extends BlockPaintable
{
	public BlockGSPaintable(String s)
	{
		super(s);
		setBlockTextureName("paintableGS");
		setLightLevel(1F);
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this), " P ", "PGP", " P ",
				'P', ODItems.FACADE_PAINTABLE,
				'G', Blocks.glowstone);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileGSPaintable(); }
}