package com.latmod.latblocks.block;

import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.util.FTBLib;
import com.latmod.latblocks.tile.TileNetherChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
}