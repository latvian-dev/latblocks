package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.latcore.LC;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

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
		LC.mod.recipes().addRecipe(new ItemStack(this), " P ", "PGP", " P ",
				'P', ODItems.FACADE_PAINTABLE,
				'G', Blocks.glowstone);
	}
}