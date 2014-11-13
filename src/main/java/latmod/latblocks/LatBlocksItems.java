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
	
	public static ItemBlockPainter i_painter;
	public static ItemBlockPainterDmd i_painter_dmd;
	
	public static void init()
	{
		LatBlocks.mod.addBlock(b_paintable = new BlockPaintable("paintable"));
		LatBlocks.mod.addBlock(b_rs_paintable = new BlockRSPaintable("paintableRS"));
		LatBlocks.mod.addBlock(b_gs_paintable = new BlockGSPaintable("paintableGS"));
		LatBlocks.mod.addBlock(b_facade = new BlockFacade("facade"));
		LatBlocks.mod.addBlock(b_fountain = new BlockFountain("fountain"));
		
		LatBlocks.mod.addItem(i_painter = new ItemBlockPainter("blockPainter"));
		LatBlocks.mod.addItem(i_painter_dmd = new ItemBlockPainterDmd("blockPainterDmd"));
	}
}