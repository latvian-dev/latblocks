package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.tile.TilePaintableGS;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPaintableGS extends BlockPaintable
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
				'P', ODItems.PAINTABLE_COVER,
				'G', Blocks.glowstone);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePaintableGS(); }
}