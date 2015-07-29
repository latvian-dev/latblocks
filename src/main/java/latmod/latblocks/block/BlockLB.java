package latmod.latblocks.block;

import java.util.List;

import latmod.ftbu.core.LMMod;
import latmod.ftbu.core.block.BlockLM;
import latmod.ftbu.core.item.ItemBlockLM;
import latmod.ftbu.core.paint.*;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.client.LatBlocksClient;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import cpw.mods.fml.relauncher.*;

public abstract class BlockLB extends BlockLM implements ICustomPaintBlockIcon
{
	public boolean hasSpecialPlacement = false;
	
	public BlockLB(String s, Material m)
	{ super(s, m); }
	
	public LMMod getMod()
	{ return LatBlocks.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return LatBlocks.tab; }
	
	public Class<? extends ItemBlockLM> getItemBlock()
	{ return ItemBlockLB.class; }
	
	@SuppressWarnings("all")
	public final void addCollisionBoxesToList(World w, int x, int y, int z, AxisAlignedBB bb, List l, Entity e)
	{
		FastList<AxisAlignedBB> boxes = new FastList<AxisAlignedBB>();
		addCollisionBoxes(w, x, y, z, -1, boxes, e);
		
		for(int i = 0; i < boxes.size(); i++)
		{
			AxisAlignedBB bb1 = boxes.get(i).getOffsetBoundingBox(x, y, z);
			if(bb.intersectsWith(bb1)) l.add(bb1);
		}
	}
	
	public void addCollisionBoxes(World w, int x, int y, int z, int m, FastList<AxisAlignedBB> boxes, Entity e)
	{
		AxisAlignedBB bb = getCollisionBoundingBoxFromPool(w, x, y, z);
		if(bb != null) boxes.add(bb.getOffsetBoundingBox(-x, -y, -z));
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getCustomPaintIcon(int side, Paint p)
	{
		if(LatBlocksClient.fluidsFlowing.getB()) return null;
		
		if(p.block instanceof IFluidBlock)
		{
			Fluid f = ((IFluidBlock)p.block).getFluid();
			if(f != null) return f.getStillIcon();
		}
		else if(p.block instanceof BlockLiquid || p.block instanceof BlockFluidBase)
			return p.block.getIcon(1, p.meta);
		
		return null;
	}
}