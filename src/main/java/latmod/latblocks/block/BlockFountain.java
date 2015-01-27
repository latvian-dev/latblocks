package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.tank.BlockTank;
import latmod.latblocks.client.render.RenderFountain;
import latmod.latblocks.tile.TileFountain;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockFountain extends BlockLB
{
	public BlockFountain(String s)
	{
		super(s, Material.rock);
		setHardness(1.5F);
		setBlockTextureName("paintable");
		isBlockContainer = true;
		mod.addTile(TileFountain.class, s);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), " P ", "PGP", "SHS",
				'H', Blocks.hopper,
				'G', BlockTank.TANK_BASIC,
				'S', ODItems.STONE,
				'P', LatBlocksItems.b_paintable);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileFountain(); }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderFountain.instance.getRenderId(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    { return true; }
}