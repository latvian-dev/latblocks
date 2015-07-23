package latmod.latblocks.item.bag;

import latmod.ftbu.core.LMNBTUtils;
import latmod.ftbu.core.event.*;
import latmod.ftbu.core.inv.LMInvUtils;
import latmod.ftbu.core.util.*;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class QBagDataHandler
{
	public static final QBagDataHandler instance = new QBagDataHandler();
	public static int lastBagID = 0;
	private static final FastMap<Integer, InvQItems> invMap = new FastMap<Integer, InvQItems>();
	
	@SubscribeEvent
	public void loadLMData(LoadLMDataEvent e)
	{
		if(e.phase.isPost())
		{
			NBTTagCompound tag = e.worldData.getCompoundTag("LatBlocks");
			lastBagID = tag.getInteger("LastBagID");
			invMap.clear();
			
			NBTTagCompound map = tag.getCompoundTag("QBags");
			
			for(String s : LMNBTUtils.getMapKeys(map))
			{
				int id = Converter.toInt(s, 0);
				if(id > 0) LMInvUtils.readItemsFromNBT(getItems(id).stacks, map, Integer.toString(id));
			}
		}
	}
	
	@SubscribeEvent
	public void saveLMData(SaveLMDataEvent e)
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setInteger("LastBagID", lastBagID);
		
		NBTTagCompound map = new NBTTagCompound();
		
		for(int i = 0; i < invMap.size(); i++)
			LMInvUtils.writeItemsToNBT(invMap.values.get(i).stacks, map, Integer.toString(invMap.keys.get(i)));
		
		tag.setTag("QBags", map);
		
		e.worldData.setTag("LatBlocks", tag);
	}

	public static InvQItems getItems(int id)
	{
		if(id <= 0) return null;
		InvQItems i = invMap.get(id);
		if(i != null) return i;
		i = new InvQItems(id);
		invMap.put(id, i);
		return i;
	}
	
	public static void set(int id, InvQItems i)
	{ /*if(id > 0) invMap.put(id, i);*/ }
}