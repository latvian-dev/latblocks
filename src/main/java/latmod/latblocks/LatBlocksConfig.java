package latmod.latblocks;
import latmod.core.*;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LatBlocksConfig extends LMConfig implements IServerConfig
{
	public LatBlocksConfig(FMLPreInitializationEvent e)
	{ super(e, "/LatMod/LatBlocks.cfg"); }
	
	public void load()
	{
		General.load(get("general"));
		Crafting.load(get("crafting"));
	}
	
	public void readConfig(NBTTagCompound tag)
	{
		General.fencesIgnorePlayers = tag.getBoolean("Fences");
		General.tankCraftingHandler = tag.getBoolean("Tanks");
	}
	
	public void writeConfig(NBTTagCompound tag)
	{
		tag.setBoolean("Fences", General.fencesIgnorePlayers);
		tag.setBoolean("Tanks", General.tankCraftingHandler);
	}
	
	public static class General
	{
		public static boolean fencesIgnorePlayers;
		public static boolean tankCraftingHandler;
		
		public static void load(Category c)
		{
			fencesIgnorePlayers = c.getBool("fencesIgnorePlayers", true);
			tankCraftingHandler = c.getBool("tankCraftingHandler", true);
		}
	}
	
	public static class Crafting
	{
		public static int hammer;
		public static boolean goggles;
		public static boolean glowiumBlocks;
		public static boolean dataErase;
		public static boolean endlessTank;
		public static boolean glowiumGems;
		public static boolean glowiumDusts;
		public static boolean chest;
		public static boolean furnace;
		
		public static void load(Category c)
		{
			hammer = c.getInt("hammer", 1, 0, 2);
			goggles = c.getBool("goggles", true);
			glowiumBlocks = c.getBool("glowiumBlocks", true);
			dataErase = c.getBool("dataErase", true);
			endlessTank = c.getBool("endlessTank", true);
			glowiumGems = c.getBool("glowiumGems", true);
			glowiumDusts = c.getBool("glowiumDusts", true);
			chest = c.getBool("chest", true);
			furnace = c.getBool("furnace", true);
		}
	}
}