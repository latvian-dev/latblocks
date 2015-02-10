package latmod.latblocks.block.paintable;
import latmod.core.ODItems;
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
		setBlockTextureName("paintableLamp");
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
		TilePaintableLamp t = (TilePaintableLamp)iba.getTileEntity(x, y, z);
		if(t != null && t.isValid()) if(t.power > 0) return 15; return 0;
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