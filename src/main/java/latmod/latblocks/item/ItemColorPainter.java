package latmod.latblocks.item;

import ftb.lib.EnumMCColor;
import ftb.lib.api.gui.IClientActionItem;
import ftb.lib.item.ODItems;
import latmod.latblocks.LatBlocksGuiHandler;
import net.minecraft.block.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class ItemColorPainter extends ItemLB implements IClientActionItem
{
	public static final String ACTION = "ChangeColor";
	
	public ItemColorPainter(String s)
	{
		super(s);
		setMaxStackSize(1);
		setFull3D();
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addShapelessRecipe(new ItemStack(this, 1, 0), ItemMaterialsLB.PAINT_ROLLER_ROD, ItemMaterialsLB.PAINT_ROLLER_COLOR);
		
		for(int i = 0; i < 16; i++)
		{
			getMod().recipes.addShapelessRecipe(new ItemStack(Blocks.wool, 1, BlockColored.func_150032_b(i)), new ItemStack(this, 1, i), new ItemStack(Blocks.wool, 1, ODItems.ANY));
		}
	}
	
	public ItemStack getContainerItem(ItemStack is)
	{ return is == null ? null : is.copy(); }
	
	public boolean hasContainerItem(ItemStack is)
	{ return true; }
	
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack is)
	{ return false; }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(!w.isRemote && ep.isSneaking())
			LatBlocksGuiHandler.instance.openGui(ep, LatBlocksGuiHandler.COLOR_PAINTER, null);
		return is;
	}
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{
		Block b = w.getBlock(x, y, z);
		b.recolourBlock(w, x, y, z, ForgeDirection.VALID_DIRECTIONS[s], BlockColored.func_150032_b(is.getItemDamage()));
		return true;
	}
	
	public boolean itemInteractionForEntity(ItemStack is, EntityPlayer ep, EntityLivingBase el)
	{
		if(el instanceof EntitySheep)
		{
			EntitySheep es = (EntitySheep) el;
			int i = BlockColored.func_150032_b(is.getItemDamage());
			
			if(!es.getSheared() && es.getFleeceColor() != i)
			{
				es.setFleeceColor(i);
				is.stackSize--;
			}
			
			return true;
		}
		
		return false;
	}
	
	public ItemStack onClientAction(ItemStack is, EntityPlayer ep, String action, NBTTagCompound data)
	{
		if(action.equals(ACTION)) is.setItemDamage(data.getByte("Dmg"));
		return is;
	}
	
	public void addInfo(ItemStack is, EntityPlayer ep, List<String> l)
	{ l.add(EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + EnumMCColor.VALUES[is.getItemDamage()].toString()); }
}