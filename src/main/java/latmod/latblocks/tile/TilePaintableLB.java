package latmod.latblocks.tile;

import latmod.core.tile.*;

public abstract class TilePaintableLB extends TileLM implements IPaintable, IWailaTile.Stack
{
	public boolean rerenderBlock()
	{ return true; }
	
	public int iconMeta()
	{ return 0; }
	
	public abstract Paint[] getPaint();
}