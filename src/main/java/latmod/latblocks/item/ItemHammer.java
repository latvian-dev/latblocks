package latmod.latblocks.item;

import com.google.common.collect.Multimap;
import ftb.lib.api.item.ODItems;
import ftb.lib.api.item.Tool;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemHammer extends ItemLB
{
	public ItemHammer(String s)
	{
		super(s);
		setMaxStackSize(1);
		setFull3D();
		setMaxDamage(0);
		setHarvestLevel(Tool.Type.WRENCH, Tool.Level.BASIC);
		setHarvestLevel(Tool.Type.PICK, Tool.Level.IRON);
	}
	
	@Override
	public float func_150893_a(ItemStack is, Block b)
	{ return 3F; }
	
	@Override
	public boolean func_150897_b(Block b)
	{ return Items.iron_pickaxe.func_150897_b(b); }
	
	@Override
	public void loadRecipes()
	{
		if(LatBlocksConfigCrafting.hammer.getAsInt() > 0)
			getMod().recipes.addRecipe(new ItemStack(this), "OGO", " I ", " I ", 'I', ODItems.IRON, 'O', ODItems.OBSIDIAN, 'G', (LatBlocksConfigCrafting.hammer.getAsInt() == 1 ? ItemMaterialsLB.GEM_GLOWIUM_D : ODItems.DIAMOND));
	}
	
	@Override
	public int getDamage(ItemStack is)
	{ return 0; }
	
	@Override
	public ItemStack getContainerItem(ItemStack is)
	{ return new ItemStack(this, 1, 0); }
	
	@Override
	public boolean hasContainerItem(ItemStack is)
	{ return true; }
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack is)
	{ return false; }
	
	public void addRecipe(ItemStack out, Object in, Object... extra)
	{
		List<Object> l = new ArrayList<>();
		l.add(new ItemStack(this, 1, ODItems.ANY));
		l.add(in);
		Collections.addAll(l, extra);
		getMod().recipes.addShapelessRecipe(out, l.toArray());
	}
	
	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{ return true; }
	
	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep, World w, int x, int y, int z, int s, float x1, float y1, float z1)
	{ return false; }
	
	@SuppressWarnings("all")
	public Multimap getAttributeModifiers(ItemStack is)
	{
		Multimap m = super.getAttributeModifiers(is);
		m.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 5D, 0));
		return m;
	}
}