package com.latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.item.ODItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

import java.util.List;

/**
 * Created by LatvianModder on 16.05.2016.
 */
public class ItemHammer extends ItemLB
{
    public ItemHammer()
    {
        setMaxDamage(1024);
        setMaxStackSize(1);
        setFull3D();
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this, 1, 0),
                "OBO", " I ", " I ",
                'O', ODItems.OBSIDIAN,
                'B', "blockIron",
                'I', ODItems.IRON);

        List<String> list = Arrays.asList(OreDictionary.getOreNames());

        for(String s : list)
        {
            if(s.startsWith("ingot"))
            {
                ItemStack is = ODItems.getFirstOre("dust" + s.substring(5));

                if(is != null)
                {
                    ItemStack is1 = is.copy();
                    is1.stackSize = 1;
                    getMod().recipes.addShapelessRecipe(is1, this, s);
                }
            }
            else if(s.startsWith("ore"))
            {
                ItemStack is = ODItems.getFirstOre("dust" + s.substring(3));

                if(is != null)
                {
                    ItemStack is1 = is.copy();
                    is1.stackSize = 2;
                    getMod().recipes.addShapelessRecipe(is1, this, s);
                }
            }
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return stack.getItemDamage() <= getMaxDamage(stack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if(hasContainerItem(itemStack))
        {
            return new ItemStack(this, 1, itemStack.getItemDamage() + 1);
        }

        return null;
    }
}