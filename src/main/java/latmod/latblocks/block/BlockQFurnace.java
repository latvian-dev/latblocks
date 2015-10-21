package latmod.latblocks.block;
import cpw.mods.fml.relauncher.*;
import ftb.lib.MathHelperMC;
import ftb.lib.item.ODItems;
import latmod.ftbu.tile.TileLM;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileQFurnace;
import latmod.lib.FastList;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockQFurnace extends BlockLB
{
	@SideOnly(Side.CLIENT)
	public IIcon iconOn, iconOff;
	
	private static final TileQFurnace tempFurn = new TileQFurnace();
	
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
		if(LatBlocksConfigCrafting.chest.get())
		{
			mod.recipes.addRecipe(new ItemStack(this), "QDQ", "QFQ", "QSQ",
					'Q', Blocks.quartz_block,
					'F', Blocks.furnace,
					'D', ODItems.DIAMOND,
					'S', ItemMaterialsLB.DUST_GLOWIUM_D);
		}
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileQFurnace(); }
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return MathHelperMC.get2DRotation(ep); }
	
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
	{ return (s == 3) ? ((m == 100) ? iconOn : iconOff) : blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		if(s == iba.getBlockMetadata(x, y, z))
		{
			TileQFurnace t = getTile(TileQFurnace.class, iba, x, y, z);
			return (t != null && t.isLit()) ? iconOn : iconOff;
		}
		return blockIcon;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(TileQFurnace.ITEM_TAG))
		{
			tempFurn.readTileData(is.stackTagCompound.getCompoundTag(TileQFurnace.ITEM_TAG));
			
			if(tempFurn.fuel > 0)
			{
				double fuel = (tempFurn.fuel / TileQFurnace.MAX_PROGRESS);
				fuel = ((int)(fuel * 10D)) / 10D;
				l.add("Fuel: " + fuel + " items");
			}
			
			if(tempFurn.progress > 0 && tempFurn.result != null)
			{
				int prog = (int)(tempFurn.progress * 100D / TileQFurnace.MAX_PROGRESS);
				l.add("Progress: " + prog + "% smelting " + tempFurn.result.getDisplayName());
			}
		}
	}
}