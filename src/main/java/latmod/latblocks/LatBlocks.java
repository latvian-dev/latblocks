package latmod.latblocks;
import java.util.List;

import latmod.core.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;

@Mod(modid = LatBlocks.MOD_ID, name = "LatBlocks", version = "@VERSION@", dependencies = "required-after:LatCoreMC")
public class LatBlocks
{
	protected static final String MOD_ID = "LatBlocks";
	
	@Mod.Instance(LatBlocks.MOD_ID)
	public static LatBlocks inst;
	
	@SidedProxy(clientSide = "latmod.latblocks.client.LatBlocksClient", serverSide = "latmod.core.LMProxy")
	public static LMProxy proxy;
	
	public static LMMod mod;
	public static CreativeTabs tab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(MOD_ID, new LatBlocksConfig(e), null);
		
		LatBlocksItems.init();
		mod.onPostLoaded();
		
		tab = mod.createTab("tab", new ItemStack(LatBlocksItems.b_fountain));
		
		proxy.preInit(e);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
		mod.loadRecipes();
	}
	
	@Mod.EventHandler
	public void missingMapping(FMLMissingMappingsEvent e)
	{
		List<MissingMapping> l = e.getAll();
		
		for(int i = 0; i < l.size(); i++)
		{
			MissingMapping m = l.get(i);
			
			LatCoreMC.remap(m, "LatCoreMC:blockPainter", LatBlocksItems.i_painter);
			LatCoreMC.remap(m, "LatCoreMC:blockPainterDmd", LatBlocksItems.i_painter_dmd);
			
			LatCoreMC.remap(m, "LatCoreMC:paintable", LatBlocksItems.b_paintable);
			LatCoreMC.remap(m, "LatCoreMC:facade", LatBlocksItems.b_cover);
			LatCoreMC.remap(m, "LatBlocks:facade", LatBlocksItems.b_cover);
			LatCoreMC.remap(m, "LatCoreMC:paintableRS", LatBlocksItems.b_paintable_rs);
			
			LatCoreMC.remap(m, "Elemite:waterTank", LatBlocksItems.b_tank_water);
			LatCoreMC.remap(m, "Elemite:voidTank", LatBlocksItems.b_tank_void);
		}
	}
}