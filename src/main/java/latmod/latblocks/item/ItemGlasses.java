package latmod.latblocks.item;
import latmod.core.*;
import latmod.core.item.IItemLM;
import latmod.core.mod.LC;
import latmod.latblocks.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class ItemGlasses extends ItemArmor implements IItemLM
{
	public static boolean hasClientPlayer()
	{ return hasPlayer(LC.proxy.getClientPlayer()); }
	
	public static boolean hasPlayer(EntityPlayer ep)
	{
		return (ep == null) ? false : (ep.inventory.armorInventory[3] != null
		&& ep.inventory.armorInventory[3].getItem() == LatBlocksItems.i_glasses);
	}
	
	public static void spawnInvParticles(World w, double x, double y, double z, int q)
	{
		if(hasClientPlayer())
		{
			double s = 0.25D;
			for(int i = 0; i < q; i++)
			{
				double x1 = x + MathHelperLM.randomDouble(ParticleHelper.rand, -s, s);
				double y1 = y + MathHelperLM.randomDouble(ParticleHelper.rand, -s, s);
				double z1 = z + MathHelperLM.randomDouble(ParticleHelper.rand, -s, s);
				LC.proxy.spawnDust(w, x1, y1, z1, 0xC200FFFF);
			}
		}
	}
	
	public final String itemName;
	
	public ItemGlasses(String s)
	{
		super(ArmorMaterial.GOLD, 4, 0);
		itemName = s;
	}
	
	public ItemGlasses register()
	{ LatBlocks.mod.addItem(this); return this; }
	
	public String getUnlocalizedName(ItemStack is)
	{ return LatBlocks.mod.getItemName(itemName); }
	
	public void loadRecipes()
	{
		LatBlocks.mod.recipes.addRecipe(new ItemStack(this), "CCC", "LIL",
				'C', Items.leather,
				'L', ItemMaterialsLB.LENS,
				'I', ODItems.IRON);
	}
	
	public boolean isValidArmor(ItemStack is, int i, Entity e)
	{ return i == 0; }
	
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    { return LatBlocks.mod.assets + "textures/items/glassesArmor.png"; }
	
	public Item getItem()
	{ return this; }
	
	public String getItemID()
	{ return itemName; }
	
	public void onPostLoaded()
	{
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		super.registerIcons(ir);
		itemIcon = ir.registerIcon(LatBlocks.mod.assets + itemName);
	}
}