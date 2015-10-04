package latmod.latblocks.block;
import cpw.mods.fml.relauncher.*;
import latmod.ftbu.inv.ODItems;
import latmod.ftbu.tile.TileLM;
import latmod.ftbu.util.MathHelperMC;
import latmod.latblocks.config.LatBlocksConfigCrafting;
import latmod.latblocks.item.ItemMaterialsLB;
import latmod.latblocks.tile.TileQTerminal;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlockQTerminal extends BlockLB
{
	@SideOnly(Side.CLIENT)
	public IIcon icon_front;
	
	public BlockQTerminal(String s)
	{
		super(s, Material.rock);
		setHardness(1.2F);
		isBlockContainer = true;
		mod.addTile(TileQTerminal.class, s);
	}
	
	public void loadRecipes()
	{
		if(LatBlocksConfigCrafting.qNetBlocks.get())
		{
			mod.recipes.addRecipe(new ItemStack(this), "QDQ", "QSQ", "QDQ",
					'Q', Blocks.quartz_block,
					'D', ODItems.DIAMOND,
					'D', ItemMaterialsLB.DUST_GLOWIUM_D,
					'S', ItemMaterialsLB.DUST_STAR);
		}
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileQTerminal(); }
	
	public int damageDropped(int i)
	{ return 0; }
	
	public int onBlockPlaced(World w, EntityPlayer ep, MovingObjectPosition mop, int m)
	{ return MathHelperMC.get3DRotation(w, mop.blockX, mop.blockY, mop.blockZ, ep); }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "terminal_side");
		icon_front = ir.registerIcon(mod.assets + "terminal_front");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{ return (s == 3) ? icon_front : blockIcon; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		if(s == iba.getBlockMetadata(x, y, z))
			return icon_front;
		return blockIcon;
	}
}