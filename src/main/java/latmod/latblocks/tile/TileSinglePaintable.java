package latmod.latblocks.tile;

import latmod.latblocks.api.Paint;

public abstract class TileSinglePaintable extends TilePaintableLB
{
	public TileSinglePaintable()
	{ super(1); }
	
	@Override
	public Paint getPaint(int side)
	{ return paint[0]; }
	
	@Override
	public void setPaint(int side, Paint p)
	{ paint[0] = p; }
}