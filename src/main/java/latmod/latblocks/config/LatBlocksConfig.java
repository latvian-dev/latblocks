package latmod.latblocks.config;
import java.io.File;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigListRegistry;
import latmod.lib.config.ConfigFile;

public class LatBlocksConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("latblocks", new File(FTBLib.folderConfig, "LatBlocks.json"), true);
		LatBlocksConfigGeneral.load(configFile);
		LatBlocksConfigCrafting.load(configFile);
		ConfigListRegistry.instance.add(configFile);
		configFile.load();
	}
}