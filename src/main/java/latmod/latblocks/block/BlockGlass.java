package latmod.latblocks.block;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.api.paint.*;
import latmod.ftbu.inv.ODItems;
import latmod.ftbu.tile.TileLM;
import latmod.ftbu.util.client.LatCoreMCClient;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGlass extends BlockLB implements ICustomPaintBlockIcon
{
	@SideOnly(Side.CLIENT)
	public IIcon icons[][][][];
	
	public BlockGlass(String s)
	{
		super(s, Material.glass);
		setHardness(0.2F);
		setBlockTextureName("invGlass");
		isBlockContainer = false;
		setStepSound(soundTypeGlass);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 8), "GGG", "GPG", "GGG",
				'G', ODItems.GLASS,
				'P', new ItemStack(Items.potionitem, 1, 8206));
	}
	
	public void onPostLoaded()
	{
		super.onPostLoaded();
		
		ODItems.add(ODItems.GLASS, new ItemStack(this, 1, ODItems.ANY));
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return null; }
	
	public boolean canHarvestBlock(EntityPlayer ep, int m)
	{ return true; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getCustomPaintIcon(int side, Paint p)
	{ return LatCoreMCClient.blockNullIcon; }
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{ return 1; }
	
	public boolean isOpaqueCube()
	{ return false; }
	
	public boolean renderAsNormalBlock()
	{ return false; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[2][2][2][2];
		
		for(int a = 0; a <= 1; a++) for(int b = 0; b <= 1; b++) for(int c = 0; c <= 1; c++) for(int d = 0; d <= 1; d++)
			icons[a][b][c][d] = ir.registerIcon(mod.assets + "glass/inv/" + a + "" + b + "" + c + "" + d);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return icons[0][0][0][0]; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		//int m = iba.getBlockMetadata(x, y, z);
		
		ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[s];
		if(isAt(iba, x + fd.offsetX, y + fd.offsetY, z + fd.offsetZ, iba.getBlockMetadata(x, y, z)) == 1)
			return LatCoreMCClient.blockNullIcon;
		
		int m = 0;
		
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
		
		return icons[a][b][c][d];
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int s)
    {
		Block b1 = iba.getBlock(x, y, z);
		if(b1 == this) return false;
		return b1 == Blocks.air || !b1.renderAsNormalBlock() || !b1.isOpaqueCube();
	}
	
	private int isAt(IBlockAccess iba, int x, int y, int z, int m)
	{ return (iba.getBlock(x, y, z) == this) ? 1 : 0; }
}