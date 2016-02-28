package latmod.latblocks;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import ftb.lib.*;
import latmod.latblocks.config.LatBlocksConfig;
import latmod.latblocks.net.LatBlocksNetHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

@Mod(modid = LatBlocks.MOD_ID, name = "LatBlocks", version = "@VERSION@", dependencies = "required-after:FTBU")
public class LatBlocks
{
	protected static final String MOD_ID = "LatBlocks";
	
	@Mod.Instance(LatBlocks.MOD_ID)
	public static LatBlocks inst;
	
	@SidedProxy(clientSide = "latmod.latblocks.client.LatBlocksClient", serverSide = "latmod.latblocks.LatBlocksCommon")
	public static LatBlocksCommon proxy;
	
	public static LMMod mod;
	public static CreativeTabs tab;
	public static LBGlowiumCreativeTab tabGlowium;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = LMMod.create(MOD_ID);
		LatBlocksConfig.load();
		LatBlocksItems.init();
		mod.onPostLoaded();
		tab = mod.createTab("tab", new ItemStack(LatBlocksItems.b_fountain));
		tabGlowium = new LBGlowiumCreativeTab();
		LatBlocksNetHandler.init();
		EventBusHelper.register(new LatBlockEventHandler());
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		tabGlowium.init();
		mod.loadRecipes();
		proxy.postInit();
	}
}