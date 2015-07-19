package latmod.latblocks.block;
import latmod.ftbu.core.client.LatCoreMCClient;
import latmod.ftbu.core.inv.ODItems;
import latmod.ftbu.core.tile.TileLM;
import latmod.ftbu.core.util.*;
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
	
	public static final TileQChest tempTile = new TileQChest();
	
	public BlockQChest(String s)
	{
		super(s, Material.wood);
		float f = 1F / 16F;
		setBlockBounds(f, 0F, f, 1F - f, 0.875F, 1F - f);
		isBlockContainer = true;
		mod.addTile(TileQChest.class, s);
	}
	
	public TileLM createNewTileEntity(World world, int m)
	{ return new TileQChest(); }
	
	public void loadRecipes()
	{
		if(LatBlocksConfig.Crafting.chest)
		{
			mod.recipes.addRecipe(new ItemStack(this), "QDQ", "QFQ", "QSQ",
					'Q', Blocks.quartz_block,
					'F', Blocks.chest,
					'D', ODItems.DIAMOND,
					'S', ItemMaterialsLB.DUSTS_GLOWIUM[4]);
		}
	}
	
	public int damageDropped(int i)
	{ return 0; }
	
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
		return (s == 3) ? chestFront : blockIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{ return LatCoreMCClient.blockNullIcon; }
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(TileQChest.ITEM_TAG))
		{
			tempTile.readTileData(is.stackTagCompound.getCompoundTag(TileQChest.ITEM_TAG));
			
			if(is.hasDisplayName()) l.add(new ItemStack(is.getItem()).getDisplayName());
			
			int slotsUsed = 0;
			int items = 0;
			
			for(int i = 0; i < tempTile.items.length; i++)
			{
				if(tempTile.items[i] != null)
				{
					slotsUsed++;
					items += tempTile.items[i].stackSize;
				}
			}
			
			l.add("Slots Used: " + slotsUsed + " / " + tempTile.items.length);
			l.add("Items in chest: " + items);
			l.add("Title: " + tempTile.customName);
			l.add("Chest Color: " + LatCore.Colors.getHex(tempTile.colorChest));
			l.add("Text Color: " + LatCore.Colors.getHex(tempTile.colorText));
		}
	}
}