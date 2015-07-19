package latmod.latblocks.block;
import latmod.ftbu.core.inv.*;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockGelLamp extends BlockPaintableLB
{
	public BlockGelLamp(String s)
	{
		super(s);
		setLightLevel(1F);
		setHardness(0.1F);
		setBlockTextureName("lamp");
		isBlockContainer = true;
		hasSpecialPlacement = true;
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 8), "GS", "SG",
				'G', ItemMaterialsLB.DUSTS_GLOWIUM[0],
				'S', ODItems.SLIMEBALL);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int i)
	{ return new TileGelLamp(); }
	
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
	
	public void addCollisionBoxes(World w, int x, int y, int z, int m, FastList<AxisAlignedBB> boxes, Entity e) {}
	
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int s)
	{ return true; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	public void setBlockBoundsForItemRender()
	{
		float f = 1F / 16F * 1F;
		float h = 1F / 16F * 8F;
		
		setBlockBounds(f, 0F, f, 1F - f, h, 1F - f);
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
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
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return ForgeDirection.VALID_DIRECTIONS[mop.sideHit].getOpposite().ordinal(); }
	
	public int damageDropped(int i)
	{ return 0; }
	
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
	{
	}
}