package latmod.latblocks.block;

import latmod.latblocks.tile.TilePaintable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockPaintable extends BlockLB
{
    public BlockPaintable()
    {
        super(Material.WOOD);
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player)
    {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nonnull
    @Override
    public TilePaintable createTileEntity(@Nonnull World w, @Nonnull IBlockState state)
    {
        return null;
    }
}