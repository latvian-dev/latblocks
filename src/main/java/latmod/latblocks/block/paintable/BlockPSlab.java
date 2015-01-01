package latmod.latblocks.block.paintable;

import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.paintable.TilePSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPSlab extends BlockPaintableSingle
{
	public BlockPSlab(String s)
	{
		super(s, Material.rock, 1F / 2F);
		setHardness(1.5F);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 6), "PPP",
				'P', LatBlocksItems.b_paintable);
		
		mod.recipes.addShapelessRecipe(new ItemStack(this, 2), LatBlocksItems.b_paintable, ODItems.TOOL_SAW);
		mod.recipes.addShapelessRecipe(new ItemStack(LatBlocksItems.b_paintable), this, this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePSlab(); }
}