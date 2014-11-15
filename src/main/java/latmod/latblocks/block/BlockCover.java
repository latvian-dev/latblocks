package latmod.latblocks.block;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockCover extends BlockLB
{
	public BlockCover(String s)
	{
		super(s, Material.rock);
		setHardness(0.3F);
		isBlockContainer = false;
		setBlockTextureName("paintable");
		setBlockBounds(0.5F - 1F / 16F, 0F, 0F, 0.5F + 1F / 16F, 1F, 1F);
	}
	
	public boolean canPlaceBlockAt(World w, int x, int y, int z)
	{ return false; }
	
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int side)
	{ return false; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		LatCoreMC.addOreDictionary(ODItems.PAINTABLE_COVER_ANY, new ItemStack(this));
		
		if(this == LatBlocksItems.b_cover)
			LatCoreMC.addOreDictionary(ODItems.PAINTABLE_COVER, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes().addShapelessRecipe(new ItemStack(this), ODItems.PAINTABLE_COVER_ANY);
		
		mod.recipes().addRecipe(new ItemStack(this, 16), "PP", "PP",
				'P', ODItems.PAINTABLE_BLOCK);
		
		mod.recipes().addShapelessRecipe(new ItemStack(this), ODItems.PAINTABLE_BLOCK, ODItems.TOOL_SAW);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
}