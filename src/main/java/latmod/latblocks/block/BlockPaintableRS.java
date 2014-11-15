package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.tile.TilePaintableRS;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockPaintableRS extends BlockPaintable
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_on;
	
	public BlockPaintableRS(String s)
	{
		super(s);
		setBlockTextureName("paintableRS");
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this), " P ", "PRP", " P ",
				'P', ODItems.PAINTABLE_COVER,
				'R', Blocks.redstone_block);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePaintableRS(); }
	
	public boolean canConnectRedstone(IBlockAccess iba, int x, int y, int z, int side)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		super.registerBlockIcons(ir);
		icon_on = ir.registerIcon(mod.assets + getTextureName() + "_on");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return m == 1 ? icon_on : blockIcon; }
}