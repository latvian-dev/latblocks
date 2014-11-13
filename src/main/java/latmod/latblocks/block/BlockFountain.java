package latmod.latblocks.block;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.client.render.*;
import latmod.latblocks.tile.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockFountain extends BlockLB
{
	public BlockFountain(String s)
	{
		super(s, Material.rock);
		setHardness(1F);
		setBlockTextureName("paintable");
		isBlockContainer = true;
		LatBlocks.mod.addTile(TileFountain.class, s);
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this), " H ", "PGP", "SSS",
				'H', Blocks.hopper,
				'G', ODItems.GLASS,
				'S', ODItems.STONE,
				'P', ODItems.BLOCK_PAINTABLE);
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
}