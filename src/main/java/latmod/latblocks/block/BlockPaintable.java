package latmod.latblocks.block;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.client.render.RenderPaintable;
import latmod.latblocks.tile.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockPaintable extends BlockLB
{
	public BlockPaintable(String s)
	{
		super(s, Material.rock);
		setHardness(0.3F);
		setBlockTextureName("paintable");
		isBlockContainer = true;
		
		mod.addTile(createNewTileEntity(null, 0).getClass(), s);
	}
	
	public int getLightOpacity()
	{ return 0; }
	
	public boolean canHarvestBlock(EntityPlayer ep, int meta)
	{ return true; }
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		LatCoreMC.addOreDictionary(ODItems.PAINTABLE_BLOCK_ANY, new ItemStack(this));
		
		if(this == LatBlocksItems.b_paintable)
			LatCoreMC.addOreDictionary(ODItems.PAINTABLE_BLOCK, new ItemStack(this));
	}
	
	public void loadRecipes()
	{
		mod.recipes().addRecipe(new ItemStack(this), "PP", "PP",
				'P', ODItems.PAINTABLE_COVER);
		
		mod.recipes().addRecipe(new ItemStack(this, 16), "WWW", "WPW", "WWW",
				'W', new ItemStack(Blocks.wool, 1, LatCoreMC.ANY),
				'P', ODItems.PLANKS);
		
		mod.recipes().addShapelessRecipe(new ItemStack(this), ODItems.PAINTABLE_BLOCK_ANY);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileDefPaintable(); }
	
	@SideOnly(Side.CLIENT)
	public int getRenderType()
	{ return RenderPaintable.instance.getRenderId(); }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return true; }
	
	public boolean isSideSolid(IBlockAccess iba, int x, int y, int z, ForgeDirection side)
	{ return true; }
	
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
	{ return true; }
	
	public float getExplosionResistance(Entity e, World w, int x, int y, int z, double ex, double ey, double ez)
	{
		float f = getExplosionResistance(e);
		
		TilePaintable t = (TilePaintable) w.getTileEntity(x, y, z);
		
		if(t != null && t.isValid())
		{
			for(int i = 0; i < t.paint.length; i++)
			{
				if(t.paint[i] != null)
				{
					float f1 = t.paint[i].block.getExplosionResistance(e);
					if(f1 > f) f = f1;
				}
			}
		}
		
		return f;
	}
}