package latmod.latblocks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by LatvianModder on 12.05.2016.
 */
@Mod(modid = LatBlocks.MOD_ID, name = "LatBlocks", version = "2.0.0")
public class LatBlocks
{
	public static final String MOD_ID = "latblocks";
	
	@Mod.Instance(MOD_ID)
	public static LatBlocks inst;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}
