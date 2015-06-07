package latmod.latblocks.block.tank;

import latmod.ftbu.core.InvUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class TankCraftingHandler implements IRecipe // ShapedRecipes
{
	public final ItemStack output;
	public final ItemStack input;
	public final ItemStack item;
	
	public TankCraftingHandler(ItemStack out, ItemStack in, ItemStack m)
	{ output = out; input = in; item = m; }
	
	@SuppressWarnings("unchecked")
	public static void register(ItemStack out, ItemStack in, ItemStack m)
	{ CraftingManager.getInstance().getRecipeList().add(new TankCraftingHandler(out, in, m)); }
	
	public boolean matches(InventoryCrafting inv, World w)
	{
		boolean hasItem = false;
		int tanks = 0;
		
		if(inv.getSizeInventory() == 9)
		{
			ItemStack ci = inv.getStackInSlot(4);
			
			hasItem = ci != null && ci.stackSize > 0 && InvUtils.itemsEquals(ci, item, false, false);
			
			if(hasItem) for(int i = 0; i < 9; i++)
			{
				ItemStack is = inv.getStackInSlot(i);
				
				if(is != null && i != 4)
				{
					if(InvUtils.itemsEquals(is, input, false, false))
						tanks++;
				}
			}
		}
		
		return hasItem && tanks == 8;
	}
	
	public ItemStack getCraftingResult(InventoryCrafting inv)
	{
		ItemStack out = getRecipeOutput();
		FluidStack outFluid = null;
		
		if(inv.getSizeInventory() == 9) for(int i = 0; i < 9; i++)
		{
			ItemStack is = inv.getStackInSlot(i);
			
			if(i != 4 && is != null && is.hasTagCompound() && is.stackTagCompound.hasKey("Fluid"))
			{
				FluidStack fs = FluidStack.loadFluidStackFromNBT(is.stackTagCompound.getCompoundTag("Fluid"));
				
				if(fs != null && fs.amount > 0 && (outFluid == null || outFluid.isFluidEqual(fs)))
				{
					if(outFluid == null) outFluid = fs.copy();
					else outFluid.amount += fs.amount;
					
				}
			}
		}
		
		if(outFluid != null && outFluid.amount > 0)
		{
			NBTTagCompound tag = new NBTTagCompound();
			outFluid.writeToNBT(tag);
			out.stackTagCompound = new NBTTagCompound();
			out.stackTagCompound.setTag("Fluid", tag);
		}
		
		return out;
	}
	
	public int getRecipeSize()
	{ return 9; }
	
	public ItemStack getRecipeOutput()
	{ return output.copy(); }
}