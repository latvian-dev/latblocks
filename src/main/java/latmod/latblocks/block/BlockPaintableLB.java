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
		setBlockTextureName("paintable");
		isBlockContainer = true;
		registerTiles();
	}
	
	public abstract TileLM createNewTileEntity(World w, int m);
	
	public int damageDropped(int i)
	{ return 0; }
	
	public void registerTiles()
	{
		mod.addTile(createNewTileEntity(null, 0).getClass(), blockName);
	}
	
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
		addBoxes(boxes, onBlockPlaced(event.player.worldObj, event.player, event.target, -1));
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
	}
	
	public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 start, Vec3 end)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w.getBlockMetadata(x, y, z));
		return LatCoreMC.collisionRayTrace(w, x, y, z, start, end, boxes);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getDefaultWorldIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getDefaultItemIcon()
	{ return blockIcon; }
}