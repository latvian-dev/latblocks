package latmod.latblocks;

import latmod.latblocks.block.*;
import latmod.latblocks.block.paintable.*;
import latmod.latblocks.block.tank.*;
import latmod.latblocks.item.*;

public class LatBlocksItems
{
	public static BlockPaintableDef b_paintable;
	public static BlockPaintableRS b_paintable_rs;
	public static BlockPaintableGS b_paintable_gs;
	public static BlockPaintableLamp b_lamp;
	public static BlockFountain b_fountain;
	public static BlockPCover b_cover;
	public static BlockPCarpet b_carpet;
	public static BlockPSlab b_slab;
	public static BlockPStairs b_stairs;
	public static BlockPFence b_fence;
	public static BlockPFenceGate b_fence_gate;
	public static BlockPWall b_wall;
	public static BlockPLadder b_ladder;
	public static BlockPPressurePlate b_pressure_plate;
	//public static BlockPDoor b_door;
	//public static BlockPSlope b_slope;
	
	public static BlockQChest b_qchest;
	public static BlockQFurnace b_qfurnace;
	//public static BlockQCraftingTable b_qcrafting_table;
	public static BlockWaterTank b_tank_water;
	public static BlockVoidTank b_tank_void;
	public static BlockGelLamp b_gel_lamp;
	public static BlockTank b_tank;
	public static BlockGlass b_glass;
	public static BlockCraftingPanel b_crafting_panel;
	
	public static BlockGlowium[] b_glowium;
	public static BlockLBBricks b_bricks;
	
	public static ItemMaterialsLB i_mat;
	public static ItemPainterParts i_parts;
	public static ItemBlockPainter i_painter;
	public static ItemBlockPainterDmd i_painter_dmd;
	public static ItemColorPainter i_painter_col;
	public static ItemHammer i_hammer;
	public static ItemGlasses i_glasses;
	public static ItemQuartzBag i_qbag;
	//public static ItemPocketFurnace i_pfurnace;
	
	public static void init()
	{
		b_paintable = new BlockPaintableDef("paintable").register();
		b_paintable_rs = new BlockPaintableRS("paintableRS").register();
		b_paintable_gs = new BlockPaintableGS("paintableGS").register();
		b_lamp = new BlockPaintableLamp("paintableLamp").register();
		b_fountain = new BlockFountain("fountain").register();
		b_cover = new BlockPCover("cover").register();
		b_carpet = new BlockPCarpet("carpet").register();
		b_slab = new BlockPSlab("slab").register();
		b_stairs = new BlockPStairs("stairs").register();
		b_fence = new BlockPFence("fence").register();
		b_fence_gate = new BlockPFenceGate("fenceGate").register();
		b_wall = new BlockPWall("wall").register();
		b_ladder = new BlockPLadder("ladder").register();
		b_pressure_plate = new BlockPPressurePlate("pressurePlate").register();
		//b_door = new BlockPDoor("door").register();
		//b_slope = new BlockPSlope("slope").register();
		
		b_qchest = new BlockQChest("quartzChest").register();
		b_qfurnace = new BlockQFurnace("quartzFurnace").register();
		//b_qcrafting_table = new BlockQCraftingTable("quartzCraftingTable").register();
		b_tank_water = new BlockWaterTank("tankWater").register();
		b_tank_void = new BlockVoidTank("tankVoid").register();
		b_gel_lamp = new BlockGelLamp("gelLamp").register();
		b_tank = new BlockTank("tank").register();
		b_glass = new BlockGlass("glass").register();
		b_crafting_panel = new BlockCraftingPanel("craftingPanel").register();
		
		b_glowium = new BlockGlowium[]
		{
			new BlockGlowium.BGBlock("glowiumBlock").register(),
			new BlockGlowium.BGTile("glowiumTile").register(),
			new BlockGlowium.BGBrick("glowiumBrick").register(),
			new BlockGlowium.BGBrickSmall("glowiumSmallBrick").register(),
			new BlockGlowium.BGBrickChiseled("glowiumChiseledBrick").register(),
			new BlockLinedBlock("lined_block").register(),
		};
		
		b_bricks = new BlockLBBricks("bricks").register();
		
		i_mat = new ItemMaterialsLB("materials").register();
		i_parts = new ItemPainterParts("painterParts").register();
		i_painter = new ItemBlockPainter("blockPainter").register();
		i_painter_dmd = new ItemBlockPainterDmd("blockPainterDmd").register();
		i_painter_col = new ItemColorPainter("colorPainter").register();
		i_hammer = new ItemHammer("hammer").register();
		i_glasses = new ItemGlasses("glasses").register();
		i_qbag = new ItemQuartzBag("qbag").register();
		//i_pfurnace = new  ItemPocketFurnace("pfurnace").register();
	}
}