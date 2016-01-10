package latmod.latblocks.block;

import ftb.lib.item.ODItems;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.item.ItemMaterialsLB;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGlowiumFarmland extends BlockLB
{
	public BlockGlowiumFarmland(String s)
	{
		super(s, Material.ground);
		isBlockContainer = false;
		setHardness(0.5F);
		setLightLevel(12F / 16F);
		setStepSound(soundTypeGravel);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 8), "SDO", "TGT", "ODS", 'S', ODItems.SAND, 'D', Blocks.dirt, 'O', Blocks.soul_sand, 'T', ODItems.GLOWSTONE, 'G', ItemMaterialsLB.DUST_GLOWIUM_G);
	}
	
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{ return true; }
	
	public boolean isFertile(World world, int x, int y, int z)
	{ return true; }
}