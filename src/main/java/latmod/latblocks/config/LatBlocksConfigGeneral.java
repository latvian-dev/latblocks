package latmod.latblocks.config;

import ftb.lib.api.config.ConfigSyncRegistry;
import latmod.lib.config.*;

public class LatBlocksConfigGeneral
{
	public static final ConfigGroup group = new ConfigGroup("general");
	public static final ConfigEntryBool fencesIgnorePlayers = new ConfigEntryBool("fencesIgnorePlayers", true);
	public static final ConfigEntryBool tankCraftingHandler = new ConfigEntryBool("tankCraftingHandler", true);
	
	public static void load(ConfigFile f)
	{
		group.add(fencesIgnorePlayers);
		group.add(tankCraftingHandler);
		f.add(group);
		
		ConfigSyncRegistry.add(fencesIgnorePlayers);
		ConfigSyncRegistry.add(tankCraftingHandler);
	}
}