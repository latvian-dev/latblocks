package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.FTBLibAPI;
import com.feed_the_beast.ftbl.api.gui.GuiHelper;
import com.latmod.latblocks.gui.LBGuis;
import com.latmod.latblocks.tile.TileCraftingPanel;
import com.latmod.lib.math.MathHelperLM;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class BlockCraftingPanel extends BlockLB
{
    private static final AxisAlignedBB[] BOXES = MathHelperLM.getRotatedBoxes(new AxisAlignedBB(3D / 16D, 0D, 3D / 16D, 13D / 16D, 1D / 13D, 13D / 16D));

    public BlockCraftingPanel()
    {
        super(Material.WOOD);
        setDefaultState(blockState.getBaseState().withProperty(BlockDirectional.FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World w, IBlockState state)
    {
        return new TileCraftingPanel();
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.VALUES[meta % 6]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockDirectional.FACING).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    @Deprecated
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(BlockDirectional.FACING, facing.getOpposite());
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOXES[state.getValue(BlockDirectional.FACING).ordinal()];
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileCraftingPanel)
            {
                ((TileCraftingPanel) te).dropItems();
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileCraftingPanel)
            {
                te.markDirty();
                FTBLibAPI.get().openGui(LBGuis.CRAFTING_PANEL, (EntityPlayerMP) playerIn, GuiHelper.getPosData(te));
            }
        }

        return true;
    }
}