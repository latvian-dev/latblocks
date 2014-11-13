package latmod.latblocks;

import latmod.latblocks.block.*;
import latmod.latblocks.item.*;

public class LatBlocksItems
{
	public static BlockPaintable b_paintable;
	public static BlockRSPaintable b_rs_paintable;
	public static BlockGSPaintable b_gs_paintable;
	public static BlockFacade b_facade;
	public static BlockFountain b_fountain;
	public static BlockCarpet b_carpet;
	
	public static ItemBlockPainter i_painter;
	public static ItemBlockPainterDmd i_painter_dmd;
	
	public static void init()
	{
		b_paintable = new BlockPaintable("paintable").register();
		b_rs_paintable = new BlockRSPaintable("paintableRS").register();
		b_gs_paintable = new BlockGSPaintable("paintableGS").register();
		b_facade = new BlockFacade("facade").register();
		b_fountain = new BlockFountain("fountain").register();
		b_carpet = new BlockCarpet("carpet").register();
		
		i_painter = new ItemBlockPainter("blockPainter").register();
		i_painter_dmd = new ItemBlockPainterDmd("blockPainterDmd").register();
	}
}