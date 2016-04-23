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
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addShapelessRecipe(new ItemStack(this), ItemMaterialsLB.PAINT_ROLLER_ROD, ItemMaterialsLB.PAINT_ROLLER_DMD);
	}
	
	@Override
	public boolean canPaintBlock(ItemStack is)
	{ return true; }
	
	@Override
	public void damagePainter(ItemStack is, EntityPlayer ep) { }
}