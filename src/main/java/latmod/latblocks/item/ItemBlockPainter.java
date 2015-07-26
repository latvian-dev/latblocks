package latmod.latblocks.item;
import latmod.ftbu.core.tile.IPaintable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockPainter extends ItemLB implements IPaintable.IPainterItem
{
	public ItemBlockPainter(String s)
	{
		super(s);
		setMaxStackSize(1);
		setMaxDamage(128);
		setFull3D();
	}
	
	public void loadRecipes()
	{
		mod.recipes.addShapelessRecipe(new ItemStack(this), ItemMaterialsLB.PAINT_ROLLER_ROD.stack, ItemMaterialsLB.PAINT_ROLLER.stack);
	}
	
	public ItemStack getPaintItem(ItemStack is)
	{ return IPaintable.Helper.getPaintItem(is); }
	
	public boolean canPaintBlock(ItemStack is)
	{ return is.getItemDamage() <= getMaxDamage(); }
	
	public void damagePainter(ItemStack is, EntityPlayer ep)
	{ is.damageItem(1, ep); }
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{ return IPaintable.Helper.onItemRightClick(this, is, w, ep); }
	
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{ return IPaintable.Helper.onItemUse(this, is, ep, w, x, y, z, s, x1, y1, z1); }
}