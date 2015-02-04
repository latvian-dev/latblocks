package latmod.latblocks.block.tank;
import java.util.ArrayList;

import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.*;
import latmod.latblocks.tile.tank.TileTank;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.*;

public class BlockTank extends BlockTankBase
{
	public static ItemStack TANK_BASIC = new ItemStack(Blocks.glass, 1, 0);
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public BlockTank(String s)
	{
		super(s);
		LatBlocks.mod.addTile(TileTank.class, s);
	}
	
	public void onPostLoaded()
	{
		addAllDamages(6);
		TANK_BASIC = new ItemStack(this, 1, 0);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this, 1, 0), "SGS", "G G", "SGS",
				'G', ODItems.GLASS,
				'S', ODItems.STICK);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 1), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 0),
				'I', ODItems.QUARTZ);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 2), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 1),
				'I', ODItems.IRON);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 3), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 2),
				'I', ODItems.GOLD);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 4), "TTT", "TIT", "TTT",
				'T', new ItemStack(this, 1, 3),
				'I', ODItems.DIAMOND);
		
		mod.recipes.addRecipe(new ItemStack(this, 1, 5), "TET", "NGN", "TET",
				'T', new ItemStack(this, 1, 4),
				'N', Items.nether_star,
				'E', Items.ender_pearl,
				'G', LatBlocksItems.b_tank_void);
		
		LatBlocksItems.i_hammer.addRecipe(new ItemStack(Items.nether_star, 2), new ItemStack(this, 1, 5));
	}
	
	public int damageDropped(int i)
	{ return i; }
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileTank(m); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[6];
		for(int i = 0; i < icons.length; i++)
			icons[i] = ir.registerIcon(mod.assets + "tank/outside_" + i);
	}
	
	public ArrayList<ItemStack> getDrops(World w, int x, int y, int z, int m, int f)
	{ return new ArrayList<ItemStack>(); }
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		int meta = is.getItemDamage();
		
		if(meta == 5)
		{
			l.add("Capacity: Endless");
			return;
		}
		
		int c = 1;
		if(meta == 1) c = 8;
		else if(meta == 2) c = 64;
		else if(meta == 3) c = 512;
		else if(meta == 4) c = 4096;
		
		l.add("Capacity: " + c + MathHelperLM.getPluralWord(c, " bucket", " buckets"));
		
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("Fluid"))
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(is.stackTagCompound.getCompoundTag("Fluid"));
			l.add("Stored: " + fs.amount + "mB of " + fs.getLocalizedName());
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(int m)
	{ return icons[m]; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemFluidIcon(int m)
	{ return null; }
}