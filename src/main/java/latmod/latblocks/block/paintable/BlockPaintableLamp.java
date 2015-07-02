package latmod.latblocks.block.paintable;
import latmod.ftbu.core.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.tile.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;

public class BlockPaintableLamp extends BlockPaintableRS
{
	public BlockPaintableLamp(String s)
	{
		super(s);
		setBlockTextureName("paintable_lamp");
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPaintableDef.ORE_NAME, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), " P ", "PLP", " P ",
				'P', LatBlocksItems.b_cover,
				'L', Blocks.redstone_lamp);
	}
	
	public int getLightValue(IBlockAccess iba, int x, int y, int z)
	{
		TilePaintableLamp t = getTile(TilePaintableLamp.class, iba, x, y, z);
		return (t != null && t.power > 0) ? 15 : 0;
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePaintableLamp(); }
	
	public static class TilePaintableLamp extends TilePaintableRS
	{
		private boolean prevLit = false;
		
		public void onUpdate()
		{
			if(prevLit != power > 0)
			{
				prevLit = power > 0;
				worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
			}
		}
	}
}