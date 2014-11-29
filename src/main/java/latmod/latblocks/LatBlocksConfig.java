package latmod.latblocks;
import latmod.core.LMConfig;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LatBlocksConfig extends LMConfig
{
	public General general;
	public Client client;
	
	public LatBlocksConfig(FMLPreInitializationEvent e)
	{
		super(e, "/LatMod/LatBlocks.cfg");
		general = new General();
		client = new Client();
		save();
	}
	
	public class General extends Category
	{
		public General()
		{
			super("general");
		}
	}
	
	public class Client extends Category
	{
		public boolean renderBoxes;
		
		public Client()
		{
			super("client");
			
			renderBoxes = getBool("renderBoxes", true);
		}
	}
}