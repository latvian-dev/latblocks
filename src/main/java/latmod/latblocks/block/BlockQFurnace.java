package latmod.latblocks.block;
import latmod.core.ODItems;
import latmod.core.client.LatCoreMCClient;
import latmod.core.tile.TileLM;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import cpw.mods.fml.relauncher.*;

public class BlockQFurnace extends BlockLB
{
	@SideOnly(Side.CLIENT)
	public IIcon iconOn, iconOff;
	
	public BlockQFurnace(String s)
	{
		super(s, Material.rock);
		setHardness(1.2F);
		setBlockTextureName("furnSide");
		isBlockContainer = true;
		LatBlocks.mod.addTile(TileQFurnace.class, s);
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "QDQ", "QFQ", "QCQ",
				'Q', Blocks.quartz_block,
				'F', Blocks.furnace,
				'D', ODItems.DIAMOND,
				'C', Blocks.chest);
	}
	
	public TileLM createNewTileEntity(World w, int m)
	{ return new TileQFurnace(); }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int s, int m)
	{
		if(s == LatCoreMCClient.FRONT)
			return iconOff;
		return blockIcon;
	}
	
	public int damageDropped(int i)
	{ return 0; }
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		blockIcon = ir.registerIcon(mod.assets + "furnSide");
		iconOn = ir.registerIcon(mod.assets + "furnOn");
		iconOff = ir.registerIcon(mod.assets + "furnOff");
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iba, int x, int y, int z, int s)
	{
		if(s == iba.getBlockMetadata(x, y, z))
		{
			TileQFurnace t = (TileQFurnace)iba.getTileEntity(x, y, z);
			
			if(t != null && t.isValid() && t.isLit())
				return iconOn;
			
			return iconOff;
		}
		return blockIcon;
	}
}