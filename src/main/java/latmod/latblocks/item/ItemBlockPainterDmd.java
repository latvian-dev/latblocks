package latmod.latblocks.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockPainterDmd extends ItemBlockPainter
{
	public ItemBlockPainterDmd(String s)
	{
		super(s);
		setMaxDamage(0);
	}
	
	public void loadRecipes()
	{
		getMod().recipes.addShapelessRecipe(new ItemStack(this), ItemMaterialsLB.PAINT_ROLLER_ROD, ItemMaterialsLB.PAINT_ROLLER_DMD);
	}
	
	public boolean canPaintBlock(ItemStack is)
	{ return true; }
	
	public void damagePainter(ItemStack is, EntityPlayer ep) { }
}