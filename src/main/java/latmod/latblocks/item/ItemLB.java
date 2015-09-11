package latmod.latblocks.item;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.core.LMMod;
import latmod.ftbu.core.item.ItemLM;
import latmod.latblocks.LatBlocks;
import net.minecraft.creativetab.CreativeTabs;

public class ItemLB extends ItemLM
{
	public ItemLB(String s)
	{ super(s); }
	
	public LMMod getMod()
	{ return LatBlocks.mod; }
	
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{ return LatBlocks.tab; }
}