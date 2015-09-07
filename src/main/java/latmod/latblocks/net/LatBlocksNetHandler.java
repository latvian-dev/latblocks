package latmod.latblocks.net;

import latmod.ftbu.core.net.LMNetHelper;
import latmod.latblocks.LatBlocks;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class LatBlocksNetHandler
{
	public static final SimpleNetworkWrapper NET = LMNetHelper.newChannel(LatBlocks.mod.modID);
	
	public static void init()
	{
		NET.registerMessage(MessageDefaultPaint.class, MessageDefaultPaint.class, 0, Side.SERVER);
		NET.registerMessage(MessageOpenDefPaintGui.class, MessageOpenDefPaintGui.class, 1, Side.SERVER);
	}
}