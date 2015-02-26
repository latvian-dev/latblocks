package latmod.latblocks.block;
import latmod.latblocks.block.paintable.BlockPaintableDef;
import latmod.latblocks.client.render.world.RenderPaintable;
import latmod.latblocks.tile.*;
import net.minecraft.entity.Entity;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public abstract class BlockPaintableSided extends BlockPaintableLB
{
	public BlockPaintableSided(String s)
	{ super(s); }
	
	public void registerTiles()
	{
		mod.addTile(createNewTileEntity(null, 0).getClass(), blockName);
	}
	
	public int getLightOpacity()
	{ return 0; }
	
	public TilePaintableLB createNewTileEntity(World w, int m)
	{ return new BlockPaintableDef.TilePaintableDef(); }
	
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
		
		TileSidedPaintable t = (TileSidedPaintable) w.getTileEntity(x, y, z);
		
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