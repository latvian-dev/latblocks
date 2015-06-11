package latmod.latblocks.block;
import latmod.ftbu.core.ODItems;
import latmod.ftbu.core.tile.TileLM;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocksConfig;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileQFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockQFurnace extends BlockLB
{
	@SideOnly(Side.CLIENT)
	public IIcon iconOn, iconOff;
	
	public BlockQFurnace(String s)
	{
		super(s, Material.rock);
		setHardness(1.2F);
		setBlockTextureName("furnSide");
		isBlockContainer = true;
		mod.addTile(TileQFurnace.class, s);
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfig.Crafting.chest)
		{
			mod.recipes.addRecipe(new ItemStack(this), "QDQ", "QFQ", "QSQ",
					'Q', Blocks.quartz_block,
					'F', Blocks.furnace,
					'D', ODItems.DIAMOND,
					'S', ItemMaterialsLB.STAR_DUST);
		}
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileQFurnace(); }
	
	public int damageDropped(int i)
	{ return 0; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "furn_side");
		iconOn = ir.registerIcon(mod.assets + "furn_on");
		iconOff = ir.registerIcon(mod.assets + "furn_off");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return (s == 3) ? iconOff : blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		if(s == iba.getBlockMetadata(x, y, z))
		{
			TileQFurnace t = (TileQFurnace)iba.getTileEntity(x, y, z);
			
			if(t != null && t.isValid() && t.isLit())
				return iconOn;
			
			return iconOff;
		}
		return blockIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(TileQFurnace.ITEM_TAG))
		{
			TileQFurnace t = new TileQFurnace();
			t.readTileData(is.stackTagCompound.getCompoundTag(TileQFurnace.ITEM_TAG));
			
			if(t.fuel > 0)
			{
				double fuel = (t.fuel / TileQFurnace.MAX_PROGRESS);
				fuel = ((int)(fuel * 10D)) / 10D;
				l.add("Fuel: " + fuel + " items");
			}
			
			if(t.progress > 0 && t.result != null)
			{
				int prog = (int)(t.progress * 100D / TileQFurnace.MAX_PROGRESS);
				l.add("Progress: " + prog + "% smelting " + t.result.getDisplayName());
			}
		}
	}
}