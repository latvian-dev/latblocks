package latmod.latblocks.block;

import latmod.ftbu.core.item.ItemBlockLM;
import latmod.ftbu.core.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemBlockLB extends ItemBlockLM
{
	public ItemBlockLB(Block b)
	{
		super(b);
	}
	
	public boolean canPlace(World w, int x, int y, int z, int s, EntityPlayer ep, ItemStack is)
	{
		MovingObjectPosition mop = MathHelperLM.rayTrace(ep);
		if(mop == null) return false;
		
		BlockLB b = (BlockLB) Block.getBlockFromItem(is.getItem());
		
		int bx = x + Facing.offsetsXForSide[s];
		int by = y + Facing.offsetsYForSide[s];
		int bz = z + Facing.offsetsZForSide[s];
		
		FastList<AxisAlignedBB> list = new FastList<AxisAlignedBB>();
		int m = b.onBlockPlaced(w, ep, mop, is.getItemDamage());
		
		if(m == -1) return false;
		
		b.addCollisionBoxes(w, bx, by, bz, m, list, ep);
		
		if(list.isEmpty()) return true;
		
		for(AxisAlignedBB aabb : list)
		{
			if(!w.checkNoEntityCollision(aabb.getOffsetBoundingBox(bx, by, bz), null))
				return false;
		}
		
		return true;
	}
}