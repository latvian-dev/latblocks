package latmod.latblocks;

import ftb.lib.FTBLib;
import latmod.latblocks.api.Paint;
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
		
		public void init(Entity entity, World world)
		{
			FTBLib.dev_logger.info("Inited @ " + world);
		}
		
		public void saveNBTData(NBTTagCompound compound)
		{
			Paint.writeToNBT(compound, "Paint", paint);
		}
		
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
