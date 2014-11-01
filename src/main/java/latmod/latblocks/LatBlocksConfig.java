package latmod.latblocks;
import latmod.core.LMConfig;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LatBlocksConfig extends LMConfig
{
	public LatBlocksConfig(FMLPreInitializationEvent e)
	{
		super(e, "/LatMod/LatBlocks.cfg");
		save();
	}

	public class General extends Category
	{
		public General()
		{
			super("general");
		}
	}
}