package latmod.latblocks.config;

import ftb.lib.FTBLib;
import ftb.lib.api.config.*;

import java.io.File;

public class LatBlocksConfig
{
	public static final ConfigFile configFile = new ConfigFile("latblocks");
	
	public static void load()
	{
		configFile.setFile(new File(FTBLib.folderConfig, "LatMod/LatBlocks.json"));
		configFile.setDisplayName("LatBlocks");
		configFile.addGroup("crafting", LatBlocksConfigCrafting.class);
		configFile.addGroup("general", LatBlocksConfigGeneral.class);
		ConfigRegistry.add(configFile);
		configFile.load();
	}
}