package latmod.latblocks.item;

import ftb.lib.LMMod;
import ftb.lib.api.item.ItemLM;
import latmod.latblocks.LatBlocks;

public class ItemLB extends ItemLM
{
	public ItemLB(String s)
	{
		super(s);
		setCreativeTab(LatBlocks.tab);
	}
	
	@Override
	public LMMod getMod()
	{ return LatBlocks.mod; }
}