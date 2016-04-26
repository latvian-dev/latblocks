package latmod.latblocks.block.tank;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.item.ODItems;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.latblocks.config.LatBlocksConfigGeneral;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.tank.TileTank;
import latmod.lib.MathHelperLM;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.RecipeSorter;

import java.util.List;

public class BlockTank extends BlockTankBase
{
	public static ItemStack TANK_BASIC = new ItemStack(Blocks.glass, 1, 0);
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	public BlockTank(String s)
	{ super(s); }
	
	@Override
	public Class<? extends ItemBlock> getItemBlock()
	{ return ItemBlockTank.class; }
	
	@Override
	public boolean hasTileEntity(int meta)
	{ return true; }
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{ return new TileTank(metadata); }
	
	@Override
	public void onPostLoaded()
	{
		super.onPostLoaded();
		getMod().addTile(TileTank.class, blockName);
		TANK_BASIC = new ItemStack(this, 1, 0);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs c, List l)
	{
		for(int i = 0; i < 6; i++)
		{
			l.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void loadRecipes()
	{
		if(LatBlocksConfigGeneral.tank_crafting_handler.getAsBoolean())
		{
			RecipeSorter.register("latblocks:tanks", TankCraftingHandler.class, RecipeSorter.Category.SHAPED, "before:minecraft:shaped");
			TankCraftingHandler.register(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0), new ItemStack(Items.iron_ingot));
			TankCraftingHandler.register(new ItemStack(this, 1, 2), new ItemStack(this, 1, 1), new ItemStack(Items.gold_ingot));
			TankCraftingHandler.register(new ItemStack(this, 1, 3), new ItemStack(this, 1, 2), new ItemStack(Items.quartz));
			TankCraftingHandler.register(new ItemStack(this, 1, 4), new ItemStack(this, 1, 3), new ItemStack(Items.diamond));
			
			if(LatBlocksConfigCrafting.endlessTank.getAsBoolean())
				TankCraftingHandler.register(new ItemStack(this, 1, 5), new ItemStack(this, 1, 4), ItemMaterialsLB.DUST_STAR.getStack());
		}
		
		getMod().recipes.addRecipe(new ItemStack(this, 1, 0), " S ", "SGS", " S ", 'G', ODItems.GLASS_PANE_ANY, 'S', ODItems.STICK);
		
		getMod().recipes.addRecipe(new ItemStack(this, 1, 1), "TTT", "TIT", "TTT", 'T', new ItemStack(this, 1, 0), 'I', ODItems.IRON);
		
		getMod().recipes.addRecipe(new ItemStack(this, 1, 2), "TTT", "TIT", "TTT", 'T', new ItemStack(this, 1, 1), 'I', ODItems.GOLD);
		
		getMod().recipes.addRecipe(new ItemStack(this, 1, 3), "TTT", "TIT", "TTT", 'T', new ItemStack(this, 1, 2), 'I', ODItems.QUARTZ);
		
		getMod().recipes.addRecipe(new ItemStack(this, 1, 4), "TTT", "TIT", "TTT", 'T', new ItemStack(this, 1, 3), 'I', ODItems.DIAMOND);
		
		if(LatBlocksConfigCrafting.endlessTank.getAsBoolean())
			getMod().recipes.addRecipe(new ItemStack(this, 1, 5), "TTT", "TIT", "TTT", 'T', new ItemStack(this, 1, 4), 'I', ItemMaterialsLB.DUST_STAR);
	}
	
	@Override
	public int damageDropped(int i)
	{ return i; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		icons = new IIcon[6];
		for(int i = 0; i < icons.length; i++)
			icons[i] = ir.registerIcon(getMod().lowerCaseModID + ":tank/outside_" + i);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTankItemBorderIcon(ItemStack item)
	{ return icons[item.getItemDamage()]; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public FluidStack getTankItemFluid(ItemStack item)
	{
		if(item.hasTagCompound() && item.stackTagCompound.hasKey("Fluid"))
		{
			FluidStack fs = FluidStack.loadFluidStackFromNBT(item.stackTagCompound.getCompoundTag("Fluid"));
			
			int meta = item.getItemDamage();
			
			double c = 1000D;
			if(meta == 1) c = 8000D;
			else if(meta == 2) c = 64000D;
			else if(meta == 3) c = 512000D;
			else if(meta == 4) c = 4096000D;
			else if(meta == 5) c = fs.amount;
			
			fs.amount = (int) MathHelperLM.map(fs.amount, 0D, c, 0D, 1000D);
			return fs;
		}
		
		return null;
	}
}