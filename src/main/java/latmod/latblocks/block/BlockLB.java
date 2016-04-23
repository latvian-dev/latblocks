package latmod.latblocks.block;

import cpw.mods.fml.relauncher.*;
import ftb.lib.LMMod;
import ftb.lib.api.block.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.api.*;
import latmod.latblocks.client.LatBlocksClient;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

import java.util.*;

public abstract class BlockLB extends BlockLM implements ICustomPaintBlockIcon
{
	public boolean hasSpecialPlacement = false;
	
	public BlockLB(String s, Material m)
	{
		super(s, m);
		setCreativeTab(LatBlocks.tab);
	}
	
	@Override
	public LMMod getMod()
	{ return LatBlocks.mod; }
	
	@Override
	public Class<? extends ItemBlockLM> getItemBlock()
	{ return ItemBlockLB.class; }
	
	@SuppressWarnings("all")
	public final void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		ArrayList<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
		addCollisionBoxes(w, x, y, z, -1, boxes, e);
		
		for(int i = 0; i < boxes.size(); i++)
		{
			AxisAlignedBB bb1 = boxes.get(i).getOffsetBoundingBox(x, y, z);
			if(bb.intersectsWith(bb1)) l.add(bb1);
		}
	}
	
	public void addCollisionBoxes(World w, int x, int y, int z, int m, List<AxisAlignedBB> boxes, Entity e)
	{
		AxisAlignedBB bb = getCollisionBoundingBoxFromPool(w, x, y, z);
		if(bb != null) boxes.add(bb.getOffsetBoundingBox(-x, -y, -z));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getCustomPaintIcon(int side, Paint p)
	{
		if(LatBlocksClient.fluidsFlowing.getAsBoolean()) return null;
		
		if(p.block instanceof IFluidBlock)
		{
			Fluid f = ((IFluidBlock) p.block).getFluid();
			if(f != null) return f.getStillIcon();
		}
		else if(p.block instanceof BlockLiquid || p.block instanceof BlockFluidBase) return p.block.getIcon(1, p.meta);
		
		return null;
	}
}