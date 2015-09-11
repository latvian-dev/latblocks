package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.LatCoreMC;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.paint.IPainterItem;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

public class BlockPDoor extends BlockPaintableSided
{
	public BlockPDoor(String s)
	{
		super(s);
		setBlockTextureName("paintable_door");
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePDoor(); }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPaintableDef.ORE_NAME, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 6), "PP", "PP", "PP",
				'P', LatBlocksItems.b_paintable);
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D));
	}
	
	@SideOnly(Side.CLIENT)
	public void getPlacementBoxes(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return 0; }
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		if(m == 0) boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D));
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		if(ep.getHeldItem() != null && (ep.getHeldItem().getItem() instanceof IPainterItem || ep.getHeldItem().getItem() == getItem()))
			return false;
		
		if(!w.isRemote)
		{
			TilePDoor t = getTile(TilePDoor.class, w, x, y, z);
			if(t != null) t.open();
		}
		
		return true;
	}
	
	public boolean isOpen(int meta)
	{ return meta > 1; }
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{
		if (!w.isRemote && w.isBlockIndirectlyGettingPowered(x, y, z))
		{
			TilePDoor t = getTile(TilePDoor.class, w, x, y, z);
			if(t != null) t.open();
		}
	}
	
	public static class TilePDoor extends TileSidedPaintable
	{
		public int timer = 0;
		
		public void readTileData(NBTTagCompound tag)
		{
			super.readTileData(tag);
			timer = tag.getShort("Timer");
		}
		
		public void writeTileData(NBTTagCompound tag)
		{
			super.writeTileData(tag);
			tag.setShort("Timer", (short)timer);
		}
		
		public void onUpdate()
		{
			if(timer > 0 && isServer())
			{
				timer--;
				
				if(timer == 0)
				{
					markDirty();
					setMeta(0);
					worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.fizz", LatCoreMC.rand.nextFloat() * 0.05F, LatCoreMC.rand.nextFloat() * 0.2F + 0.9F);
				}
				else if(timer == 37)
				{
					for(int i = 0; i < 6; i++)
					{
						TileEntity te = getTile(i);
						if(te != null && te instanceof TilePDoor)
						{
							TilePDoor t = (TilePDoor)te;
							if(t.timer == 0) t.open();
						}
					}
				}
			}
		}
		
		public void open()
		{
			if(timer == 0 && isServer())
			{
				timer = 40;
				setMeta(1);
				markDirty();
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.fizz", LatCoreMC.rand.nextFloat() * 0.2F, LatCoreMC.rand.nextFloat() * 0.2F + 0.9F);
				//worldObj.playAuxSFXAtEntity(null, 1003, xCoord, yCoord, zCoord, 0);
			}
		}
	}
}