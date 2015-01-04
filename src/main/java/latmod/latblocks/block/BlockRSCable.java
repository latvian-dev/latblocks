package latmod.latblocks.block;
import latmod.core.*;
import latmod.core.MathHelper;
import latmod.core.tile.TileLM;
import latmod.latblocks.client.render.RenderRSCable;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileRSCable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockRSCable extends BlockLB
{
	public static final float pipeBorder = 1F / 32F * 12F;
	private static final FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
	
	static { updateBoxes(); }
	
	private static void updateBoxes()
	{
		boxes.clear();
		
		double d = pipeBorder;
		
		addBB(d, d, d, 1D - d, 1D - d, 1D - d);
		
		//addBB(f, 0D, f, 1D - f, h, 1D - f);
		addBB(d, 0D, d, 1D - d, d, 1D - d);
		
		//addBB(f, 1D - h, f, 1D - f, 1D, 1D - f);
		addBB(d, 1D - d, d, 1D - d, 1D, 1D - d);
		
		//addBB(f, f, 0D, 1D - f, 1D - f, h);
		addBB(d, d, 0D, 1D - d, 1D - d, d);
		
		//addBB(f, f, 1D - h, 1D - f, 1D - f, 1D);
		addBB(d, d, 1D - d, 1D - d, 1D - d, 1D);
		
		//addBB(0D, f, f, h, 1D - f, 1D - f);
		addBB(0D, d, d, d, 1D - d, 1D - d);
		
		//addBB(1D - h, f, f, 1D, 1D - f, 1D - f);
		addBB(1D - d, d, d, 1D, 1D - d, 1D - d);
	}
	
	private static void addBB(double x1, double y1, double z1, double x2, double y2, double z2)
	{ boxes.add(AxisAlignedBB.getBoundingBox(x1, y1, z1, x2, y2, z2)); }
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_on;
	
	public BlockRSCable(String s)
	{
		super(s, Material.iron);
		setHardness(0.4F);
		isBlockContainer = true;
		mod.addTile(TileRSCable.class, s);
	}
	
	public boolean canHarvestBlock(EntityPlayer ep, int meta)
	{ return true; }
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileRSCable(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 8), "SRS", "SRS", "SRS",
				'R', ItemMaterialsLB.GEM_RED_GLOWIUM,
				'S', ODItems.STICK);
	}
	
	public void setBlockBoundsForItemRender()
	{
		float s = pipeBorder;
		setBlockBounds(0F, s, s, 1F, 1F - s, 1F - s);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		TileEntity te = iba.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileRSCable)
		{
			TileRSCable t = (TileRSCable)te;
			
			if(t.isInvalid() || t.hasCover)
			{
				setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
				return;
			}
			
			float s = pipeBorder;// - 1 / 16F;
			
			boolean x0 = TileRSCable.connectCable(t, ForgeDirection.WEST);
			boolean x1 = TileRSCable.connectCable(t, ForgeDirection.EAST);
			boolean y0 = TileRSCable.connectCable(t, ForgeDirection.DOWN);
			boolean y1 = TileRSCable.connectCable(t, ForgeDirection.UP);
			boolean z0 = TileRSCable.connectCable(t, ForgeDirection.NORTH);
			boolean z1 = TileRSCable.connectCable(t, ForgeDirection.SOUTH);
			
			setBlockBounds(x0 ? 0F : s, y0 ? 0F : s, z0 ? 0F: s, x1 ? 1F : 1F - s, y1 ? 1F: 1F - s, z1 ? 1F : 1F - s);
		}
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderRSCable.instance.getRenderId(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "rsCable");
		icon_on = ir.registerIcon(mod.assets + "rsCableOn");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return blockIcon; }
	
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	public int isProvidingWeakPower(IBlockAccess iba, int x, int y, int z, int s)
	{
		TileRSCable t = (TileRSCable)iba.getTileEntity(x, y, z);
		return (t != null && t.isValid() && t.power) ? 15 : 0;
	}
	
	public boolean canConnectRedstone(IBlockAccess iba, int x, int y, int z, int side)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileRSCable)
		{
			if(((TileRSCable)te).hasCover)
			{
				return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
			}
			
			updateBoxes();
			
			MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
			
			if(mop != null && mop.subHit >= 0 && mop.subHit < boxes.size())
			{
				AxisAlignedBB aabb = boxes.get(mop.subHit).copy();
				aabb.minX += x; aabb.maxX += x;
				aabb.minY += y; aabb.maxY += y;
				aabb.minZ += z; aabb.maxZ += z;
				return aabb;
			}
		}
		
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);
	}
	
	public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 start, Vec3 end)
	{
		TileEntity te = w.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileRSCable)
		{
			if(((TileRSCable)te).hasCover)
			{
				return super.collisionRayTrace(w, x, y, z, start, end);
			}
			
			updateBoxes();
			
			for(int i = 0; i < boxes.size(); i++)
			{
				if(!((TileRSCable)te).isAABBEnabled(i))
					boxes.set(i, null);
			}
			
			return MathHelper.collisionRayTrace(w, x, y, z, start, end, boxes);
		}
		
		return null;
	}
}