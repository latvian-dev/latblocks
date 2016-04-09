package latmod.latblocks;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import ftb.lib.*;
import latmod.latblocks.block.BlockGlowium;
import latmod.latblocks.config.LatBlocksConfig;
import latmod.latblocks.net.LatBlocksNetHandler;
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
	public static CreativeTabLM tab;
	public static CreativeTabLM tabGlowium;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = LMMod.create(MOD_ID);
		tab = new CreativeTabLM("latblocks").setMod(mod);
		tabGlowium = new CreativeTabLM("latblocks.glowium").setMod(mod).setTimer(1000L);
		
		LatBlocksConfig.load();
		LatBlocksItems.init();
		mod.onPostLoaded();
		LatBlocksNetHandler.init();
		EventBusHelper.register(new LatBlockEventHandler());
		proxy.preInit();
		
		tab.addIcon(new ItemStack(LatBlocksItems.b_fountain));
		
		for(int j = 0; j < 16; j++)
			for(BlockGlowium b : LatBlocksItems.b_glowium)
				tabGlowium.addIcon(new ItemStack(b, 1, j));
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
		proxy.postInit();
	}
}