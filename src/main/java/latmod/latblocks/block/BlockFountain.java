package latmod.latblocks.block;

import cpw.mods.fml.relauncher.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.api.*;
import latmod.latblocks.block.tank.BlockTank;
import latmod.latblocks.client.render.world.RenderFountain;
import latmod.latblocks.tile.TileFountain;
import latmod.lib.MathHelperLM;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockFountain extends BlockLB
{
	public BlockFountain(String s)
	{
		super(s, Material.rock);
		setHardness(1.5F);
		setBlockTextureName("paintable");
		getMod().addTile(TileFountain.class, s);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), " H ", "PGP", " P ", 'H', Blocks.hopper, 'G', BlockTank.TANK_BASIC, 'P', LatBlocksItems.b_paintable);
	}
	
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileFountain(); }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderFountain.instance.getRenderId(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World w, MovingObjectPosition mop, EffectRenderer er)
	{
		int x = mop.blockX;
		int y = mop.blockY;
		int z = mop.blockZ;
		
		TileFountain cb = (TileFountain) w.getTileEntity(x, y, z);
		
		Paint p = cb.paint[0];
		
		if(p == null || p.block == null) return false;
		
		IBlockAccess iba = new PaintBlockAccess(w, x, y, z, p);
		
		IIcon tex = p.block.getIcon(iba, x, y, z, mop.sideHit);
		if(tex == null) tex = blockIcon;
		
		addBlockHitEffects(w, er, x, y, z, mop.sideHit, tex);
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World w, int x, int y, int z, int meta, EffectRenderer er)
	{
		TileFountain cb = (TileFountain) w.getTileEntity(x, y, z);
		
		int side = 1;
		
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		
		if(mop != null && mop.blockX == x && mop.blockY == y && mop.blockZ == z) side = mop.sideHit;
		
		Paint p = cb.paint[0];
		
		if(p == null || p.block == null) return false;
		
		IBlockAccess iba = new PaintBlockAccess(w, x, y, z, p);
		
		IIcon tex = p.block.getIcon(iba, x, y, z, side);
		if(tex == null) tex = blockIcon;
		
		byte b0 = 4;
		
		for(int x1 = 0; x1 < b0; ++x1)
			for(int y1 = 0; y1 < b0; ++y1)
				for(int z1 = 0; z1 < b0; ++z1)
				{
					double d0 = x + (x1 + 0.5D) / b0;
					double d1 = y + (y1 + 0.5D) / b0;
					double d2 = z + (z1 + 0.5D) / b0;
					int i2 = MathHelperLM.rand.nextInt(6);
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
		double d0 = x + MathHelperLM.rand.nextDouble() * (getBlockBoundsMaxX() - getBlockBoundsMinX() - (f * 2.0F)) + f + getBlockBoundsMinX();
		double d1 = y + MathHelperLM.rand.nextDouble() * (getBlockBoundsMaxY() - getBlockBoundsMinY() - (f * 2.0F)) + f + getBlockBoundsMinY();
		double d2 = z + MathHelperLM.rand.nextDouble() * (getBlockBoundsMaxZ() - getBlockBoundsMinZ() - (f * 2.0F)) + f + getBlockBoundsMinZ();
		
		if(side == 0) d1 = y + getBlockBoundsMinY() - f;
		else if(side == 1) d1 = y + getBlockBoundsMaxY() + f;
		else if(side == 2) d2 = z + getBlockBoundsMinZ() - f;
		else if(side == 3) d2 = z + getBlockBoundsMaxZ() + f;
		else if(side == 4) d0 = x + getBlockBoundsMinX() - f;
		else if(side == 5) d0 = x + getBlockBoundsMaxX() + f;
		
		EntityDiggingFX digFX = new EntityDiggingFX(w, d0, d1, d2, 0.0D, 0.0D, 0.0D, this, side, 0);
		digFX.applyColourMultiplier(x, y, z).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F);
		digFX.setParticleIcon(tex);
		er.addEffect(digFX);
	}
}