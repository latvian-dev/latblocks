package latmod.latblocks;

import cpw.mods.fml.relauncher.Side;
import ftb.lib.*;
import latmod.ftbu.api.paint.*;
import latmod.ftbu.tile.TileLM;
import latmod.ftbu.world.*;
import latmod.latblocks.tile.TileFountain;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

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
		
		LMPlayer player = LMWorld.getWorld(t.isServer() ? Side.SERVER : Side.CLIENT).getPlayer(ep);
		Paint[] p = new Paint[6];
		int[] ai = player.getPrivateData().getIntArray(LatBlocksGuiHandler.DEF_PAINT_TAG);
		if(ai.length != 12) return;
		
		for(int i = 0; i < 6; i++)
		{
			Block b = Block.getBlockById(ai[i * 2 + 0]);
			if(b != Blocks.air) p[i] = new Paint(b, ai[i * 2 + 1]);
		}
		
		if(paint.length != 6)
		{
			if(p[SidedDirection.FRONT.ID] != null)
			{
				for(int i = 0; i < paint.length; i++)
				{
					if(paintable.isPaintValid(i, p[SidedDirection.FRONT.ID])) paint[i] = p[SidedDirection.FRONT.ID];
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
				
				if(sd != SidedDirection.NONE && p[sd.ID] != null && paintable.isPaintValid(f, p[sd.ID]))
					paint[f] = p[sd.ID].clone();
			}
		}
		
		t.markDirty();
	}
}