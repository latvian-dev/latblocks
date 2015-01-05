package latmod.latblocks.block.tank;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.tile.tank.TileVoidTank;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockVoidTank extends BlockTankBase
{
	public BlockVoidTank(String s)
	{
		super(s);
		mod.addTile(TileVoidTank.class, s);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "WEW", "ETE", "WEW",
				'W', Items.water_bucket,
				'T', new ItemStack(LatBlocksItems.b_tank, 1, 1),
				'E', Blocks.obsidian);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileVoidTank(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{ blockIcon = ir.registerIcon(mod.assets + "tank/outside_void"); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(int m)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemFluidIcon(int m)
	{ return null; }
}