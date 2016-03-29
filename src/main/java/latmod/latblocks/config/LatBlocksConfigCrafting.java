package latmod.latblocks.config;

import ftb.lib.api.config.*;
import latmod.lib.annotations.NumberBounds;

public class LatBlocksConfigCrafting
{
	//TODO: Enum
	@NumberBounds(min = 0, max = 2)
	public static final ConfigEntryInt hammer = new ConfigEntryInt("hammer", 1);
	
	public static final ConfigEntryBool goggles = new ConfigEntryBool("goggles", true);
	public static final ConfigEntryBool endlessTank = new ConfigEntryBool("endless_tank", true);
	public static final ConfigEntryBool chest = new ConfigEntryBool("chest", true);
	public static final ConfigEntryBool furnace = new ConfigEntryBool("furnace", true);
	public static final ConfigEntryBool easyStartDust = new ConfigEntryBool("easy_start_dust", true);
	public static final ConfigEntryBool qNetBlocks = new ConfigEntryBool("qnet_blocks", true);
}