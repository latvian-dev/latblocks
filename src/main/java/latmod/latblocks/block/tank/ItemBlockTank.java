package latmod.latblocks.block.tank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.block.ItemBlockLM;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.lib.MathHelperLM;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by LatvianModder on 24.04.2016.
 */
public class ItemBlockTank extends ItemBlockLM
{
	public ItemBlockTank(Block b)
	{
		super(b);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer ep, List l, boolean adv)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("Fluid"))
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(is.stackTagCompound.getCompoundTag("Fluid"));
			l.add("Stored: " + fs.amount + "mB of " + fs.getLocalizedName());
		}
		
		int meta = is.getItemDamage();
		
		int cap = MathHelperLM.power(8, meta);
		
		DecimalFormat format = new DecimalFormat("###,###,###,###,###.###");
		l.add(format.format(((meta == 5) ? 2000000000 : (cap * 1000))).replace(',', '\'') + " mB [ Mk" + (meta + 1) + " ]");
		
		if(GuiScreen.isShiftKeyDown())
		{
			l.add((cap * 4) + "x " + new ItemStack(Items.stick).getDisplayName());
			l.add((int) Math.ceil(cap * 6D / 16D) + "x " + new ItemStack(Blocks.glass).getDisplayName());
			
			if(cap >= 8) l.add((cap / 8) + "x " + new ItemStack(Items.iron_ingot).getDisplayName());
			if(cap >= 64) l.add((cap / 64) + "x " + new ItemStack(Items.gold_ingot).getDisplayName());
			if(cap >= 512) l.add((cap / 512) + "x " + new ItemStack(Items.quartz).getDisplayName());
			if(cap >= 4096) l.add((cap / 4096) + "x " + new ItemStack(Items.diamond).getDisplayName());
			if(meta == 5) l.add("1x " + ItemMaterialsLB.DUST_STAR.getStack().getDisplayName());
		}
	}
}
