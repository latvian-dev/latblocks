package latmod.latblocks.block;

import ftb.lib.LMMod;
import ftb.lib.api.block.BlockLM;
import latmod.latblocks.LatBlocks;
import net.minecraft.block.material.Material;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockLB extends BlockLM
{
	public BlockLB(Material m)
	{
		super(m);
		setCreativeTab(LatBlocks.tab);
	}
	
	@Override
	public LMMod getMod()
	{ return LatBlocks.mod; }
}