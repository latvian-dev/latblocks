package latmod.latblocks.block;
import java.util.List;

import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.core.util.FastList;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.tile.paintable.TileGelLamp;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
		super(s, Material.wood);
		setLightLevel(1F);
		setHardness(0.1F);
		setBlockTextureName("lamp");
		isBlockContainer = true;
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 8), "GGG", "GSG", "LPL",
				'P', LatBlocksItems.b_cover,
				'L', ODItems.GLOWSTONE,
				'S', ODItems.SLIMEBALL,
				'G', ODItems.GLASS);
	}
	
	public TileLM createNewTileEntity(World w, int i)
	{ return new TileGelLamp(); }
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e) {}
	
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int s)
	{
		ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[s].getOpposite();
		Block b = w.getBlock(x + f.offsetX, y + f.offsetY, z + f.offsetZ);
		return (b != null && (b == Blocks.fence || b.isSideSolid(w, x, y, z, f.getOpposite())));
	}
	
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
		
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[m];
		
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
		int m = w.getBlockMetadata(x, y, z);
		ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[m];
		
		Block b1 = w.getBlock(x + f.offsetX, y + f.offsetY, z + f.offsetZ);
		if(b1 == null || b1 == Blocks.air)
		{
			dropBlockAsItem(w, x, y, z, new ItemStack(this));
			w.setBlockToAir(x, y, z);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{ return 1; }
}