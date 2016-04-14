package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.*;
import ftb.lib.MathHelperMC;
import latmod.latblocks.block.*;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import java.util.*;

public class BlockPLadder extends BlockPaintableSingle
{
	public static final AxisAlignedBB[] ladder_boxes = new AxisAlignedBB[6];
	
	static
	{
		double c0 = 0.5D - 1D / 16D;
		double c1 = 0.5D + 1D / 16D;
		
		double d = 1D / 16D;
		double dz = 1D / 64D;
		
		double b = 1D / 128D;
		
		ladder_boxes[0] = AxisAlignedBB.getBoundingBox(d, 0D, c0, d * 3, 1D, c1);
		ladder_boxes[1] = AxisAlignedBB.getBoundingBox(d * 13, 0D, c0, d * 15, 1D, c1);
		
		ladder_boxes[2] = AxisAlignedBB.getBoundingBox(b, d * 1, c0 + dz, 1D - b, d * 3, c1 - dz);
		ladder_boxes[3] = AxisAlignedBB.getBoundingBox(b, d * 5, c0 + dz, 1D - b, d * 7, c1 - dz);
		ladder_boxes[4] = AxisAlignedBB.getBoundingBox(b, d * 9, c0 + dz, 1D - b, d * 11, c1 - dz);
		ladder_boxes[5] = AxisAlignedBB.getBoundingBox(b, d * 13, c0 + dz, 1D - b, d * 15, c1 - dz);
	}
	
	public BlockPLadder(String s)
	{
		super(s, 1F / 8F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePLadder(); }
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 2), "S S", "SSS", "S S", 'S', ItemMaterialsLB.ROD);
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(List<AxisAlignedBB> boxes)
	{
		Collections.addAll(boxes, ladder_boxes);
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		int i = super.onBlockPlaced(w, ep, mop, m);
		if(i == 0 || i == 1) return -1;
		return i;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(List<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
		if(onBlockPlaced(event.player.worldObj, event.player, event.target, -1) == -1) return;
		super.drawHighlight(boxes, event);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		int m = iba.getBlockMetadata(x, y, z);
		
		if(m == Placement.D_DOWN) setBlockBounds(0F, 0F, 0F, 1F, height, 1F);
		else if(m == Placement.D_UP) setBlockBounds(0F, 1F - height, 0F, 1F, 1F, 1F);
		else if(m == Placement.D_NORTH) setBlockBounds(0F, 0F, 0F, 1F, 1F, height);
		else if(m == Placement.D_SOUTH) setBlockBounds(0F, 0F, 1F - height, 1F, 1F, 1F);
		else if(m == Placement.D_WEST) setBlockBounds(0F, 0F, 0F, height, 1F, 1F);
		else if(m == Placement.D_EAST) setBlockBounds(1F - height, 0F, 0F, 1F, 1F, 1F);
	}
	
	public void addBoxes(List<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		super.addBoxes(boxes, iba, x, y, z, m);
		
		//if(m == -1) m = iba.getBlockMetadata(x, y, z);
		//boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.5D, 0.5D, h1, 1.0D))
	}
	
	public boolean isLadder(IBlockAccess iba, int x, int y, int z, EntityLivingBase e)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(List<AxisAlignedBB> boxes0, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		AxisAlignedBB[] boxes = ladder_boxes.clone();
		
		double shift = 0.5D - 1D / 16D;
		
		for(int i = 0; i < boxes.length; i++)
		{
			boxes[i] = MathHelperMC.rotate90BoxV(boxes[i], m);
			boxes[i] = boxes[i].getOffsetBoundingBox(Facing.offsetsXForSide[m] * shift, 0D, Facing.offsetsZForSide[m] * shift);
		}
		
		Collections.addAll(boxes0, boxes);
	}
	
	public static class TilePLadder extends TileSinglePaintable
	{ }
}