package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.client.gui.GuiHandler;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.feed_the_beast.ftbl.util.MathHelperMC;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.gui.LBGuiHandler;
import com.latmod.latblocks.tile.TileCraftingPanel;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class BlockCraftingPanel extends BlockLB
{
    public static final AxisAlignedBB[] BOXES = MathHelperMC.getRotatedBoxes(new AxisAlignedBB(3D / 16D, 0D, 3D / 16D, 13D / 16D, 1D / 13D, 13D / 16D));
    public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.class);

    public BlockCraftingPanel()
    {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(BlockDirectional.FACING, EnumFacing.NORTH));
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this, 2), "C", "C", 'C', Blocks.CRAFTING_TABLE);
    }

    @Override
    public void loadTiles()
    {
        FTBLib.addTile(TileCraftingPanel.class, getRegistryName());
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World w, @Nonnull IBlockState state)
    {
        return new TileCraftingPanel();
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.VALUES[meta % 6]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).ordinal();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Nonnull
    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOXES[state.getValue(FACING).ordinal()];
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
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
                GuiHandler.openGui(LatBlocks.MOD_ID, playerIn, LBGuiHandler.CRAFTING_PANEL, GuiHandler.getTileData(te));
            }
        }

        return true;
    }
}