package latmod.latblocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ftb.lib.FTBLib;
import ftb.lib.api.EventFTBSync;
import latmod.latblocks.api.Paint;
import latmod.latblocks.config.LatBlocksConfigGeneral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * Created by LatvianModder on 22.02.2016.
 */
public class LatBlockEventHandler
{
	@SubscribeEvent
	public void syncData(EventFTBSync e)
	{
		if(e.world.side.isServer())
		{
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setBoolean("FIP", LatBlocksConfigGeneral.fences_ignore_players.getAsBoolean());
			tag.setBoolean("TCH", LatBlocksConfigGeneral.tank_crafting_handler.getAsBoolean());
			
			e.syncData.setTag("LatBlocks", tag);
		}
		else
		{
			NBTTagCompound tag = e.syncData.getCompoundTag("LatBlocks");
			
			LatBlocksConfigGeneral.fences_ignore_players.set(tag.getBoolean("FIP"));
			LatBlocksConfigGeneral.tank_crafting_handler.set(tag.getBoolean("TCH"));
		}
	}
	
	@SubscribeEvent
	public void onPlayerPropertiesEvent(EntityEvent.EntityConstructing e)
	{
		if(e.entity instanceof EntityPlayer)
		{
			e.entity.registerExtendedProperties("LatBlocks", new LatBlockProperties());
		}
	}
	
	public static class LatBlockProperties implements IExtendedEntityProperties
	{
		public final Paint[] paint = new Paint[6];
		
		@Override
		public void init(Entity entity, World world)
		{
			FTBLib.dev_logger.info("Inited @ " + world);
		}
		
		@Override
		public void saveNBTData(NBTTagCompound compound)
		{
			Paint.writeToNBT(compound, "Paint", paint);
		}
		
		@Override
		public void loadNBTData(NBTTagCompound compound)
		{
			Paint.readFromNBT(compound, "Paint", paint);
		}
		
		public static LatBlockProperties get(EntityPlayer p)
		{
			LatBlockProperties properties = (LatBlockProperties) p.getExtendedProperties("LatBlocks");
			
			if(properties == null)
			{
				properties = new LatBlockProperties();
				p.registerExtendedProperties("LatBlocks", properties);
			}
			
			return properties;
		}
	}
}
