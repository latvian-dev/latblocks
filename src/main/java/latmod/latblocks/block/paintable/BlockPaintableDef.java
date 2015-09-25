package latmod.latblocks.block.paintable;
import latmod.ftbu.inv.ODItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPaintableDef extends BlockPaintableSided
{
	public static final String ORE_NAME = "blockPaintable";
	
	public BlockPaintableDef(String s)
	{
		super(s);
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(ORE_NAME, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 16), "WWW", "WQW", "WWW",
				'W', new ItemStack(Blocks.wool, 1, 0),
				'Q', ODItems.QUARTZ);
		
		mod.recipes.addShapelessRecipe(new ItemStack(this, 1), ORE_NAME);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePaintableDef(); }
	
	public static class TilePaintableDef extends TileSidedPaintable
	{
	}
}