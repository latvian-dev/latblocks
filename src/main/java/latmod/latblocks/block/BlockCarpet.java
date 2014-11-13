package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.client.render.RenderCarpet;
import latmod.latblocks.tile.TileCarpet;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockCarpet extends BlockLB
{
	public BlockCarpet(String s)
	{
		super(s, Material.cloth);
		setHardness(0.3F);
		isBlockContainer = true;
		setBlockTextureName("paintable");
		setBlockBounds(0F, 0F, 0F, 1F, 0.0625F, 1F);
		mod.addTile(TileCarpet.class, s);
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this, 3), "PPP",
				'P', ODItems.FACADE_PAINTABLE);
		
		mod.recipes().addShapelessRecipe(new ItemStack(LatBlocksItems.b_facade), this);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileCarpet(); }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderCarpet.instance.getRenderId(); }
}