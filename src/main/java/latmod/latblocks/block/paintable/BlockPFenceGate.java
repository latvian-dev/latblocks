package latmod.latblocks.block.paintable;

import java.util.List;

import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.core.util.*;
import latmod.core.util.MathHelper;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSingle;
import latmod.latblocks.tile.paintable.TilePFence;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import cpw.mods.fml.relauncher.*;

public class BlockPFenceGate extends BlockPaintableSingle
{
	public BlockPFenceGate(String s)
	{
		super(s, Material.wood, 1F);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePFence(); }
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "PSP", "PSP",
				'P', LatBlocksItems.b_paintable,
				'S', ODItems.STICK);
	}
	
	@SuppressWarnings("all")
	public void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		if(w.getBlockMetadata(x, y, z) > 1) return;
		
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addBoxes(boxes, w, x, y, z, -1);
		
		for(int i = 0; i < boxes.size(); i++)
		{
			AxisAlignedBB bb1 = boxes.get(i).getOffsetBoundingBox(x, y, z);
			bb1.maxY += 0.5D;
			if(bb.intersectsWith(bb1)) l.add(bb1);
		}
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int s, float x1, float y1, float z1)
	{
		w.setBlockMetadataWithNotify(x, y, z, (w.getBlockMetadata(x, y, z) + 2) % 4, 3);
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void addItemRenderBoxes(FastList<AxisAlignedBB> boxes)
	{
		double p = 1F / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, pn, pp, 1D, pp));
		//boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, pn + 0.5D, pp, 1D, pp));
	}
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{
		return MathHelper.floor((double)(ep.rotationYaw * 2D / 360D) + 0.5D) & 1;
	}
	
	@SideOnly(Side.CLIENT)
	public void drawHighlight(FastList<AxisAlignedBB> boxes, DrawBlockHighlightEvent event)
	{
	}
	
	public void addBoxes(FastList<AxisAlignedBB> boxes, IBlockAccess iba, int x, int y, int z, int m)
	{
		if(m == -1) m = iba.getBlockMetadata(x, y, z);
		
		double p = 1F / 4D;
		double pn = 0.5D - p / 2D;
		double pp = 0.5D + p / 2D;
		
		/*
		double h1n = 1D / 8D * 2D;
		double h1p = 1D / 8D * 3D;
		double h2n = 1D / 8D * 5D;
		double h2p = 1D / 8D * 6D;
		*/
		
		boxes.add(AxisAlignedBB.getBoundingBox(pn, 0D, pn, pp, 1D, pp));
	}
}