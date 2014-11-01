package latmod.latblocks.block;

import latmod.core.LMMod;
import latmod.core.block.BlockLM;
import latmod.latblocks.LatBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

public abstract class BlockLB extends BlockLM
{
	public BlockLB(String s, Material m)
	{ super(s, m); }
	
	public LMMod<?, ?> getMod()
	{ return LatBlocks.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTabToDisplayOn()
	{ return LatBlocks.tab; }
}