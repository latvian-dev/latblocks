package latmod.latblocks.block.paintable;

import ftb.lib.api.item.ODItems;
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
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPaintableDef.ORE_NAME, new ItemStack(this));
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " P ", "PLP", " P ", 'P', LatBlocksItems.b_cover, 'L', Blocks.redstone_lamp);
	}
	
	@Override
	public int getLightValue(IBlockAccess iba, int x, int y, int z)
	{
		TilePaintableLamp t = (TilePaintableLamp) getTile(iba, x, y, z);
		return (t != null && t.power > 0) ? 15 : 0;
	}
	
	@Override
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePaintableLamp(); }
	
	public static class TilePaintableLamp extends TilePaintableRS
	{
		private boolean prevLit = false;
		
		@Override
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