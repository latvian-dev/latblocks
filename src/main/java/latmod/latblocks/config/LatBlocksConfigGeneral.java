package latmod.latblocks.config;

import latmod.lib.config.*;

public class LatBlocksConfigGeneral
{
	@Sync
	public static final ConfigEntryBool fencesIgnorePlayers = new ConfigEntryBool("fences_ignore_players", true);
	
	@Sync
	public static final ConfigEntryBool tankCraftingHandler = new ConfigEntryBool("tank_crafting_handler", true);
}