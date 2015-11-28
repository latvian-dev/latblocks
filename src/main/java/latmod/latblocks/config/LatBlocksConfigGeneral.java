package latmod.latblocks.config;

import latmod.lib.config.*;

public class LatBlocksConfigGeneral
{
	public static final ConfigGroup group = new ConfigGroup("general");
	public static final ConfigEntryBool fencesIgnorePlayers = new ConfigEntryBool("fences_ignore_players", true).sync();
	public static final ConfigEntryBool tankCraftingHandler = new ConfigEntryBool("tank_crafting_handler", true).sync();
}