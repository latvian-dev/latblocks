package latmod.latblocks.block;

import java.util.List;

import latmod.core.LMMod;
import latmod.core.block.BlockLM;
import latmod.core.item.ItemBlockLM;
import latmod.core.util.FastList;
import latmod.latblocks.LatBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public abstract class BlockLB extends BlockLM
{
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
}