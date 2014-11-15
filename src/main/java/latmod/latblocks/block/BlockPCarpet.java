package latmod.latblocks.block;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.client.render.RenderCarpet;
import latmod.latblocks.tile.TilePCarpet;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockPCarpet extends BlockLB
{
	public BlockPCarpet(String s)
	{
		super(s, Material.cloth);
		setHardness(0.3F);
		isBlockContainer = true;
		setBlockTextureName("paintable");
		setBlockBounds(0F, 0F, 0F, 1F, 0.0625F, 1F);
		mod.addTile(TilePCarpet.class, s);
	}
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		LatCoreMC.addOreDictionary(ODItems.PAINTABLE_COVER_ANY, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this, 3), "PPP",
				'P', ODItems.PAINTABLE_COVER);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TilePCarpet(); }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderCarpet.instance.getRenderId(); }
}