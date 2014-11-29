package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.tile.TileWaterTank;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockWaterTank extends BlockLB
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_on;
	
	public BlockWaterTank(String s)
	{
		super(s, Material.iron);
		isBlockContainer = true;
		mod.addTile(TileWaterTank.class, s);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "WWW", "WGW", "WWW",
				'W', Items.water_bucket,
				'G', ODItems.GLASS);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileWaterTank(); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "tank_water");
		icon_on = ir.registerIcon(mod.assets + "tank_water_on");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return (m == 0) ? blockIcon : icon_on; }
	
	public int damageDropped(int i)
	{ return 0; }
}