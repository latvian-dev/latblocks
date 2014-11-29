package latmod.latblocks.block;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.tile.TileVoidTank;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockVoidTank extends BlockLB
{
	public BlockVoidTank(String s)
	{
		super(s, Material.iron);
		isBlockContainer = true;
		mod.addTile(TileVoidTank.class, s);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), " E ", "STS", " S ",
				'E', Items.ender_pearl,
				'S', Blocks.stone,
				'T', LatBlocksItems.b_tank_water);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileVoidTank(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{ blockIcon = ir.registerIcon(mod.assets + "tank_void"); }
}