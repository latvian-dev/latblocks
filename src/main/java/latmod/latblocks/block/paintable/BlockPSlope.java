package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.*;
import ftb.lib.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockLB;
import latmod.latblocks.tile.TileSinglePaintable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPSlope extends BlockLB
{
	public BlockPSlope(String s)
	{
		super(s, Material.rock);
		setHardness(1.5F);
		setBlockTextureName("paintable");
		getMod().addTile(TilePSlope.class, s);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 4), "B  ", "BC ", "BBB", 'C', LatBlocksItems.b_cover, 'B', LatBlocksItems.b_paintable);
	}
	
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TilePSlope(); }
	
	//@SideOnly(Side.CLIENT)
	//public int getRenderType()
	//{ return RenderPSlope.instance.getRenderId(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean isNormalCube(IBlockAccess iba, int x, int y, int z)
	{ return true; }
	
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{
		TilePSlope t = (TilePSlope) getTile(iba, x, y, z);
		return (t == null) ? true : t.isSolid(side.ordinal());
	}
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
	{ return true; }
	
	public static class TilePSlope extends TileSinglePaintable
	{
		public byte yaw = 0;
		public byte pitch = -1;
		
		public void readTileData(NBTTagCompound tag)
		{
			super.readTileData(tag);
			yaw = tag.getByte("Yaw");
			pitch = tag.getByte("Pitch");
		}
		
		public void writeTileData(NBTTagCompound tag)
		{
			super.writeTileData(tag);
			tag.setByte("Yaw", yaw);
			tag.setByte("Pitch", pitch);
		}
		
		public void onPlacedBy(EntityPlayer ep, ItemStack is)
		{
			super.onPlacedBy(ep, is);
			
			int side = MathHelperMC.rayTrace(ep).sideHit;
			
			if(side == 0) pitch = 1;
			else if(side == 1) pitch = 0;
			else pitch = -1;
			
			yaw = (byte) MathHelperMC.get2DRotation(ep);
			
			if(isServer()) FTBLib.printChat(ep, "Yaw: " + yaw + ", Pitch: " + pitch + ", Side: " + side);
			
			markDirty();
		}
		
		public boolean isSolid(int side)
		{ return !(yaw == side || pitch == side); }
	}
}