package latmod.latblocks.block.tank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.client.FTBLibClient;
import latmod.latblocks.api.ICustomPaintBlock;
import latmod.latblocks.api.Paint;
import latmod.latblocks.block.BlockLB;
import latmod.latblocks.client.render.world.RenderTank;
import latmod.latblocks.tile.tank.TileTankBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public abstract class BlockTankBase extends BlockLB implements ICustomPaintBlock
{
	public BlockTankBase(String s)
	{
		super(s, Material.iron);
		setHardness(1.5F);
		setBlockTextureName("tank/inside");
	}
	
	@Override
	public void loadRecipes()
	{
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderTank.instance.getRenderId(); }
	
	@Override
	public boolean isOpaqueCube()
	{ return false; }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@Override
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	@Override
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return FTBLibClient.blockNullIcon; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
	{ return true; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
	}
	
	@Override
	public Paint getCustomPaint(World w, int x, int y, int z)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		if(te != null && te instanceof TileTankBase)
		{
			TileTankBase t = (TileTankBase) te;
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