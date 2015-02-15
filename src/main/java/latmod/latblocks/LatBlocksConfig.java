package latmod.latblocks;
import latmod.core.LMConfig;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LatBlocksConfig extends LMConfig
{
	public LatBlocksConfig(FMLPreInitializationEvent e)
	{ super(e, "/LatMod/LatBlocks.cfg"); }
	
	public void load()
	{
		General.load(get("general"));
	}
	
	public static class General
	{
		public static boolean fencesIgnorePlayers;
		public static boolean tankCraftingHandler;
		
		public static void load(Category c)
		{
			fencesIgnorePlayers = c.getBool("fencesIgnorePlayers", true);
			tankCraftingHandler = c.getBool("tankCraftingHandler", true);
		}
	}
}