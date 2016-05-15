package latmod.latblocks.block;

import com.feed_the_beast.ftbl.util.FTBLib;
import latmod.latblocks.tile.TilePaintable;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockPaintableDef extends BlockPaintable
{
	public BlockPaintableDef()
	{
		super(false);
	}
	
	@Override
	public void loadTiles()
	{
		FTBLib.addTile(TilePaintable.class, getRegistryName());
	}
}