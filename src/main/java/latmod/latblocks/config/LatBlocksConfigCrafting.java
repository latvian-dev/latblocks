package latmod.latblocks.config;

import latmod.lib.config.*;
import latmod.lib.util.IntBounds;

public class LatBlocksConfigCrafting
{
	public static final ConfigGroup group = new ConfigGroup("crafting");
	public static final ConfigEntryInt hammer = new ConfigEntryInt("goggles", new IntBounds(1, 0, 2));
	public static final ConfigEntryBool goggles = new ConfigEntryBool("goggles", true);
	public static final ConfigEntryBool endlessTank = new ConfigEntryBool("endless_tank", true);
	public static final ConfigEntryBool chest = new ConfigEntryBool("chest", true);
	public static final ConfigEntryBool furnace = new ConfigEntryBool("furnace", true);
	public static final ConfigEntryBool easyStartDust = new ConfigEntryBool("easy_start_dust", true);
	public static final ConfigEntryBool qNetBlocks = new ConfigEntryBool("qnet_blocks", true);
}