package latmod.latblocks.block;
import latmod.core.util.FastList;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public abstract class BlockPaintableSingle extends BlockPaintableLB
{
	public final float height;
	
	public BlockPaintableSingle(String s, Material m, float h)
	{
		super(s, m);
		height = h;
		setHardness(0.3F);
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
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
		}
		
		if(event.target.sideHit == Placement.D_NORTH || event.target.sideHit == Placement.D_SOUTH)
		{
			int h = event.target.sideHit == Placement.D_NORTH ? 1 : 0;
			boxes.add(AxisAlignedBB.getBoundingBox(0D, b0, h, 1D, b1, h));
			boxes.add(AxisAlignedBB.getBoundingBox(b0, 0D, h, b1, 1D, h));
		}
		
		if(event.target.sideHit == Placement.D_WEST || event.target.sideHit == Placement.D_EAST)
		{
			int h = event.target.sideHit == Placement.D_WEST ? 1 : 0;
			boxes.add(AxisAlignedBB.getBoundingBox(h, b0, 0D, h, b1, 1D));
			boxes.add(AxisAlignedBB.getBoundingBox(h, 0D, b0, h, 1D, b1));
		}
	}
	
	public void setBlockBoundsForItemRender()
	{ setBlockBounds(0F, 0F, 0F, 1F, height, 1F); }
		
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		double hitX = mop.hitVec.xCoord - mop.blockX;
		double hitY = mop.hitVec.yCoord - mop.blockY;
		double hitZ = mop.hitVec.zCoord - mop.blockZ;
		
		if(mop.sideHit == Placement.D_UP || mop.sideHit == Placement.D_DOWN)
		{
			Placement p = Placement.get(hitX, hitZ);
			
			if(p == Placement.CENTER) return mop.sideHit == Placement.D_UP ? Placement.D_DOWN : Placement.D_UP;
			if(p == Placement.UP) return Placement.D_NORTH;
			if(p == Placement.RIGHT) return Placement.D_EAST;
			if(p == Placement.DOWN) return Placement.D_SOUTH;
			if(p == Placement.LEFT) return Placement.D_WEST;
		}
		else if(mop.sideHit == Placement.D_NORTH || mop.sideHit == Placement.D_SOUTH)
		{
			Placement p = Placement.get(hitX, hitY);
			
			if(p == Placement.CENTER) return mop.sideHit == Placement.D_NORTH ? Placement.D_SOUTH : Placement.D_NORTH;
			if(p == Placement.UP) return Placement.D_DOWN;
			if(p == Placement.RIGHT) return Placement.D_EAST;
			if(p == Placement.DOWN) return Placement.D_UP;
			if(p == Placement.LEFT) return Placement.D_WEST;
		}
		else if(mop.sideHit == Placement.D_WEST || mop.sideHit == Placement.D_EAST)
		{
			Placement p = Placement.get(hitZ, hitY);
			
			if(p == Placement.CENTER) return mop.sideHit == Placement.D_WEST ? Placement.D_EAST : Placement.D_WEST;
			if(p == Placement.UP) return Placement.D_DOWN;
			if(p == Placement.RIGHT) return Placement.D_SOUTH;
			if(p == Placement.DOWN) return Placement.D_UP;
			if(p == Placement.LEFT) return Placement.D_NORTH;
		}
		
		return -1;
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, int m)
	{
		if(m == Placement.D_DOWN) boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, height, 1D));
		else if(m == Placement.D_UP) boxes.add(AxisAlignedBB.getBoundingBox(0D, 1D - height, 0D, 1D, 1D, 1D));
		else if(m == Placement.D_NORTH) boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, height));
		else if(m == Placement.D_SOUTH) boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 1D - height, 1D, 1D, 1D));
		else if(m == Placement.D_WEST) boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, height, 1D, 1D));
		else if(m == Placement.D_EAST) boxes.add(AxisAlignedBB.getBoundingBox(1D - height, 0D, 0D, 1D, 1D, 1D));
	}
}