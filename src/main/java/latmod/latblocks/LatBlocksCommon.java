package latmod.latblocks;

import ftb.lib.MathHelperMC;
import ftb.lib.SidedDirection;
import ftb.lib.api.tile.TileLM;
import latmod.latblocks.api.IPaintable;
import latmod.latblocks.api.Paint;
import latmod.latblocks.tile.TileFountain;
import net.minecraft.entity.player.EntityPlayer;

public class LatBlocksCommon // LatBlocksClient
{
	public void preInit()
	{
	}
	
	public void postInit()
	{
	}
	
	public void spawnFountainParticle(TileFountain t)
	{
	}
	
	public void setDefPaint(TileLM t, EntityPlayer ep, Paint[] paint)
	{
		IPaintable paintable = (t instanceof IPaintable) ? (IPaintable) t : null;
		if(paintable == null) return;
		
		LatBlockEventHandler.LatBlockProperties props = LatBlockEventHandler.LatBlockProperties.get(ep);
		
		if(paint.length != 6)
		{
			if(props.paint[SidedDirection.FRONT.ID] != null)
			{
				for(int i = 0; i < paint.length; i++)
				{
					if(paintable.isPaintValid(i, props.paint[SidedDirection.FRONT.ID]))
						paint[i] = props.paint[SidedDirection.FRONT.ID];
				}
			}
		}
		else
		{
			int r3 = MathHelperMC.get3DRotation(t.getWorldObj(), t.xCoord, t.yCoord, t.zCoord, ep);
			int r2 = 0;
			
			if(r3 == 0 || r3 == 1) r2 = MathHelperMC.get2DRotation(ep);
			
			for(int f = 0; f < 6; f++)
			{
				SidedDirection sd = SidedDirection.get(f, r3, r2);
				
				if(sd != SidedDirection.NONE && props.paint[sd.ID] != null && paintable.isPaintValid(f, props.paint[sd.ID]))
					paint[f] = props.paint[sd.ID].copy();
			}
		}
		
		t.markDirty();
	}
}