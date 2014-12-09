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
		Client.load(get("client"));
	}
	
	public static class General
	{
		public static boolean fencesIgnorePlayers;
		
		public static void load(Category c)
		{
			fencesIgnorePlayers = c.getBool("fencesIgnorePlayers", true);
		}
	}
	
	public static class Client
	{
		public static boolean renderBoxes;
		public static boolean rotateInvBlocks;
		
		public static void load(Category c)
		{
			renderBoxes = c.getBool("renderBoxes", true);
			rotateInvBlocks = c.getBool("rotateInvBlocks", true);
		}
	}
}