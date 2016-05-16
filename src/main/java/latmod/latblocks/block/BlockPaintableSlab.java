package latmod.latblocks.block;

import com.feed_the_beast.ftbl.util.BlockStateSerializer;
import latmod.latblocks.tile.TilePaintable;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class BlockPaintableSlab extends BlockPaintable
{
	public static final PropertyEnum<BlockSlab.EnumBlockHalf> HALF = PropertyEnum.create("half", BlockSlab.EnumBlockHalf.class);
	public static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0D, 0D, 0D, 1D, 0.5D, 1D);
	public static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0D, 0.5D, 0D, 1D, 1D, 1D);
	
	public BlockPaintableSlab()
	{
		setLightOpacity(255);
	}
	
	@Override
	public TilePaintable createTileEntity(World w, IBlockState state)
	{ return new TilePaintable.Single(); }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{ return false; }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{ return false; }
	
	@Override
	protected BlockStateContainer createBlockState()
	{ return new BlockStateContainer(this, HALF); }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{ return getDefaultState().withProperty(HALF, meta == 1 ? BlockSlab.EnumBlockHalf.TOP : BlockSlab.EnumBlockHalf.BOTTOM); }
	
	@Override
	public int getMetaFromState(IBlockState state)
	{ return state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP ? 1 : 0; }
	
	@Override
	public String getModelState()
	{ return BlockStateSerializer.getString(HALF, BlockSlab.EnumBlockHalf.BOTTOM); }
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{ return state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF; }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate : iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.TOP);
	}
}