package latmod.latblocks.block.blocks;

import latmod.core.tile.TileLM;
import latmod.core.util.FastList;
import latmod.latblocks.block.*;
import latmod.latblocks.tile.blocks.TilePStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public class BlockPStairs extends BlockPaintableSingle
{
	public BlockPStairs(String s)
	{
		super(s, Material.wood, 1F / 2F);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePStairs(); }
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 0.5D, 1D));
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0.5D, 0.5D, 1D, 1D, 1D));
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		double hitY = mop.hitVec.yCoord - mop.blockY;
		
		if(!(mop.sideHit == Placement.D_DOWN || mop.sideHit == Placement.D_UP))
			hitY = 1D - hitY;
		
		int l = MathHelper.floor_double((double)(ep.rotationYaw * 8F / 360F) + 0.5D) & 7;
		return (hitY >= 0.5D) ? (8 + l) : l;
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, int m)
	{
		if(m == -1) return;
		
		boolean isUp = (m / 8) == 0;
		
		double h = isUp ? 0D : 0.5D;
		double h1 = isUp ? 0.5D : 1D;
		
		int s = m % 8;
		boolean addUp = s == 7 || s == 0 || s == 1;
		boolean addLeft = s == 1 || s == 2 || s == 3;
		boolean addDown = s == 3 || s == 4 || s == 5;
		boolean addRight = s == 5 || s == 6 || s == 7;
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 1D - h1, 0D, 1D, 1D - h, 1D));
		
		if(addUp) boxes.add(AxisAlignedBB.getBoundingBox(0D, h, 0.5D, 1D, h1, 1D));
		if(addRight) boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0D, 1D, h1, 1D));
		if(addDown) boxes.add(AxisAlignedBB.getBoundingBox(0D, h, 0D, 1D, h1, 0.5D));
		if(addLeft) boxes.add(AxisAlignedBB.getBoundingBox(0D, h, 0D, 0.5D, h1, 1D));
	}
	
	@SideOnly(Side.CLIENT)
	public void getPlacementBoxes(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
		addBoxes(boxes, onBlockPlaced(event.player.worldObj, event.player, event.target, 0));
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, int m)
	{
		if(m == -1) return;
		
		boolean isUp = (m / 8) == 0;
		
		double h = isUp ? 0D : 0.5D;
		double h1 = isUp ? 0.5D : 1D;
		
		int s = m % 8;
		
		boolean addUp = s == 7 || s == 0 || s == 1;
		boolean addLeft = s == 1 || s == 2 || s == 3;
		boolean addDown = s == 3 || s == 4 || s == 5;
		boolean addRight = s == 5 || s == 6 || s == 7;
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 1D - h1, 0D, 1D, 1D - h, 1D));
		
		if(addUp || addLeft)	boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.5D, 0.5D, h1, 1.0D));
		if(addUp || addRight)	boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.5D, 1.0D, h1, 1.0D));
		if(addDown || addLeft)	boxes.add(AxisAlignedBB.getBoundingBox(0.0D, h, 0.0D, 0.5D, h1, 0.5D));
		if(addDown || addRight)	boxes.add(AxisAlignedBB.getBoundingBox(0.5D, h, 0.0D, 1.0D, h1, 0.5D));
	}
}