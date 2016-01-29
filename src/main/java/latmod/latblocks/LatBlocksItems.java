package latmod.latblocks;

import ftb.lib.LMMod;
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
	public static BlockPDoor b_door;
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
	public static BlockQCable b_qcable;
	public static BlockQTerminal b_qterminal;
	public static BlockGlowiumFarmland b_farmland;
	
	public static BlockGlowium[] b_glowium;
	public static BlockLBBricks b_bricks;
	
	public static ItemMaterialsLB i_mat;
	public static ItemBlockPainter i_painter;
	public static ItemBlockPainterDmd i_painter_dmd;
	public static ItemColorPainter i_painter_col;
	public static ItemHammer i_hammer;
	public static ItemGlasses i_glasses;
	//public static ItemQBag i_qbag;
	public static ItemEnderBag i_ebag;
	
	public static void init()
	{
		LMMod m = LatBlocks.mod;
		m.addItem(b_paintable = new BlockPaintableDef("paintable"));
		m.addItem(b_paintable_rs = new BlockPaintableRS("paintableRS"));
		m.addItem(b_paintable_gs = new BlockPaintableGS("paintableGS"));
		m.addItem(b_lamp = new BlockPaintableLamp("paintableLamp"));
		m.addItem(b_fountain = new BlockFountain("fountain"));
		m.addItem(b_cover = new BlockPCover("cover"));
		m.addItem(b_carpet = new BlockPCarpet("carpet"));
		m.addItem(b_slab = new BlockPSlab("slab"));
		m.addItem(b_stairs = new BlockPStairs("stairs"));
		m.addItem(b_fence = new BlockPFence("fence"));
		m.addItem(b_fence_gate = new BlockPFenceGate("fenceGate"));
		m.addItem(b_wall = new BlockPWall("wall"));
		m.addItem(b_ladder = new BlockPLadder("ladder"));
		m.addItem(b_pressure_plate = new BlockPPressurePlate("pressurePlate"));
		m.addItem(b_door = new BlockPDoor("door"));
		//b_slope = new BlockPSlope("slope"));
		
		m.addItem(b_qchest = new BlockQChest("quartzChest"));
		m.addItem(b_qfurnace = new BlockQFurnace("quartzFurnace"));
		//b_qcrafting_table = new BlockQCraftingTable("quartzCraftingTable"));
		m.addItem(b_tank_water = new BlockWaterTank("tankWater"));
		m.addItem(b_tank_void = new BlockVoidTank("tankVoid"));
		m.addItem(b_gel_lamp = new BlockGelLamp("gelLamp"));
		m.addItem(b_tank = new BlockTank("tank"));
		m.addItem(b_glass = new BlockGlass("glass"));
		m.addItem(b_crafting_panel = new BlockCraftingPanel("craftingPanel"));
		m.addItem(b_qcable = new BlockQCable("cable"));
		m.addItem(b_qterminal = new BlockQTerminal("terminal"));
		m.addItem(b_farmland = new BlockGlowiumFarmland("farmland"));
		
		m.addItem(b_glowium = new BlockGlowium[] {new BlockGlowium.BGBlock("glowiumBlock"), new BlockGlowium.BGTile("glowiumTile"), new BlockGlowium.BGBrick("glowiumBrick"), new BlockGlowium.BGBrickSmall("glowiumSmallBrick"), new BlockGlowium.BGBrickChiseled("glowiumChiseledBrick"), new BlockLinedBlock("lined_block")});
		
		m.addItem(b_bricks = new BlockLBBricks("bricks"));
		
		m.addItem(i_mat = new ItemMaterialsLB("materials"));
		m.addItem(i_painter = new ItemBlockPainter("blockPainter"));
		m.addItem(i_painter_dmd = new ItemBlockPainterDmd("blockPainterDmd"));
		m.addItem(i_painter_col = new ItemColorPainter("colorPainter"));
		m.addItem(i_hammer = new ItemHammer("hammer"));
		m.addItem(i_glasses = new ItemGlasses("glasses"));
		//i_qbag = new ItemQBag("qbag"));
		m.addItem(i_ebag = new ItemEnderBag("ebag"));
	}
}