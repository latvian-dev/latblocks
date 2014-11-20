package latmod.latblocks;

import latmod.core.*;
import latmod.latblocks.block.*;
import latmod.latblocks.block.paintable.*;
import latmod.latblocks.item.*;
import net.minecraft.item.ItemStack;

public class LatBlocksItems
{
	public static BlockPaintableSided b_paintable;
	public static BlockPaintableRS b_paintable_rs;
	public static BlockPaintableGS b_paintable_gs;
	public static BlockFountain b_fountain;
	public static BlockPCover b_cover;
	public static BlockPCarpet b_carpet;
	public static BlockPSlab b_slab;
	public static BlockPStairs b_stairs;
	public static BlockNoteBoard b_note_board;
	
	public static ItemBlockPainter i_painter;
	public static ItemBlockPainterDmd i_painter_dmd;
	public static ItemNote i_note;
	
	public static void init()
	{
		b_paintable = new BlockPaintableDef("paintable").register();
		b_paintable_rs = new BlockPaintableRS("paintableRS").register();
		b_paintable_gs = new BlockPaintableGS("paintableGS").register();
		b_fountain = new BlockFountain("fountain").register();
		b_cover = new BlockPCover("cover").register();
		b_carpet = new BlockPCarpet("carpet").register();
		b_slab = new BlockPSlab("slab").register();
		b_stairs = new BlockPStairs("stairs").register();
		b_note_board = new BlockNoteBoard("noteBoard").register();
		
		i_painter = new ItemBlockPainter("blockPainter").register();
		i_painter_dmd = new ItemBlockPainterDmd("blockPainterDmd").register();
		i_note = new ItemNote("note").register();
		
		ODItems.paintableBlock = new ItemStack(b_paintable, 1, 0);
	}
}