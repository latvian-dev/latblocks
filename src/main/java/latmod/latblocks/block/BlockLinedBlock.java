package latmod.latblocks.block;
import latmod.ftbu.core.client.LatCoreMCClient;
import latmod.latblocks.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class BlockLinedBlock extends BlockGlowium
{
	@SideOnly(Side.CLIENT)
	public IIcon icons[][][][];
	
	public BlockLinedBlock(String s)
	{ super(s, "lined"); }
	
	public void loadRecipes()
	{
		super.loadRecipes();
		
		if(LatBlocksConfig.Crafting.glowiumBlocks)
			mod.recipes.addRecipe(new ItemStack(this, 5, DEF_DMG), " G ", "GGG", " G ",
				'G', new ItemStack(LatBlocksItems.b_glowium[1], 1, DEF_DMG));
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "paintable");
		
		icons = new IIcon[2][2][2][2];
		
		for(int a = 0; a <= 1; a++) for(int b = 0; b <= 1; b++) for(int c = 0; c <= 1; c++) for(int d = 0; d <= 1; d++)
			icons[a][b][c][d] = ir.registerIcon(mod.assets + "glowium/lined/" + a + "" + b + "" + c + "" + d);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getGlowItemIcon()
	{ return icons[1][1][1][1]; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getGlowIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[s];
		if(isAt(iba, x + fd.offsetX, y + fd.offsetY, z + fd.offsetZ, iba.getBlockMetadata(x, y, z)) == 1)
			return LatCoreMCClient.blockNullIcon;
		
		int m = iba.getBlockMetadata(x, y, z);
		
		if(isBlockAlone(iba, x, y, z, m))
			return icons[0][0][0][0];
		
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		
		if(s == 0 || s == 1)
		{
			a = isAt(iba, x, y, z - 1, m);
			b = isAt(iba, x + 1, y, z, m);
			c = isAt(iba, x, y, z + 1, m);
			d = isAt(iba, x - 1, y, z, m);
		}
		else
		{
			a = isAt(iba, x, y + 1, z, m);
			c = isAt(iba, x, y - 1, z, m);
			
			if(s == Placement.D_SOUTH)
			{
				b = isAt(iba, x + 1, y, z, m);
				d = isAt(iba, x - 1, y, z, m);
			}
			else if(s == Placement.D_NORTH)
			{
				b = isAt(iba, x - 1, y, z, m);
				d = isAt(iba, x + 1, y, z, m);
			}
			else if(s == Placement.D_EAST)
			{
				b = isAt(iba, x, y, z - 1, m);
				d = isAt(iba, x, y, z + 1, m);
			}
			else
			{
				b = isAt(iba, x, y, z + 1, m);
				d = isAt(iba, x, y, z - 1, m);
			}
		}
		
		if(a == 0 && b == 0 && c == 0 && d == 0)
			return LatCoreMCClient.blockNullIcon;
		return icons[a][b][c][d];
	}
	
	private int isAt(IBlockAccess iba, int x, int y, int z, int m)
	{ return (iba.getBlock(x, y, z) == this && iba.getBlockMetadata(x, y, z) == m) ? 1 : 0; }
	
	private boolean isBlockAlone(IBlockAccess iba, int x, int y, int z, int m)
	{
		for(int i = 0; i < 6; i++)
		{
			if(isAt(iba, x + Facing.offsetsXForSide[i], y + Facing.offsetsYForSide[i], z + Facing.offsetsZForSide[i], m) == 1)
				return false;
		}
		
		return true;
	}
}