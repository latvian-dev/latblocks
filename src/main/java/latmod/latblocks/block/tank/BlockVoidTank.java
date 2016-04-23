package latmod.latblocks.block.tank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import latmod.latblocks.tile.tank.TileVoidTank;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class BlockVoidTank extends BlockTankBase
{
	public BlockVoidTank(String s)
	{ super(s); }
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileVoidTank(); }
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileVoidTank.class, blockName);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "WEW", "ETE", "WEW", 'W', Items.water_bucket, 'T', BlockTank.TANK_BASIC, 'E', Blocks.obsidian);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{ blockIcon = ir.registerIcon(getMod().lowerCaseModID + ":tank/outside_void"); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(ItemStack item)
	{ return blockIcon; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public FluidStack getTankItemFluid(ItemStack item)
	{ return null; }
}