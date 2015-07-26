package latmod.latblocks.block.paintable;

import latmod.ftbu.core.util.*;
import latmod.latblocks.block.*;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockPLadder extends BlockPaintableSingle
{
	public static final AxisAlignedBB[] ladder_boxes = new AxisAlignedBB[6];
	
	static
	{
		double c0 = 0.5D - 1D / 16D;
		double c1 = 0.5D + 1D / 16D;
		
		double d = 1D / 16D;
		double dz = 1D / 64D;
		
		ladder_boxes[0] = AxisAlignedBB.getBoundingBox(d, 0D, c0, d * 3, 1D, c1);
		ladder_boxes[1] = AxisAlignedBB.getBoundingBox(d * 13, 0D, c0, d * 15, 1D, c1);
		
		ladder_boxes[2] = AxisAlignedBB.getBoundingBox(0D, d * 1, c0 + dz, 1D, d * 3, c1 - dz);
		ladder_boxes[3] = AxisAlignedBB.getBoundingBox(0D, d * 5, c0 + dz, 1D, d * 7, c1 - dz);
		ladder_boxes[4] = AxisAlignedBB.getBoundingBox(0D, d * 9, c0 + dz, 1D, d * 11, c1 - dz);
		ladder_boxes[5] = AxisAlignedBB.getBoundingBox(0D, d * 13, c0 + dz, 1D, d * 15, c1 - dz);
	}
	
	public BlockPLadder(String s)
	{
		super(s, 1F / 8F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePLadder(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 2), "S S", "SSS", "S S",
				'S', ItemMaterialsLB.ROD.stack);
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		boxes.addAll(ladder_boxes);
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		int i = super.onBlockPlaced(w, ep, mop, m);
		if(i == 0 || i == 1) return -1;
		return i;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
		if(onBlockPlaced(event.player.worldObj, event.player, event.target, -1) == -1)
			return;
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
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		super.addBoxes(boxes, iba, x, y, z, m);
		
		//if(m == -1) m = iba.getBlockMetadata(x, y, z);
		//boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.5D, 0.5D, h1, 1.0D))
	}
	
	public boolean isLadder(IBlockAccess iba, int x, int y, int z, EntityLivingBase e)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes0, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		AxisAlignedBB[] boxes = ladder_boxes.clone();
		
		ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[m];
		
		double shift = 0.5D - 1D / 16D;
		
		for(int i = 0; i < boxes.length; i++)
		{
			boxes[i] = MathHelperLM.rotate90BoxV(boxes[i], f);
			boxes[i] = boxes[i].getOffsetBoundingBox(f.offsetX * shift, 0D, f.offsetZ * shift);
		}
		
		boxes0.addAll(boxes);
	}
	
	public static class TilePLadder extends TileSinglePaintable
	{
	}
}