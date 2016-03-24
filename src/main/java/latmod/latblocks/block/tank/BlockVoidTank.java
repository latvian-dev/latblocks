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
	{
		super(s);
		getMod().addTile(TileVoidTank.class, s);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "WEW", "ETE", "WEW", 'W', Items.water_bucket, 'T', BlockTank.TANK_BASIC, 'E', Blocks.obsidian);
	}
	
	public TileEntity createNewTileEntity(World w, int m)
	{ return new TileVoidTank(); }
	
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