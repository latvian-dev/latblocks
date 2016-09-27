package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.latmod.latblocks.FTBLibIntegration;
import com.latmod.latblocks.gui.ContainerNetherChest;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileNetherChest();
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
                FTBLibIntegration.API.openGui(ContainerNetherChest.ID, (EntityPlayerMP) playerIn, GuiHelper.getPosData(te));
            }
        }

        return true;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        if(te instanceof TileNetherChest)
        {
            ItemStack itemstack = new ItemStack(this, 1, 0);

            itemstack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = new NBTTagCompound();
            ((TileNetherChest) te).writeTileData(tag);
            itemstack.getTagCompound().setTag("NetherChestData", tag);

            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, null, stack);
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new ArrayList<>();
        ItemStack itemstack = new ItemStack(this, 1, 0);

        TileEntity te = world.getTileEntity(pos);

        if(te instanceof TileNetherChest)
        {
            itemstack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = new NBTTagCompound();
            ((TileNetherChest) te).writeTileData(tag);
            itemstack.getTagCompound().setTag("NetherChestData", tag);
        }

        ret.add(itemstack);
        return ret;
    }
}