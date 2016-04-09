package latmod.latblocks.block.tank;

import cpw.mods.fml.relauncher.*;
import latmod.latblocks.tile.tank.TileVoidTank;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class BlockVoidTank extends BlockTankBase
{
	public BlockVoidTank(String s)
	{ super(s); }
	
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileVoidTank(); }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileVoidTank.class, blockName);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "WEW", "ETE", "WEW", 'W', Items.water_bucket, 'T', BlockTank.TANK_BASIC, 'E', Blocks.obsidian);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{ blockIcon = ir.registerIcon(getMod().lowerCaseModID + ":tank/outside_void"); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(ItemStack item)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public FluidStack getTankItemFluid(ItemStack item)
	{ return null; }
}