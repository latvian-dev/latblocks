package latmod.latblocks.block;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.core.util.FastList;
import latmod.latblocks.*;
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
			
			if(LatBlocksConfig.Crafting.dataErase)
				LatBlocksItems.i_hammer.addRecipe(new ItemStack(this), new ItemStack(this, 1, ODItems.ANY));
		}
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileQFurnace(); }
	
	public int damageDropped(int i)
	{ return 0; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "furnSide");
		iconOn = ir.registerIcon(mod.assets + "furnOn");
		iconOff = ir.registerIcon(mod.assets + "furnOff");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{
		if(s == LatCoreMC.FRONT)
			return iconOff;
		return blockIcon;
	}
	
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
	
	public void harvestBlock(World w, EntityPlayer ep, int x, int y, int z, int m)
	{
	}
	
	public void onBlockHarvested(World w, int x, int y, int z, int m, EntityPlayer ep)
	{
		if (!(ep.capabilities.isCreativeMode))
		{
			dropBlockAsItem(w, x, y, z, m, 0);
			w.setBlock(x, y, z, Blocks.air, 0, 7);
		}
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