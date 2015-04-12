package latmod.latblocks.block;
import latmod.core.tile.*;
import latmod.latblocks.LatBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockCraftingPanel extends BlockLB
{
	public BlockCraftingPanel(String s)
	{
		super(s, Material.wood);
		setHardness(0.7F);
		isBlockContainer = true;
	}
	
	public void loadRecipes()
	{
		LatBlocksItems.i_hammer.addRecipe(new ItemStack(this), Blocks.crafting_table);
	}
	
	public TileLM createNewTileEntity(World w, int i)
	{ return new TileCraftingPanel(); }
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{ return super.onBlockActivated(w, x, y, z, ep, s, x1, y1, z1); }
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
	{ return null; }
	
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int s)
	{
		ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[s].getOpposite();
		Block b = w.getBlock(x + f.offsetX, y + f.offsetY, z + f.offsetZ);
		return b == Blocks.fence || b == LatBlocksItems.b_fence || b.isSideSolid(w, x, y, z, f.getOpposite());
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public void setBlockBoundsForItemRender()
	{
		float f = 1F / 8F;
		setBlockBounds(f, f, 0.5F - 1F / 16F, 1F - f, 1F - f, 0.5F + 1F / 16F);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		int m = iba.getBlockMetadata(x, y, z);
		
		float f = 1F / 8F;
		float h = 1F / 16F;
		
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[m];
		
		if(dir == ForgeDirection.UP) // y+
			setBlockBounds(f, 1F - h, f, 1F - f, 1F, 1F - f);
		else if(dir == ForgeDirection.DOWN) //y-
			setBlockBounds(f, 0F, f, 1F - f, h, 1F - f);
		
		else if(dir == ForgeDirection.EAST) //x
			setBlockBounds(1F - h, f, f, 1F, 1F - f, 1F - f);
		else if(dir == ForgeDirection.WEST) //x
			setBlockBounds(0F, f, f, h, 1F - f, 1F - f);
		
		else if(dir == ForgeDirection.NORTH) // z-
			setBlockBounds(f, f, 0F, 1F - f, 1F - f, h);
		else if(dir == ForgeDirection.SOUTH) // z+
			setBlockBounds(f, f, 1F - h, 1F - f, 1F - f, 1F);
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		if(!canPlaceBlockOnSide(w, mop.blockX, mop.blockY, mop.blockZ, mop.sideHit)) return -1;
		return ForgeDirection.VALID_DIRECTIONS[mop.sideHit].getOpposite().ordinal();
	}
	
	public int damageDropped(int i)
	{ return 0; }
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{
		ForgeDirection f = ForgeDirection.VALID_DIRECTIONS[w.getBlockMetadata(x, y, z)];
		
		if(w.getBlock(x + f.offsetX, y + f.offsetY, z + f.offsetZ) == Blocks.air)
		{
			dropBlockAsItem(w, x, y, z, new ItemStack(this));
			w.setBlockToAir(x, y, z);
		}
	}
	
	public static class TileCraftingPanel extends TileLM implements IGuiTile
	{
		public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
		{ if(isServer()) openGui(ep, 0); return true; }
		
		public Container getContainer(EntityPlayer ep, int ID)
		{
			return new ContainerWorkbench(ep.inventory, worldObj, xCoord, yCoord, zCoord)
			{
				public boolean canInteractWith(EntityPlayer ep)
				{ return true; }
			};
		}
		
		@SideOnly(Side.CLIENT)
		public GuiScreen getGui(EntityPlayer ep, int ID)
		{ return new GuiCrafting(ep.inventory, worldObj, xCoord, yCoord, zCoord); }
	}
}