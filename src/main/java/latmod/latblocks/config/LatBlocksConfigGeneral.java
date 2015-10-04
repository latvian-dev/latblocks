package latmod.latblocks.config;

import latmod.ftbu.api.config.*;

public class LatBlocksConfigGeneral
{
	public static final ConfigGroup group = new ConfigGroup("general");
	public static final ConfigEntryBool fencesIgnorePlayers = new ConfigEntryBool("fencesIgnorePlayers", true).setSyncWithClient();
	public static final ConfigEntryBool tankCraftingHandler = new ConfigEntryBool("tankCraftingHandler", true).setSyncWithClient();
	
	public static void load(ConfigFile f)
	{
		group.add(fencesIgnorePlayers);
		group.add(tankCraftingHandler);
		f.add(group);
	}
}