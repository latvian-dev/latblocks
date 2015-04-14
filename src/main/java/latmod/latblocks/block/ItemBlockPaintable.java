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
		return super.canPlace(w, x, y, z, s, is);
	}
}