package latmod.latblocks.block.blocks;

import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.blocks.TilePCarpet;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPCarpet extends BlockPaintableSingle
{
	public BlockPCarpet(String s)
	{
		super(s, Material.cloth, 1F / 16F);
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this, 3), "PPP",
				'P', LatBlocksItems.b_cover);
		
		mod.recipes().addShapelessRecipe(new ItemStack(this), LatBlocksItems.b_cover, ODItems.TOOL_SAW);
		mod.recipes().addShapelessRecipe(new ItemStack(LatBlocksItems.b_cover), this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePCarpet(); }
}