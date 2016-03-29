package latmod.latblocks.config;

import ftb.lib.api.config.ConfigEntryBool;
import latmod.lib.annotations.*;

public class LatBlocksConfigGeneral
{
	@Flags(Flag.SYNC)
	public static final ConfigEntryBool fencesIgnorePlayers = new ConfigEntryBool("fences_ignore_players", true);
	
	@Flags(Flag.SYNC)
	public static final ConfigEntryBool tankCraftingHandler = new ConfigEntryBool("tank_crafting_handler", true);
}