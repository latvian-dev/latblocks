package latmod.latblocks.api;

import net.minecraft.world.World;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public interface ICustomPaintBlock
{
	Paint getCustomPaint(World w, int x, int y, int z);
}
