package latmod.latblocks;
import latmod.ftbu.core.*;
import net.minecraft.entity.player.EntityPlayerMP;
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
		int[] b = tag.getIntArray("C");
		General.fencesIgnorePlayers = b[0] == 1;
		General.tankCraftingHandler = b[1] == 1;
	}
	
	public void writeConfig(NBTTagCompound tag, EntityPlayerMP ep)
	{
		tag.setIntArray("C", new int[]
		{
			General.fencesIgnorePlayers ? 1 : 0,
			General.tankCraftingHandler ? 1 : 0,
		});
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
		public static boolean endlessTank;
		public static boolean glowiumGems;
		public static boolean glowiumDusts;
		public static boolean chest;
		public static boolean furnace;
		public static boolean bricks;
		
		public static void load(Category c)
		{
			hammer = c.getInt("hammer", 1, 0, 2);
			goggles = c.getBool("goggles", true);
			glowiumBlocks = c.getBool("glowiumBlocks", true);
			endlessTank = c.getBool("endlessTank", true);
			glowiumGems = c.getBool("glowiumGems", true);
			glowiumDusts = c.getBool("glowiumDusts", true);
			chest = c.getBool("chest", true);
			furnace = c.getBool("furnace", true);
			bricks = c.getBool("bricks", true);
		}
	}
}