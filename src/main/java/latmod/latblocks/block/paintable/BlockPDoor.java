package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.api.IPainterItem;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.*;
import latmod.lib.MathHelperLM;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import java.util.List;

public class BlockPDoor extends BlockPaintableSided
{
	public BlockPDoor(String s)
	{
		super(s);
		setBlockTextureName("paintable_door");
	}
	
	@Override
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePDoor(); }
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		ODItems.add(BlockPaintableDef.ORE_NAME, new ItemStack(this));
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this, 6), "PP", "PP", "PP", 'P', LatBlocksItems.b_paintable);
	}
	
	@Override
	public boolean isOpaqueCube()
	{ return false; }
	
	@Override
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(List<AxisAlignedBB> boxes)
	{
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getPlacementBoxes(List<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@Override
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return 0; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawHighlight(List<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@Override
	public void addBoxes(List<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		if(m == 0) boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D));
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		if(ep.getHeldItem() != null && (ep.getHeldItem().getItem() instanceof IPainterItem || ep.getHeldItem().getItem() == getItem()))
			return false;
		
		if(!w.isRemote)
		{
			TilePDoor t = (TilePDoor) getTile(w, x, y, z);
			if(t != null) t.open();
		}
		
		return true;
	}
	
	public boolean isOpen(int meta)
	{ return meta > 1; }
	
	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{
		if(!w.isRemote && w.isBlockIndirectlyGettingPowered(x, y, z))
		{
			TilePDoor t = (TilePDoor) getTile(w, x, y, z);
			if(t != null) t.open();
		}
	}
	
	public static class TilePDoor extends TileSidedPaintable
	{
		public int timer = 0;
		
		@Override
		public void readTileData(NBTTagCompound tag)
		{
			super.readTileData(tag);
			timer = tag.getShort("Timer");
		}
		
		@Override
		public void writeTileData(NBTTagCompound tag)
		{
			super.writeTileData(tag);
			tag.setShort("Timer", (short) timer);
		}
		
		@Override
		public void onUpdate()
		{
			if(timer > 0 && isServer())
			{
				timer--;
				
				if(timer == 0)
				{
					markDirty();
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
					worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.fizz", MathHelperLM.rand.nextFloat() * 0.05F, MathHelperLM.rand.nextFloat() * 0.2F + 0.9F);
				}
				else if(timer == 37)
				{
					for(int i = 0; i < 6; i++)
					{
						TileEntity te = worldObj.getTileEntity(xCoord + Facing.offsetsXForSide[i], yCoord + Facing.offsetsYForSide[i], zCoord + Facing.offsetsZForSide[i]);
						if(te != null && te instanceof TilePDoor)
						{
							TilePDoor t = (TilePDoor) te;
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
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
				markDirty();
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.fizz", MathHelperLM.rand.nextFloat() * 0.2F, MathHelperLM.rand.nextFloat() * 0.2F + 0.9F);
				//worldObj.playAuxSFXAtEntity(null, 1003, xCoord, yCoord, zCoord, 0);
			}
		}
	}
}