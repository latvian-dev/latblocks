package latmod.latblocks.block;
import java.util.ArrayList;

import latmod.ftbu.core.*;
import latmod.ftbu.core.tile.TileLM;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocksConfig;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockQChest extends BlockLB
{
	@SideOnly(Side.CLIENT)
	private IIcon chestFront, chestTop;
	
	public BlockQChest(String s)
	{
		super(s, Material.wood);
		float f = 1F / 16F;
		setBlockBounds(f, 0F, f, 1F - f, 0.875F, 1F - f);
		isBlockContainer = true;
		mod.addTile(TileQChest.class, s);
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfig.Crafting.chest)
		{
			mod.recipes.addRecipe(new ItemStack(this), "QDQ", "QFQ", "QSQ",
					'Q', Blocks.quartz_block,
					'F', Blocks.chest,
					'D', ODItems.DIAMOND,
					'S', ItemMaterialsLB.STAR_DUST);
		}
	}
	
	public int damageDropped(int i)
	{ return 0; }
	
	public TileLM createNewTileEntity(World world, int m)
	{ return new TileQChest(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		chestFront = ir.registerIcon(mod.assets + "chest_front");
		blockIcon = ir.registerIcon(mod.assets + "chest_side");
		chestTop = ir.registerIcon(mod.assets + "chest_top");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{
		if(s == 0 || s == 1) return chestTop;
		return (s == LatCoreMC.FRONT) ? chestFront : blockIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		if(s == 0 || s == 1) return chestTop;
		return (s == iba.getBlockMetadata(x, y, z)) ? chestFront : blockIcon;
	}
	
	public ArrayList<ItemStack> getDrops(World w, int x, int y, int z, int m, int f)
	{ return new ArrayList<ItemStack>(); }
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> al)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(TileQChest.ITEM_TAG))
		{
			TileQChest t = new TileQChest();
			t.readTileData(is.stackTagCompound.getCompoundTag(TileQChest.ITEM_TAG));
			
			if(is.hasDisplayName()) al.add(new ItemStack(is.getItem()).getDisplayName());
			
			int slotsUsed = 0;
			int items = 0;
			
			for(int i = 0; i < t.items.length; i++)
			{
				if(t.items[i] != null)
				{
					slotsUsed++;
					items += t.items[i].stackSize;
				}
			}
			
			al.add("Slots Used: " + slotsUsed + " / " + t.items.length);
			al.add("Items in chest: " + items);
		}
	}
}