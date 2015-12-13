package latmod.latblocks.config;
import java.io.File;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigRegistry;
import latmod.lib.config.ConfigFile;

public class LatBlocksConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("latblocks", new File(FTBLib.folderConfig, "LatBlocks.json"));
		configFile.configGroup.setName("LatBlocks");
		configFile.add(LatBlocksConfigGeneral.group.addAll(LatBlocksConfigGeneral.class));
		configFile.add(LatBlocksConfigCrafting.group.addAll(LatBlocksConfigCrafting.class));
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}