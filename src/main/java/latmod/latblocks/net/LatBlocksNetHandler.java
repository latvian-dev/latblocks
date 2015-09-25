package latmod.latblocks.net;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import latmod.ftbu.net.LMNetHelper;
import latmod.latblocks.LatBlocks;

public class LatBlocksNetHandler
{
	public static final SimpleNetworkWrapper NET = LMNetHelper.newChannel(LatBlocks.mod.modID);
	
	public static void init()
	{
		NET.registerMessage(MessageDefaultPaint.class, MessageDefaultPaint.class, 0, Side.SERVER);
		NET.registerMessage(MessageOpenDefPaintGui.class, MessageOpenDefPaintGui.class, 1, Side.SERVER);
	}
}