package latmod.latblocks.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.item.LMInvUtils;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileSinglePaintable;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockGelLamp extends BlockPaintableLB
{
	public BlockGelLamp(String s)
	{
		super(s);
		setLightLevel(1F);
		setHardness(0.1F);
		setBlockTextureName("lamp");
		hasSpecialPlacement = true;
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addShapelessRecipe(new ItemStack(this, 8), ItemMaterialsLB.DUST_GLOWIUM_Y, ODItems.SLIMEBALL);
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileGelLamp(); }
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		if(LMInvUtils.isWrench(ep.inventory.getCurrentItem()))
		{
			int m = w.getBlockMetadata(x, y, z);
			if(m < 6) w.setBlockMetadataWithNotify(x, y, z, m + 6, 3);
			else w.setBlockMetadataWithNotify(x, y, z, m - 6, 3);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void addCollisionBoxes(World w, int x, int y, int z, int m, List<AxisAlignedBB> boxes, Entity e) {}
	
	@Override
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int s)
	{ return true; }
	
	@Override
	public boolean isOpaqueCube()
	{ return false; }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawHighlight(List<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@Override
	public void setBlockBoundsForItemRender()
	{
		float f = 1F / 16F * 1F;
		float h = 1F / 16F * 8F;
		
		setBlockBounds(f, 0F, f, 1F - f, h, 1F - f);
	}
	
	@Override
	public void addBoxes(List<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		double f = 1D / 16D * 5D;
		double h = 1D / 16D * 3D;
		
		if(m >= 6)
		{
			f = 1D / 8D;
			h = 1D / 16D;
		}
		
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[m % 6];
		
		if(dir == ForgeDirection.UP) // y+
			boxes.add(AxisAlignedBB.getBoundingBox(f, 1D - h, f, 1D - f, 1D, 1D - f));
		else if(dir == ForgeDirection.DOWN) //y-
			boxes.add(AxisAlignedBB.getBoundingBox(f, 0D, f, 1D - f, h, 1D - f));
		
		else if(dir == ForgeDirection.EAST) //x
			boxes.add(AxisAlignedBB.getBoundingBox(1D - h, f, f, 1D, 1D - f, 1D - f));
		else if(dir == ForgeDirection.WEST) //x
			boxes.add(AxisAlignedBB.getBoundingBox(0D, f, f, h, 1D - f, 1D - f));
		
		else if(dir == ForgeDirection.NORTH) // z-
			boxes.add(AxisAlignedBB.getBoundingBox(f, f, 0D, 1D - f, 1D - f, h));
		else if(dir == ForgeDirection.SOUTH) // z+
			boxes.add(AxisAlignedBB.getBoundingBox(f, f, 1D - h, 1D - f, 1D - f, 1D));
	}
	
	@Override
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return ForgeDirection.VALID_DIRECTIONS[mop.sideHit].getOpposite().ordinal(); }
	
	@Override
	public int damageDropped(int i)
	{ return 0; }
	
	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{
		ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[w.getBlockMetadata(x, y, z) % 6];
		
		if(w.getBlock(x + f.offsetX, y + f.offsetY, z + f.offsetZ) == Blocks.air)
		{
			dropBlockAsItem(w, x, y, z, new ItemStack(this));
			w.setBlockToAir(x, y, z);
		}
	}
	
	public static class TileGelLamp extends TileSinglePaintable
	{ }
}