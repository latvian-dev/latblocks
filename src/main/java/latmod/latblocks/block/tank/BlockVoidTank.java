package latmod.latblocks.block.tank;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.tile.tank.TileVoidTank;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

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
				'T', BlockTank.TANK_BASIC,
				'E', Blocks.obsidian);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileVoidTank(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{ blockIcon = ir.registerIcon(mod.assets + "tank/outside_void"); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(ItemStack item)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public FluidStack getTankItemFluid(ItemStack item)
	{ return null; }
}