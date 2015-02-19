package latmod.latblocks.block.paintable;

import java.util.List;

import latmod.core.util.FastList;
import latmod.latblocks.block.*;
import latmod.latblocks.tile.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public class BlockPDoor extends BlockPaintableSingle
{
	public BlockPDoor(String s)
	{
		super(s, 1F / 4F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePDoor(); }
	
	public void loadRecipes()
	{
		//mod.recipes.addRecipe(new ItemStack(this, 2), "PP", "PP", "PP",
		//		'P', LatBlocksItems.b_cover);
	}
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		int m = w.getBlockMetadata(x, y, z);
		if(this.isOpen(m)) return;
		else super.addCollisionBoxesToList(w, x, y, z, bb, l, e);
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		double b = 1D / 7D;
		double c0 = 0.5D - 1D / 16D;
		double c1 = 0.5D + 1D / 16D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(b * 0, 0D, c0, b * 1, 1D, c1));
		boxes.add(AxisAlignedBB.getBoundingBox(b * 3, 0D, c0, b * 4, 1D, c1));
		boxes.add(AxisAlignedBB.getBoundingBox(b * 6, 0D, c0, b * 7, 1D, c1));
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, b * 0, c0, 1D, b * 1, c1));
		boxes.add(AxisAlignedBB.getBoundingBox(0D, b * 3, c0, 1D, b * 4, c1));
		boxes.add(AxisAlignedBB.getBoundingBox(0D, b * 6, c0, 1D, b * 7, c1));
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		double hitX = mop.hitVec.xCoord - mop.blockX;
		double hitZ = mop.hitVec.zCoord - mop.blockZ;
		
		if(mop.sideHit == Placement.D_UP || mop.sideHit == Placement.D_DOWN)
		{
			Placement p = Placement.get(hitX, hitZ);
			
			if(p == Placement.CENTER || p == Placement.NONE) return -1;
			return p.ordinal() - 1;
		}
		
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
		double b0 = Placement.B0;
		double b1 = Placement.B1;
		
		if(event.target.sideHit == Placement.D_DOWN || event.target.sideHit == Placement.D_UP)
		{
			int h = event.target.sideHit == Placement.D_DOWN ? 1 : 0;
			boxes.add(AxisAlignedBB.getBoundingBox(0D, h, b0, 1D, h, b1));
			boxes.add(AxisAlignedBB.getBoundingBox(b0, h, 0D, b1, h, 1D));
			boxes.add(AxisAlignedBB.getBoundingBox(0D, h, 0D, 1D, h, 1D));
		}
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		int m1 = m % 8;
		double h = 1D / 4D;
		
		if(m1 == Placement.CENTER.ordinal() || m1 == Placement.NONE.ordinal())
		{
			boxes.add(AxisAlignedBB.getBoundingBox(h, 0D, h, 1D - h, h, 1D - h));
			return;
		}
		
		Placement p = Placement.values()[m1 + 1];
		
		double x1 = 0D;
		double x2 = 1D;
		double z1 = 0D;
		double z2 = 1D;
		
		if(p == Placement.UP)
		{
			z1 = 0D;
			z2 = h;
		}
		else if(p == Placement.UP_RIGHT)
		{
		}
		else if(p == Placement.RIGHT)
		{
		}
		else if(p == Placement.DOWN_RIGHT)
		{
		}
		else if(p == Placement.DOWN)
		{
		}
		else if(p == Placement.DOWN_LEFT)
		{
		}
		else if(p == Placement.LEFT)
		{
		}
		else if(p == Placement.UP_LEFT)
		{
		}
		
		boxes.add(AxisAlignedBB.getBoundingBox(x1, 0D, z1, x2, 1D, z2));
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		addBoxes(boxes, iba, x, y, z, m);
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{ setOpen(w, x, y, z, !isOpen(w.getBlockMetadata(x, y, z))); return true; }
	
	public boolean isOpen(int meta)
	{ return meta >= 8; }
	
	public void setOpen(World w, int x, int y, int z, boolean open)
	{
		int m0 = w.getBlockMetadata(x, y, z);
		
		if(open == isOpen(m0)) return;
		
		w.setBlockMetadataWithNotify(x, y, z, (m0 + 8) % 16, 3);
		
		for(int i = -2; i <= 2; i++)
		{
			if(i != 0 && w.getBlock(x, y + i, z) == this)
			{
				int m = w.getBlockMetadata(x, y + i, z);
				if(m == m0) w.setBlockMetadataWithNotify(x, y + i, z, (m + 8) % 16, 3);
			}
		}
		
		w.playAuxSFXAtEntity(null, 1003, x, y, z, 0);
	}
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{
		if (!w.isRemote)
		{
			boolean flag = w.isBlockIndirectlyGettingPowered(x, y, z);
			if(flag || b.canProvidePower()) setOpen(w, x, y, z, flag);
		}
	}
	
	public static class TilePDoor extends TileSinglePaintable
	{
	}
}