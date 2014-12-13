package latmod.latblocks.block;

import java.util.List;

import latmod.core.*;
import latmod.core.MathHelper;
import latmod.core.tile.TileLM;
import latmod.latblocks.client.render.RenderPaintable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public abstract class BlockPaintableLB extends BlockLB
{
	public BlockPaintableLB(String s, Material m)
	{
		super(s, m);
		setHardness(1.5F);
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
	
	public final void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, iba, x, y, z, -1);
		
		minX = minY = minZ = 1D;
		maxX = maxY = maxZ = 0D;
		
		for(int i = 0; i < boxes.size(); i++)
		{
			AxisAlignedBB bb = boxes.get(i);
			
			if(bb.minX < minX) minX = bb.minX;
			if(bb.minY < minY) minY = bb.minY;
			if(bb.minZ < minZ) minZ = bb.minZ;
			if(bb.maxX > maxX) maxX = bb.maxX;
			if(bb.maxY > maxY) maxY = bb.maxY;
			if(bb.maxZ > maxZ) maxZ = bb.maxZ;
		}
	}
	
	public final AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
	{ return super.getCollisionBoundingBoxFromPool(w, x, y, z); }
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w, x, y, z, -1);
		
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
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{ addBoxes(boxes, iba, x, y, z, m); }
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
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
		int m = onBlockPlaced(event.player.worldObj, event.player, event.target, -1);
		if(m == -1) return;
		ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[event.target.sideHit];
		addRenderBoxes(boxes, event.player.worldObj, event.target.blockX + fd.offsetX, event.target.blockY + fd.offsetY, event.target.blockZ + fd.offsetZ, m);
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w, x, y, z, -1);
		
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		if(mop.subHit >= 0 && mop.subHit < boxes.size()) return boxes.get(mop.subHit).getOffsetBoundingBox(x, y, z);
		return AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D);
	}
	
	public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 start, Vec3 end)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w, x, y, z, -1);
		return MathHelper.collisionRayTrace(w, x, y, z, start, end, boxes);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getDefaultWorldIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getDefaultItemIcon()
	{ return blockIcon; }
}