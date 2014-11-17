package latmod.latblocks.block;

import java.util.List;

import latmod.core.LatCoreMC;
import latmod.core.tile.TileLM;
import latmod.core.util.FastList;
import latmod.latblocks.client.render.RenderPaintable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public abstract class BlockPaintableLB extends BlockLB
{
	public BlockPaintableLB(String s, Material m)
	{
		super(s, m);
		isBlockContainer = true;
		setBlockTextureName("paintable");
		registerTiles();
	}
	
	public void registerTiles()
	{
		mod.addTile(createNewTileEntity(null, 0).getClass(), blockName);
	}
	
	public abstract TileLM createNewTileEntity(World w, int m);
	
	public int damageDropped(int i)
	{ return 0; }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderPaintable.instance.getRenderId(); }
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return m; }
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, iba.getBlockMetadata(x, y, z));
		
		if(boxes.size() >= 1) 
		{
			AxisAlignedBB bb = boxes.get(0);
			setBlockBounds((float)bb.minX, (float)bb.minY, (float)bb.minZ, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ);
		}
	}
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w.getBlockMetadata(x, y, z));
		
		for(int i = 0; i < boxes.size(); i++)
		{
			AxisAlignedBB bb1 = boxes.get(i).getOffsetBoundingBox(x, y, z);
			if(bb.intersectsWith(bb1)) l.add(bb1);
		}
	}
	
	public boolean canHarvestBlock(EntityPlayer ep, int meta)
	{ return true; }
	
	public final int getPlacedMeta(EntityPlayer ep, MovingObjectPosition mop)
	{
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, int m)
	{ addBoxes(boxes, m); }
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, int m)
	{
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D));
	}
	
	public void setBlockBoundsForItemRender()
	{ setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F); }
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		setBlockBoundsForItemRender();
		boxes.add(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
	}
	
	@SideOnly(Side.CLIENT)
	public void getPlacementBoxes(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
		addBoxes(boxes, event.player.worldObj.getBlockMetadata(event.target.blockX, event.target.blockY, event.target.blockZ));
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w.getBlockMetadata(x, y, z));
		
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		if(mop.subHit >= 0 && mop.subHit < boxes.size()) return boxes.get(mop.subHit).getOffsetBoundingBox(x, y, z);
		return AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D);
		
		//return super.getSelectedBoundingBoxFromPool(w, x, y, z);
		
		/*
		TileEntity te = w.getTileEntity(x, y, z);
		
		if(te != null && te instanceof TileCBCable)
		{
			if(((TileCBCable)te).hasCover)
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
		
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1D, y + 1D, z + 1D);*/
	}
	
	public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 start, Vec3 end)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w.getBlockMetadata(x, y, z));
		return LatCoreMC.collisionRayTrace(w, x, y, z, start, end, boxes);
	}
}