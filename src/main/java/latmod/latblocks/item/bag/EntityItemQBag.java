package latmod.latblocks.item.bag;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemQBag extends EntityItem
{
	public EntityItemQBag(World w)
	{ super(w); }
	
	public EntityItemQBag(World w, double x, double y, double z, ItemStack is)
	{
		super(w, x, y, z, is);
		delayBeforeCanPickup = 40;
	}
	
	public boolean isEntityInvulnerable()
	{ return true; }
	
	public boolean combineItems(EntityItem p_70289_1_)
	{ return false; }
	
	public void onUpdate()
	{
		age = Math.min(age, delayBeforeCanPickup + 1);
		super.onUpdate();
	}
}