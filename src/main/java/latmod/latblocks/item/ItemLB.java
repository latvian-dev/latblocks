package latmod.latblocks.item;

import latmod.core.mod.*;
import latmod.core.mod.item.ItemLM;
import latmod.latblocks.LatBlocks;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.*;

public class ItemLB extends ItemLM
{
	public ItemLB(String s)
	{ super(s); }
	
	public LMMod<?, ?> getMod()
	{ return LC.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return LatBlocks.tab; }
}