package latmod.latblocks.config;

import latmod.lib.config.*;
import latmod.lib.util.IntBounds;

public class LatBlocksConfigCrafting
{
	public static final ConfigGroup group = new ConfigGroup("crafting");
	public static final ConfigEntryInt hammer = new ConfigEntryInt("goggles", new IntBounds(1, 0, 2));
	public static final ConfigEntryBool goggles = new ConfigEntryBool("goggles", true);
	public static final ConfigEntryBool endlessTank = new ConfigEntryBool("endlessTank", true);
	public static final ConfigEntryBool chest = new ConfigEntryBool("chest", true);
	public static final ConfigEntryBool furnace = new ConfigEntryBool("furnace", true);
	public static final ConfigEntryBool easyStartDust = new ConfigEntryBool("easyStartDust", true);
	public static final ConfigEntryBool qNetBlocks = new ConfigEntryBool("qNetBlocks", true);
	
	public static void load(ConfigFile f)
	{
		group.add(hammer);
		group.add(goggles);
		group.add(endlessTank);
		group.add(chest);
		group.add(furnace);
		group.add(easyStartDust);
		group.add(qNetBlocks);
		f.add(group);
	}
}