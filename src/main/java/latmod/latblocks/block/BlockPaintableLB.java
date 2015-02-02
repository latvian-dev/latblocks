package latmod.latblocks.block;

import java.util.*;

import latmod.core.*;
import latmod.core.tile.IPaintable.Paint;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.client.render.RenderPaintable;
import latmod.latblocks.item.ItemGlasses;
import latmod.latblocks.tile.TilePaintableLB;
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
	public BlockPaintableLB(String s)
	{
		super(s, Material.rock);
		setHardness(1.5F);
		setBlockTextureName("paintable");
		isBlockContainer = true;
		registerTiles();
	}
	
	public abstract TilePaintableLB createNewTileEntity(World w, int m);
	
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
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, iba, x, y, z, -1);
		
		minX = minY = minZ = 1D;
		maxX = maxY = maxZ = 0D;
		
		for(int i = 0; i < boxes.size(); i++)
		{
			AxisAlignedBB bb = boxes.get(i);
			
			if(bb.minX < minX) minX = Math.max(bb.minX, 0D);
			if(bb.minY < minY) minY = Math.max(bb.minY, 0D);
			if(bb.minZ < minZ) minZ = Math.max(bb.minZ, 0D);
			if(bb.maxX > maxX) maxX = Math.min(bb.maxX, 1D);
			if(bb.maxY > maxY) maxY = Math.min(bb.maxY, 1D);
			if(bb.maxZ > maxZ) maxZ = Math.min(bb.maxZ, 1D);
		}
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
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
		return MathHelperLM.collisionRayTrace(w, x, y, z, start, end, boxes);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getDefaultWorldIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getDefaultItemIcon()
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public final void randomDisplayTick(World w, int x, int y, int z, Random r)
	{
		TilePaintableLB t = (TilePaintableLB)w.getTileEntity(x, y, z);
		
		boolean hasInv = false;
		
		if(t != null)
		{
			for(int i = 0; i < 6; i++)
			{
				Paint p = t.getPaint(i);
				if(!hasInv && p != null && p.block == LatBlocksItems.b_glass && p.meta == 0)
					hasInv = true;
			}
			
			if(hasInv) ItemGlasses.spawnInvParticles(w, x + 0.5D, y + 0.5D, z + 0.5D, 3);
		}
	}
}