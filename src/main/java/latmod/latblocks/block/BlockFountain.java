package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.client.render.RenderFountain;
import latmod.latblocks.tile.TileFountain;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockFountain extends BlockLB
{
	public BlockFountain(String s)
	{
		super(s, Material.water);
		setHardness(1.5F);
		setBlockTextureName("paintable");
		isBlockContainer = true;
		LatBlocks.mod.addTile(TileFountain.class, s);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), " G ", "PGP", "SHS",
				'H', Blocks.hopper,
				'G', ODItems.GLASS,
				'S', ODItems.STONE,
				'P', ODItems.PAINTABLE_BLOCK);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileFountain(); }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderFountain.instance.getRenderId(); }
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{ return 0; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	public boolean isReplaceable(IBlockAccess iba, int x, int y, int z)
	{ return false; }
	
	@SideOnly(Side.CLIENT)
    public String getItemIconName()
    { return null; }//mod.assets + "fountain"; }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    { return true; }
}