package latmod.latblocks.api;

import net.minecraft.world.IBlockAccess;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public interface INoPaintBlock
{
	boolean hasPaint(IBlockAccess iba, int x, int y, int z, int s);
}
