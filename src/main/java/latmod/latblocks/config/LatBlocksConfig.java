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
		configFile = new ConfigFile("latblocks", new File(FTBLib.folderConfig, "LatBlocks.json"));
		configFile.configList.setName("LatBlocks");
		configFile.add(LatBlocksConfigGeneral.group.addAll(LatBlocksConfigGeneral.class));
		configFile.add(LatBlocksConfigCrafting.group.addAll(LatBlocksConfigCrafting.class));
		ConfigListRegistry.instance.add(configFile);
		configFile.load();
	}
}