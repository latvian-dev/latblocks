package latmod.latblocks.block.tank;

import cpw.mods.fml.relauncher.*;
import latmod.latblocks.client.render.world.RenderTank;
import latmod.latblocks.tile.tank.TileWaterTank;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class BlockWaterTank extends BlockTankBase
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_on;
	
	public BlockWaterTank(String s)
	{ super(s); }
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileWaterTank(); }
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileWaterTank.class, blockName);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "WEW", "ETE", "WEW", 'W', Items.water_bucket, 'T', BlockTank.TANK_BASIC, 'E', Items.ender_pearl);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(getMod().lowerCaseModID + ":tank/outside_water_off");
		icon_on = ir.registerIcon(getMod().lowerCaseModID + ":tank/outside_water_on");
		RenderTank.icon_inside = ir.registerIcon(getMod().lowerCaseModID + ":tank/inside");
	}
	
	@Override
	public int damageDropped(int i)
	{ return 0; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(ItemStack item)
	{ return (item.getItemDamage() == 1) ? icon_on : blockIcon; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public FluidStack getTankItemFluid(ItemStack item)
	{ return TileWaterTank.WATER_1000; }
}