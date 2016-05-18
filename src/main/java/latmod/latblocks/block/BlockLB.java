package latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.block.BlockLM;
import com.feed_the_beast.ftbl.util.LMMod;
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