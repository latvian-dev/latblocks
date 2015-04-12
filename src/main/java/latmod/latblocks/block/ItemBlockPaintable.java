package latmod.latblocks.block;

import latmod.core.item.ItemBlockLM;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockPaintable extends ItemBlockLM
{
	public ItemBlockPaintable(Block b)
	{
		super(b);
	}
	
	public boolean canPlace(World w, int x, int y, int z, int s, ItemStack is)
	{
		return true;
		/*
		Block b = w.getBlock(x, y, z);
		
        if (b == Blocks.snow_layer && (w.getBlockMetadata(x, y, z) & 7) < 1) s = 1;
        else if (b != Blocks.vine && b != Blocks.tallgrass && b != Blocks.deadbush && !b.isReplaceable(w, x, y, z))
        {
        	x += ForgeDirection.VALID_DIRECTIONS[s].offsetX;
			y += ForgeDirection.VALID_DIRECTIONS[s].offsetY;
			z += ForgeDirection.VALID_DIRECTIONS[s].offsetZ;
        }
        
		return b.getMaterial() != Material.air && w.canPlaceEntityOnSide(b, x, y, z, false, s, null, is.copy());
		*/
	}
}