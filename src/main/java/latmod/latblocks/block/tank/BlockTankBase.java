package latmod.latblocks.block.tank;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.paint.*;
import latmod.ftbu.util.client.LatCoreMCClient;
import latmod.latblocks.block.BlockLB;
import latmod.latblocks.client.render.world.RenderTank;
import latmod.latblocks.tile.tank.TileTankBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public abstract class BlockTankBase extends BlockLB implements ICustomPaintBlock
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
	{ return LatCoreMCClient.blockNullIcon; }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    { return true; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
	}
	
	public Paint getCustomPaint(World w, int x, int y, int z)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		if(te != null && te instanceof TileTankBase)
		{
			TileTankBase t = (TileTankBase)te;
			if(t != null && t.tank.hasFluid())
			{
				Block b = t.tank.getFluid().getBlock();
				if(b != null) return new Paint(b, 0);
			}
		}
		
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public abstract IIcon getTankItemBorderIcon(ItemStack item);
	
	@SideOnly(Side.CLIENT)
	public abstract FluidStack getTankItemFluid(ItemStack item);
}