package latmod.latblocks;

import latmod.latblocks.block.*;
import latmod.latblocks.item.*;

public class LatBlocksItems
{
	public static BlockPaintable b_paintable;
	public static BlockPaintableRS b_paintable_rs;
	public static BlockPaintableGS b_paintable_gs;
	public static BlockCover b_cover;
	public static BlockFountain b_fountain;
	public static BlockPCarpet b_carpet;
	
	public static ItemBlockPainter i_painter;
	public static ItemBlockPainterDmd i_painter_dmd;
	
	public static void init()
	{
		b_paintable = new BlockPaintable("paintable").register();
		b_paintable_rs = new BlockPaintableRS("paintableRS").register();
		b_paintable_gs = new BlockPaintableGS("paintableGS").register();
		b_cover = new BlockCover("cover").register();
		b_fountain = new BlockFountain("fountain").register();
		b_carpet = new BlockPCarpet("carpet").register();
		
		i_painter = new ItemBlockPainter("blockPainter").register();
		i_painter_dmd = new ItemBlockPainterDmd("blockPainterDmd").register();
	}
}