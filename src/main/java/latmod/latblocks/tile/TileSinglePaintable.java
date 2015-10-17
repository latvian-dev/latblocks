package latmod.latblocks.tile;

import latmod.ftbu.api.paint.Paint;

public abstract class TileSinglePaintable extends TilePaintableLB
{
	public TileSinglePaintable()
	{ super(1); }
	
	public Paint getPaint(int side)
	{ return paint[0]; }
	
	public void setPaint(int side, Paint p)
	{ paint[0] = p; }
}