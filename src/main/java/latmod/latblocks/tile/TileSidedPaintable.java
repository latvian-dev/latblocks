package latmod.latblocks.tile;

import latmod.latblocks.api.Paint;

public abstract class TileSidedPaintable extends TilePaintableLB
{
	public TileSidedPaintable()
	{ super(6); }
	
	public Paint getPaint(int side)
	{ return paint[side]; }
	
	public void setPaint(int side, Paint p)
	{ paint[side] = p; }
}