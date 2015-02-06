package latmod.latblocks.block.paintable;

import java.util.List;

import latmod.core.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public class BlockPPressurePlate extends BlockPaintableSingle // BlockPressurePlate
{
	public BlockPPressurePlate(String s)
	{
		super(s, 1F / 8F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePPressurePlate(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 1), "PP",
				'P', LatBlocksItems.b_paintable);
	}
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		boxes.add(getBox(0, 0, 0));
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return 0; }
	
	public int damageDropped(int m)
	{ return 0; }
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
		boxes.add(getBox(0, 0, 0));
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		boxes.add(getBox(0, 0, 0));
	}
	
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		double p = 1D / 16D;
		double h = ((TilePPressurePlate)iba.getTileEntity(x, y, z)).isPressed ? p / 2D : p;
		boxes.add(AxisAlignedBB.getBoundingBox(p, 0D, p, 1D - p, h, 1D - p));
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{ return false; }
	
	public boolean canPlaceBlockAt(World w, int x, int y, int z)
	{ return World.doesBlockHaveSolidTopSurface(w, x, y - 1, z) || BlockFence.func_149825_a(w.getBlock(x, y - 1, z)); }
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{
		if (!World.doesBlockHaveSolidTopSurface(w, x, y - 1, z) && !BlockFence.func_149825_a(w.getBlock(x, y - 1, z)))
		{
			dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
			w.setBlockToAir(x, y, z);
		}
	}
	
	public int isProvidingWeakPower(IBlockAccess iba, int x, int y, int z, int s)
	{ return ((TilePPressurePlate)iba.getTileEntity(x, y, z)).isPressed ? 15 : 0; }
	
	public int isProvidingStrongPower(IBlockAccess iba, int x, int y, int z, int s)
	{ return ((TilePPressurePlate)iba.getTileEntity(x, y, z)).isPressed ? 15 : 0; }
	
	public boolean canProvidePower()
	{ return true; }
	
	private static AxisAlignedBB getBox(int x, int y, int z)
	{ double p = 1D / 16D; return AxisAlignedBB.getBoundingBox(x + p, y, z + p, x + 1D - p, y + p, z + 1D - p); }
	
	public static class TilePPressurePlate extends TileSinglePaintable
	{
		public byte plateType = 0;
		public short maxTick = 40;
		public boolean isPressed = false;
		private short cooldown = 0;
		
		public void readTileData(NBTTagCompound tag)
		{
			super.readTileData(tag);
			plateType = tag.getByte("Type");
			maxTick = tag.getShort("MaxTick");
			isPressed = tag.getBoolean("Down");
			cooldown = tag.getShort("Tick");
		}
		
		public void writeTileData(NBTTagCompound tag)
		{
			super.writeTileData(tag);
			tag.setByte("Type", plateType);
			tag.setShort("MaxTick", maxTick);
			tag.setBoolean("Down", isPressed);
			tag.setShort("Tick", cooldown);
		}
		
		public void onUpdate()
		{
			if(!isServer()) return;
			
			boolean pPressed = isPressed;
			
			isPressed = isPressed();
			
			if(isPressed) cooldown = maxTick;
			else if(cooldown > 0) cooldown--;
			
			isPressed = cooldown > 0;
			
			if(pPressed != isPressed)
			{
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.1D, zCoord + 0.5D, "random.click", 0.3F, isPressed ? 0.6F : 0.5F);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
				markDirty();
			}
		}
		
		@SuppressWarnings("all")
		private boolean isPressed()
		{
			Class<? extends Entity> c = null;
			
				 if(plateType == 0) c = Entity.class;
			else if(plateType == 1) c = EntityLivingBase.class;
			else if(plateType == 2) c = EntityPlayer.class;
			
			List list = worldObj.getEntitiesWithinAABB(c, getBox(xCoord, yCoord, zCoord));
			
			if (list != null && !list.isEmpty())
			{
				for(int i = 0; i < list.size(); i++)
					if(!((Entity)list.get(i)).doesEntityNotTriggerPressurePlate())
						return true;
			}
			
			return false;
		}
	}
}