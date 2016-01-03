package latmod.latblocks.config;

import ftb.lib.FTBLib;
import ftb.lib.api.config.ConfigRegistry;
import latmod.lib.config.*;

import java.io.File;

public class LatBlocksConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile("latblocks", new File(FTBLib.folderConfig, "LatMod/LatBlocks.json"));
		configFile.configGroup.setName("LatBlocks");
		configFile.add(new ConfigGroup("crafting").addAll(LatBlocksConfigCrafting.class, null, false));
		configFile.add(new ConfigGroup("general").addAll(LatBlocksConfigGeneral.class, null, false));
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}