package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.client.gui.GuiHandler;
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
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer ep, EnumHand hand, ItemStack item, EnumFacing s, float x1, float y1, float z1)
    {
        if(!w.isRemote)
        {
            TileEntity te = w.getTileEntity(pos);

            if(te instanceof TileNetherChest)
            {
                LBGuiHandler.INSTANCE.openGui(ep, LBGuiHandler.NETHER_CHEST, GuiHandler.getTileData(te));
            }
        }

        return true;
    }
}