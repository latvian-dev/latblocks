package latmod.latblocks.config;
import java.io.File;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigRegistry;
import latmod.lib.config.*;

public class LatBlocksConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("latblocks", new File(FTBLib.folderConfig, "LatBlocks.json"));
		configFile.configGroup.setName("LatBlocks");
		configFile.add(new ConfigGroup("crafting").addAll(LatBlocksConfigCrafting.class));
		configFile.add(new ConfigGroup("general").addAll(LatBlocksConfigGeneral.class));
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}