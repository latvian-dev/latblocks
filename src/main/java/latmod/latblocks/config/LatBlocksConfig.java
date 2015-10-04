package latmod.latblocks.config;
import java.io.File;

import latmod.ftbu.api.config.*;
import latmod.ftbu.util.LatCoreMC;
import latmod.latblocks.LatBlocks;

public class LatBlocksConfig
{
	private static ConfigFile configFile;
	
	public static void load()
	{
		configFile = new ConfigFile(LatBlocks.mod.modID, new File(LatCoreMC.configFolder, "/LatMod/LatBlocks.txt"), true);
		LatBlocksConfigGeneral.load(configFile);
		LatBlocksConfigCrafting.load(configFile);
		ConfigFileRegistry.add(configFile);
		configFile.load();
	}
}