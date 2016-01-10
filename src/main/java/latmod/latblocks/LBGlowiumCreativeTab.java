package latmod.latblocks;

import cpw.mods.fml.relauncher.*;
import latmod.lib.LMUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;

import java.util.*;

public class LBGlowiumCreativeTab extends CreativeTabs
{
	private final ArrayList<ItemStack> allBlocks = new ArrayList<>();
	
	public LBGlowiumCreativeTab()
	{ super(LatBlocks.mod.assets + "tab.glowium"); }
	
	public void init()
	{
		allBlocks.clear();
		
		for(int j = 0; j < 16; j++)
			for(int i = 0; i < LatBlocksItems.b_glowium.length; i++)
				allBlocks.add(new ItemStack(LatBlocksItems.b_glowium[i], 1, j));
	}
	
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{ return getIconItemStack().getItem(); }
	
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void displayAllReleventItems(List l)
	{ l.addAll(allBlocks); }
	
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack()
	{ return allBlocks.get((int) ((LMUtils.millis() / 1000L) % allBlocks.size())); }
}