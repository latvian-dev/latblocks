package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.client.gui.GuiHandler;
import com.feed_the_beast.ftbl.api.item.LMInvUtils;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.latblocks.gui.LBGuiHandler;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by LatvianModder on 13.07.2016.
 */
public class BlockNetherChest extends BlockLB
{
    public BlockNetherChest()
    {
        super(Material.ROCK);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public void loadTiles()
    {
        FTBLib.addTile(TileNetherChest.class, getRegistryName());
    }

    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state)
    {
        return new TileNetherChest();
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this),
                "NQN", "NCN", "NQN",
                'N', Blocks.NETHER_BRICK,
                'Q', ODItems.QUARTZ_BLOCK,
                'C', Items.END_CRYSTAL);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileNetherChest)
            {
                te.markDirty();
                LBGuiHandler.INSTANCE.openGui(playerIn, LBGuiHandler.NETHER_CHEST, GuiHandler.getTileData(te));
            }
        }

        return true;
    }

    @Override
    public void breakBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        if(!worldIn.isRemote)
        {
            TileEntity te = worldIn.getTileEntity(pos);

            if(te instanceof TileNetherChest)
            {
                LMInvUtils.dropAllItems(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, ((TileNetherChest) te).items);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }
}