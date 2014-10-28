package latmod.latblocks;
import latmod.core.mod.LMMod;
import latmod.core.mod.recipes.LMRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = LatBlocks.MOD_ID, name = "LatBlocks", version = "@VERSION@", dependencies = "required-after:LatCoreMC")
public class LatBlocks
{
	protected static final String MOD_ID = "LatBlocks";
	
	@Mod.Instance(LatBlocks.MOD_ID)
	public static LatBlocks inst;
	
	@SidedProxy(clientSide = "latmod.latblocks.LatBlocksClient", serverSide = "latmod.latblocks.LatBlocksCommon")
	public static LatBlocksCommon proxy;
	
	public static LMMod<LatBlocksConfig, LMRecipes> mod;
	public static CreativeTabs tab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod<LatBlocksConfig, LMRecipes>(MOD_ID, new LatBlocksConfig(e), new LMRecipes(false));
		
		LatBlocksItems.init();
		mod.onPostLoaded();
		
		tab = mod.createTab("tab", new ItemStack(LatBlocksItems.i_painter_dmd));
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
		proxy.postInit();
	}
}