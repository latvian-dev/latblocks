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
		mod.recipes.addRecipe(new ItemStack(this, 1, 0), "PP",
				'P', LatBlocksItems.b_paintable);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 1), "P", "B",
				'P', new ItemStack(this, 1, 0),
				'B', ODItems.STONE);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 2), "P", "B",
				'P', new ItemStack(this, 1, 0),
				'B', ODItems.OBSIDIAN);
	}
	
	public void onPostLoaded()
	{
		addAllDamages(3);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		if(is.stackSize == 1)
		{
			int i = is.getItemDamage();
			
			if(i == 0) l.add("All Entities");
			else if(i == 1) l.add("Living Entities");
			else if(i == 2) l.add("Players");
		}
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
	{ return m; }
	
	public int damageDropped(int m)
	{ return m; }
	
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
	
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e)
	{ if(!w.isRemote) ((TilePPressurePlate)w.getTileEntity(x, y, z)).setPlate(true); }
	
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
		public boolean isPressed = false;
		private int cooldown = 0;
		
		public void readTileData(NBTTagCompound tag)
		{
			super.readTileData(tag);
			isPressed = tag.getBoolean("Down");
			cooldown = tag.getByte("Tick");
		}
		
		public void writeTileData(NBTTagCompound tag)
		{
			super.writeTileData(tag);
			tag.setBoolean("Down", isPressed);
			tag.setByte("Tick", (byte)cooldown);
		}
		
		public void onUpdate()
		{
			if(!isServer()) return;
			
			if(cooldown > 0) cooldown--;
			else
			{
				setPlate(isPressed());
				cooldown = 40;
			}
		}
		
		public void setPlate(boolean p)
		{
			if(!isServer()) return;
			
			boolean pPressed = isPressed;
			
			isPressed = p;
			
			if(pPressed != isPressed)
			{
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.1D, zCoord + 0.5D, "random.click", 0.3F, cooldown > 0 ? 0.6F : 0.5F);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
				//worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
				markDirty();
			}
		}
		
		@SuppressWarnings("all")
		private boolean isPressed()
		{
			int m = getBlockMetadata();
			
			Class<? extends Entity> entityClass = null;
			
			if(m == 0) entityClass = Entity.class;
			else if(m == 1) entityClass = EntityLivingBase.class;
			else if(m == 2) entityClass = EntityPlayer.class;
			
			List list = worldObj.getEntitiesWithinAABB(entityClass, getBox(xCoord, yCoord, zCoord));
			
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