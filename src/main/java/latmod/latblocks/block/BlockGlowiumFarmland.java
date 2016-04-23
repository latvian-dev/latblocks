package latmod.latblocks.block;

import ftb.lib.api.item.ODItems;
import latmod.latblocks.item.ItemMaterialsLB;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGlowiumFarmland extends BlockLB
{
	public BlockGlowiumFarmland(String s)
	{
		super(s, Material.ground);
		setHardness(0.5F);
		setLightLevel(12F / 16F);
		setStepSound(soundTypeGravel);
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 8), "SDO", "TGT", "ODS", 'S', ODItems.SAND, 'D', Blocks.dirt, 'O', Blocks.soul_sand, 'T', ODItems.GLOWSTONE, 'G', ItemMaterialsLB.DUST_GLOWIUM_G);
	}
	
	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{ return true; }
	
	@Override
	public boolean isFertile(World world, int x, int y, int z)
	{ return true; }
}