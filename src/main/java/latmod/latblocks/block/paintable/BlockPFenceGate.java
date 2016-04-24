package latmod.latblocks.block.paintable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.config.LatBlocksConfigGeneral;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TilePaintableLB;
import latmod.latblocks.tile.TileSinglePaintable;
import latmod.lib.MathHelperLM;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import java.util.List;

public class BlockPFenceGate extends BlockPaintableSingle
{
	public BlockPFenceGate(String s)
	{
		super(s, 1F);
	}
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new TilePFenceGate(); }
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(new ItemStack(this), "PSP", "PSP", 'P', LatBlocksItems.b_paintable, 'S', ItemMaterialsLB.ROD);
	}
	
	@Override
	public void addCollisionBoxes(World w, int x, int y, int z, int m, List<AxisAlignedBB> boxes, Entity e)
	{
		if(m == -1) m = w.getBlockMetadata(x, y, z);
		
		if(m > 1) return;
		
		double p = 1D / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		double x0 = pn;
		double x1 = pp;
		double z0 = pn;
		double z1 = pp;
		
		double d = 0.01D;
		
		if(m % 2 == 0)
		{
			x0 = -d;
			x1 = 1D + d;
		}
		else
		{
			z0 = -d;
			z1 = 1D + d;
		}
		
		double h = 1.5D;
		if(LatBlocksConfigGeneral.fences_ignore_players.getAsBoolean() && e instanceof EntityPlayer) h = 1D;
		boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, h, z1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(List<AxisAlignedBB> boxes)
	{
		double p = 1D / 16D * 3D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, pn, p, 1D, pp));
		boxes.add(AxisAlignedBB.getBoundingBox(1D - p, 0D, pn, 1D, 1D, pp));
		
		double hn = 1D / 8D * 1D;
		double hp = 1D / 8D * 7D;
		
		double dd = 1D / 8D;
		
		double x0 = p;
		double z0 = 0.5D - dd / 2D;
		double x1 = 1D - p;
		double z1 = 0.5D + dd / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(x0, hn, z0, x1, hp, z1));
	}
	
	@Override
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return (MathHelperLM.floor(ep.rotationYaw * 4D / 360D + 0.5D) & 3) % 2; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawHighlight(List<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	@Override
	public void addBoxes(List<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		double p = 1D / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		double x0 = pn;
		double x1 = pp;
		double z0 = pn;
		double z1 = pp;
		
		double d = 0.01D;
		
		if(m % 2 == 0)
		{
			x0 = -d;
			x1 = 1D + d;
		}
		else
		{
			z0 = -d;
			z1 = 1D + d;
		}
		
		boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, 1D, z1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addRenderBoxes(List<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		double p = 1D / 16D * 3D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		if(m % 2 == 0)
		{
			boxes.add(AxisAlignedBB.getBoundingBox(0D, 0D, pn, p, 1D, pp));
			boxes.add(AxisAlignedBB.getBoundingBox(1D - p, 0D, pn, 1D, 1D, pp));
		}
		else
		{
			boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, 0D, pp, 1D, p));
			boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, 1D - p, pp, 1D, 1D));
		}
		
		if(m <= 1)
		{
			double hp = 1D / 8D * 7D;
			if(iba.getBlock(x, y + 1, z) != Blocks.air) hp = 1D;
			
			double dd = 1D / 8D;
			
			double x0 = p;
			double z0 = 0.5D - dd / 2D;
			double x1 = 1D - p;
			double z1 = 0.5D + dd / 2D;
			
			if(m == 1)
			{
				z0 = p;
				x0 = 0.5D - dd / 2D;
				z1 = 1D - p;
				x1 = 0.5D + dd / 2D;
			}
			
			boxes.add(AxisAlignedBB.getBoundingBox(x0, 0D, z0, x1, hp, z1));
		}
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		setOpen(w, x, y, z, !isOpen(w.getBlockMetadata(x, y, z)));
		return true;
	}
	
	public boolean isOpen(int meta)
	{ return meta > 1; }
	
	public void setOpen(World w, int x, int y, int z, boolean open)
	{
		int m0 = w.getBlockMetadata(x, y, z);
		
		if(open == isOpen(m0)) return;
		
		w.setBlockMetadataWithNotify(x, y, z, (m0 + 2) % 4, 3);
		
		for(int i = -2; i <= 2; i++)
		{
			if(i != 0 && w.getBlock(x, y + i, z) == this)
			{
				int m = w.getBlockMetadata(x, y + i, z);
				if(m == m0) w.setBlockMetadataWithNotify(x, y + i, z, (m + 2) % 4, 3);
			}
		}
		
		if(!w.isRemote) w.playAuxSFXAtEntity(null, 1003, x, y, z, 0);
	}
	
	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b)
	{
		if(!w.isRemote)
		{
			boolean flag = w.isBlockIndirectlyGettingPowered(x, y, z);
			if(flag || b.canProvidePower()) setOpen(w, x, y, z, flag);
		}
	}
	
	public static class TilePFenceGate extends TileSinglePaintable
	{ }
}