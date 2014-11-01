package latmod.latblocks.block;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latcore.LC;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockFacade extends BlockLB
{
	public BlockFacade(String s)
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
	
	public void loadRecipes()
	{
		LC.mod.recipes().addRecipe(new ItemStack(this, 8), "W",
				'W', LatBlocksItems.b_paintable);
		
		LC.mod.recipes().addRecipe(new ItemStack(LatBlocksItems.b_paintable), "WWW", "W W", "WWW",
				'W', this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
}