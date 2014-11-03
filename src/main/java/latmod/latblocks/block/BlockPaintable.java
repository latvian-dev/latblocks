package latmod.latblocks.block;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.tile.TilePaintable;
import latmod.latcore.LC;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPaintable extends BlockLB
{
	public static int renderID = -1;
	
	public BlockPaintable(String s)
	{
		super(s, Material.rock);
		setHardness(0.3F);
		setBlockTextureName("paintable");
		isBlockContainer = true;
		LC.mod.addTile(createNewTileEntity(null, 0).getClass(), s);
	}
	
	public boolean canHarvestBlock(EntityPlayer ep, int meta)
	{ return true; }
	
	public void onPostLoaded()
	{ LatCoreMC.addOreDictionary(ODItems.BLOCK_PAINTABLE, new ItemStack(this)); }
	
	public void loadRecipes()
	{
		LC.mod.recipes().addRecipe(new ItemStack(this, 16), "WWW", "WPW", "WWW",
				'W', new ItemStack(Blocks.wool, 1, LatCoreMC.ANY),
				'P', ODItems.PLANKS);
		
		LC.mod.recipes().addShapelessRecipe(new ItemStack(this), ODItems.BLOCK_PAINTABLE);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePaintable(); }
	
	public int getRenderType()
	{ return renderID; }
	
	/*
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{ return 0; }
	
	*/
}