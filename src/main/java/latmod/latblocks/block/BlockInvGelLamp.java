package latmod.latblocks.block;
import java.util.Random;

import latmod.core.client.LatCoreMCClient;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.item.ItemGlasses;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockInvGelLamp extends BlockLB
{
	public BlockInvGelLamp(String s)
	{
		super(s, Material.wood);
		isBlockContainer = false;
		setLightLevel(1F);
		setHardness(0.1F);
		setBlockTextureName("lamp");
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World w, int x, int y, int z, Random r)
	{
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[w.getBlockMetadata(x, y, z)];
		ItemGlasses.spawnInvParticles(w, x + 0.5D + dir.offsetX * 0.3D, y + 0.5D + dir.offsetY * 0.3D, z + 0.5D + dir.offsetZ * 0.3D, 10);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "L", "G",
			'L', LatBlocksItems.b_gel_lamp,
			'G', LatBlocksItems.b_glass);
		
		LatBlocksItems.i_hammer.addRecipe(new ItemStack(LatBlocksItems.b_gel_lamp), this);
	}
	
	public TileLM createNewTileEntity(World w, int i)
	{ return null; }
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z)
	{ return null; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int s)
	{ return LatBlocksItems.b_gel_lamp.canPlaceBlockOnSide(w, x, y, z, s); }
	
	public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
	{
		float f = 1F / 16F * 5F;
		float h = 1F / 16F * 3F;
		
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[iba.getBlockMetadata(x, y, z)];
		
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
	
	public void setBlockBoundsForItemRender()
	{
		float f = 1F / 16F * 1F;
		float h = 1F / 16F * 8F;
		
		setBlockBounds(f, 0F, f, 1F - f, h, 1F - f);
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return LatBlocksItems.b_gel_lamp.onBlockPlaced(w, ep, mop, m); }
	
	public int damageDropped(int i)
	{ return 0; }
	
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{ LatBlocksItems.b_gel_lamp.onNeighborBlockChange(w, x, y, z, b); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return LatCoreMCClient.blockNullIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return blockIcon; }
}