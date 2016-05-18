package latmod.latblocks.block;

import com.feed_the_beast.ftbl.util.BlockStateSerializer;
import com.feed_the_beast.ftbl.util.MathHelperMC;
import latmod.latblocks.tile.TilePaintable;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockPaintableSlab extends BlockPaintable
{
    public final AxisAlignedBB[] BOXES;
    
    public BlockPaintableSlab(double height)
    {
        setLightOpacity(255);
        translucent = true;
        BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(0D, 0D, 0D, 1D, height, 1D));
    }
    
    @Override
    public TilePaintable createTileEntity(World w, IBlockState state)
    { return new TilePaintable.Single(); }
    
    @Override
    public int damageDropped(IBlockState state)
    { return 0; }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    { return false; }
    
    @Override
    public boolean isFullCube(IBlockState state)
    { return false; }
    
    @Override
    protected BlockStateContainer createBlockState()
    { return new BlockStateContainer(this, BlockDirectional.FACING); }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    { return getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.VALUES[meta]); }
    
    @Override
    public int getMetaFromState(IBlockState state)
    { return state.getValue(BlockDirectional.FACING).ordinal(); }
    
    @Override
    public String getModelState()
    { return BlockStateSerializer.getString(BlockDirectional.FACING, EnumFacing.DOWN); }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    { return BOXES[state.getValue(BlockDirectional.FACING).ordinal()]; }
    
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    { return getDefaultState().withProperty(BlockDirectional.FACING, facing.getOpposite()); }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    { return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING))); }
    
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    { return state.withRotation(mirrorIn.toRotation(state.getValue(BlockDirectional.FACING))); }
}