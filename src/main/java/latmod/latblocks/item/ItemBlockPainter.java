package latmod.latblocks.item;
import latmod.core.*;
import latmod.core.tile.IPaintable;
import latmod.latblocks.LatBlocksItems;
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
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		LatCoreMC.addOreDictionary(ODItems.TOOL_PAINTER_ANY, new ItemStack(this, 1, LatCoreMC.ANY));
		
		if(this == LatBlocksItems.i_painter)
			LatCoreMC.addOreDictionary(ODItems.TOOL_PAINTER, new ItemStack(this, 1, LatCoreMC.ANY));
	}
	
	public void loadRecipes()
	{
		addRecipe(new ItemStack(this), "SCS", "SPS", " P ",
				'S', ODItems.STICK,
				'C', ODItems.PAINTABLE_BLOCK,
				'P', ODItems.IRON);
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