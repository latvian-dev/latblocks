package latmod.latblocks.block.tank;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.client.render.world.RenderTank;
import latmod.latblocks.tile.tank.TileWaterTank;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class BlockWaterTank extends BlockTankBase
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_on;
	
	public BlockWaterTank(String s)
	{
		super(s);
		getMod().addTile(TileWaterTank.class, s);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "WEW", "ETE", "WEW",
				'W', Items.water_bucket,
				'T', BlockTank.TANK_BASIC,
				'E', Items.ender_pearl);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileWaterTank(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(getMod().assets + "tank/outside_water_off");
		icon_on = ir.registerIcon(getMod().assets + "tank/outside_water_on");
		RenderTank.icon_inside = ir.registerIcon(getMod().assets + "tank/inside");
	}
	
	public int damageDropped(int i)
	{ return 0; }

	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(ItemStack item)
	{ return (item.getItemDamage() == 1) ? icon_on : blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public FluidStack getTankItemFluid(ItemStack item)
	{ return TileWaterTank.WATER_1000; }
}