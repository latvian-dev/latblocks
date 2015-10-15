package latmod.latblocks.config;
import java.io.File;

import latmod.ftbu.api.config.ConfigListRegistry;
import latmod.ftbu.util.LatCoreMC;
import latmod.latblocks.LatBlocks;
import latmod.lib.config.ConfigFile;

public class LatBlocksConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile(LatBlocks.mod.modID, new File(LatCoreMC.configFolder, "/LatMod/LatBlocks.json"), true);
		LatBlocksConfigGeneral.load(configFile);
		LatBlocksConfigCrafting.load(configFile);
		ConfigListRegistry.add(configFile);
		configFile.load();
	}
}