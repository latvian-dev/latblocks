package latmod.latblocks.block;

import java.util.Random;

import latmod.ftbu.core.LatCoreMC;
import latmod.ftbu.core.tile.*;
import latmod.ftbu.core.tile.IPaintable.Paint;
import latmod.ftbu.core.util.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.client.render.world.RenderPaintable;
import latmod.latblocks.item.ItemGlasses;
import latmod.latblocks.tile.TilePaintableLB;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
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
		hasSpecialPlacement = true;
		registerTiles();
	}
	
	public abstract TilePaintableLB createNewTileEntity(World w, int m);
	
	public void addCollisionBoxes(World w, int x, int y, int z, int m, FastList<AxisAlignedBB> boxes, Entity e)
	{ addBoxes(boxes, w, x, y, z, m); }
	
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
		if(ItemGlasses.hasClientPlayer())
		{
			TilePaintableLB t = (TilePaintableLB)w.getTileEntity(x, y, z);
			
			if(t != null)
			{
				for(int i = 0; i < 6; i++)
				{
					Paint p = t.getPaint(i);
					if(p != null && p.block == LatBlocksItems.b_glass && p.meta == 0)
					{
						ItemGlasses.spawnInvParticles(w, x + 0.5D, y + 0.5D, z + 0.5D, 3);
						return;
					}
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World w, MovingObjectPosition mop, EffectRenderer er)
	{
		int x = mop.blockX;
		int y = mop.blockY;
		int z = mop.blockZ;
		
		TilePaintableLB cb = (TilePaintableLB)w.getTileEntity(x, y, z);
		
		Paint p = cb.getPaint(mop.sideHit);
		
		if(p == null || p.block == null) return false;
		
		IBlockAccess iba = new IPaintable.BlockAccess(w, x, y, z, p);
		
		IIcon tex = p.block.getIcon(iba, x, y, z, mop.sideHit);
		if (tex == null) tex = blockIcon;
		
		addBlockHitEffects(w, er, x, y, z, mop.sideHit, tex);
		
		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World w, int x, int y, int z, int meta, EffectRenderer er)
	{
		TilePaintableLB cb = (TilePaintableLB)w.getTileEntity(x, y, z);
		
		int side = 1;
		
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		
		if(mop != null && mop.blockX == x && mop.blockY == y && mop.blockZ == z)
			side = mop.sideHit;
		
		Paint p = cb.getPaint(side);
		
		if(p == null || p.block == null) return false;
		
		IBlockAccess iba = new IPaintable.BlockAccess(w, x, y, z, p);
		
		IIcon tex = p.block.getIcon(iba, x, y, z, side);
		if (tex == null) tex = blockIcon;
		
		byte b0 = 4;
		
		for (int x1 = 0; x1 < b0; ++x1)
		for (int y1 = 0; y1 < b0; ++y1)
		for (int z1 = 0; z1 < b0; ++z1)
		{
			double d0 = x + (x1 + 0.5D) / b0;
			double d1 = y + (y1 + 0.5D) / b0;
			double d2 = z + (z1 + 0.5D) / b0;
			int i2 = LatCoreMC.rand.nextInt(6);
			EntityDiggingFX fx = new EntityDiggingFX(w, d0, d1, d2, d0 - x - 0.5D, d1 - y - 0.5D, d2 - z - 0.5D, this, i2, 0).applyColourMultiplier(x, y, z);
			fx.setParticleIcon(tex);
			er.addEffect(fx);
		}
		
		return true;
	}

	@SideOnly(Side.CLIENT)
	private void addBlockHitEffects(World w, EffectRenderer er, int x, int y, int z, int side, IIcon tex)
	{
		float f = 0.1F;
		double d0 = x + LatCoreMC.rand.nextDouble() * (getBlockBoundsMaxX() - getBlockBoundsMinX() - (f * 2.0F)) + f + getBlockBoundsMinX();
		double d1 = y + LatCoreMC.rand.nextDouble() * (getBlockBoundsMaxY() - getBlockBoundsMinY() - (f * 2.0F)) + f + getBlockBoundsMinY();
		double d2 = z + LatCoreMC.rand.nextDouble() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ() - (f * 2.0F)) + f + getBlockBoundsMinZ();

		if (side == 0) d1 = y + getBlockBoundsMinY() - f;
		else if (side == 1) d1 = y + getBlockBoundsMaxY() + f;
		else if (side == 2) d2 = z + getBlockBoundsMinZ() - f;
		else if (side == 3) d2 = z + getBlockBoundsMaxZ() + f;
		else if (side == 4) d0 = x + getBlockBoundsMinX() - f;
		else if (side == 5) d0 = x + getBlockBoundsMaxX() + f;
		
		EntityDiggingFX digFX = new EntityDiggingFX(w, d0, d1, d2, 0.0D, 0.0D, 0.0D, this, side, 0);
		digFX.applyColourMultiplier(x, y, z).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F);
		digFX.setParticleIcon(tex);
		er.addEffect(digFX);
	}
}