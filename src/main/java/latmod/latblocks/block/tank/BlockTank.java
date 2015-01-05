package latmod.latblocks.block.tank;
import latmod.core.*;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.tank.TileTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import cpw.mods.fml.relauncher.*;

public class BlockTank extends BlockTankBase
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_inside;
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;

	public BlockTank(String s)
	{
		super(s);
		LatBlocks.mod.addTile(TileTank.class, s);
	}
	
	public void onPostLoaded()
	{
		addAllDamages(5);
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
	}
	
	public int damageDropped(int i)
	{ return i; }
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileTank(m); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icon_inside = ir.registerIcon(mod.assets + "tank/inside");
		icons = new IIcon[5];
		for(int i = 0; i < icons.length; i++)
			icons[i] = ir.registerIcon(mod.assets + "tank/outside_" + i);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		int meta = is.getItemDamage();
		int c = 0;
		
		if(meta == 0) c = 1;
		else if(meta == 1) c = 8;
		else if(meta == 2) c = 64;
		else if(meta == 3) c = 512;
		else if(meta == 4) c = 4096;
		
		l.add("Capacity: " + c + MathHelper.getPluralWord(c, " bucket", " buckets"));
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(int m)
	{ return icons[m]; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemFluidIcon(int m)
	{
		Fluid[] fluids = FluidRegistry.getRegisteredFluids().values().toArray(new Fluid[0]);
		int id = (m + (int)(Minecraft.getSystemTime() / 1000L)) % fluids.length;
		return fluids[id].getStillIcon();
	}
}