package latmod.latblocks.block;

public enum Placement
{
	CENTER,
	UP,
	UP_RIGHT,
	RIGHT,
	DOWN_RIGHT,
	DOWN,
	DOWN_LEFT,
	LEFT,
	UP_LEFT,
	NONE;
	
	public static final double B0 = 1D / 16D * 3D;
	public static final double B1 = 1D - B0;
	
	/**
	 * Y-: 0, -1, 0
	 */
	public static final int D_DOWN = 0;
	
	/**
	 * Y+: 0, 1, 0
	 */
	public static final int D_UP = 1;
	
	/**
	 * Z-: 0, 0, -1
	 */
	public static final int D_NORTH = 2;
	
	/**
	 * Z+: 0, 0, 1
	 */
	public static final int D_SOUTH = 3;
	
	/**
	 * X-: -1, 0, 0
	 */
	public static final int D_WEST = 4;
	
	/**
	 * X+: 1, 0, 0
	 */
	public static final int D_EAST = 5;
	
	Placement() { }
	
	public static Placement get(double b0, double b1, double x, double y)
	{
		if(x >= b0 && x <= b1 && y >= b0 && y <= b1) return CENTER;
		else
		{
			if(x <= b0 && y <= b0) return UP_LEFT;
			if(x >= b1 && y <= b0) return UP_RIGHT;
			if(x <= b0 && y >= b1) return DOWN_LEFT;
			if(x >= b1 && y >= b1) return DOWN_RIGHT;
			
			if(x > b0 && x < b1)
			{
				if(y <= b0) return UP;
				if(y >= b1) return DOWN;
			}
			
			if(y > b0 && y < b1)
			{
				if(x >= b1) return RIGHT;
				if(x <= b0) return LEFT;
			}
		}
		
		return NONE;
	}
	
	public static Placement get(double x, double y)
	{ return get(B0, B1, x, y); }
}