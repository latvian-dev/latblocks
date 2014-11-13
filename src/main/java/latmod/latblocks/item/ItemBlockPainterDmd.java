package latmod.latblocks.item;
import latmod.core.ODItems;
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
		addRecipe(new ItemStack(this), "DDD", "DPD", "DDD",
				'P', ODItems.TOOL_PAINTER,
				'D', ODItems.DIAMOND);
	}
	
	public boolean canPaintBlock(ItemStack is)
	{ return true; }
	
	public void damagePainter(ItemStack is, EntityPlayer ep) { }
}