package latmod.latblocks.block.blocks;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.block.BlockPaintableSided;
import latmod.latblocks.tile.blocks.TilePaintableRS;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockPaintableRS extends BlockPaintableSided
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
				'P', LatBlocksItems.b_cover,
				'R', Blocks.redstone_block);
		
		mod.recipes().addShapelessRecipe(new ItemStack(LatBlocksItems.b_paintable), this);
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