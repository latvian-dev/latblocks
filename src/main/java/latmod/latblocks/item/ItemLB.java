package latmod.latblocks.item;

import ftb.lib.LMMod;
import ftb.lib.api.item.ItemLM;
import latmod.latblocks.LatBlocks;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class ItemLB extends ItemLM
{
	public ItemLB()
	{
		super();
		setCreativeTab(LatBlocks.tab);
	}
	
	@Override
	public LMMod getMod()
	{ return LatBlocks.mod; }
}