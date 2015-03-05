package latmod.latblocks.block.tank;
import latmod.core.LatCoreMC;
import latmod.latblocks.block.BlockLB;
import latmod.latblocks.client.render.world.RenderTank;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.*;

public abstract class BlockTankBase extends BlockLB
{
	public BlockTankBase(String s)
	{
		super(s, Material.iron);
		setHardness(1.5F);
		setBlockTextureName("tank/inside");
		isBlockContainer = true;
	}
	
	public void loadRecipes()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderTank.instance.getRenderId(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return LatCoreMC.blockNullIcon; }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    { return true; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public abstract IIcon getTankItemBorderIcon(ItemStack item);
	
	@SideOnly(Side.CLIENT)
	public abstract FluidStack getTankItemFluid(ItemStack item);
}