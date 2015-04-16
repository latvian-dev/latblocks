package latmod.latblocks.client;

import latmod.core.item.ItemBlockLM;
import latmod.core.mod.LCConfig;
import latmod.core.util.FastList;
import latmod.latblocks.block.BlockPaintableLB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.*;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlockClientEventHandler
{
	public static final LatBlockClientEventHandler instance = new LatBlockClientEventHandler();
	
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent e)
	{
		if(!LCConfig.Client.renderHighlights) return;
		
		if (e.currentItem != null && e.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && e.currentItem.getItem() instanceof ItemBlock)
		{
			Block itemBlock = Block.getBlockFromItem(e.currentItem.getItem());
			
			if(itemBlock instanceof BlockPaintableLB)
			{
				BlockPaintableLB block = (BlockPaintableLB)itemBlock;
				ItemBlockLM itemBlockLM = (ItemBlockLM)e.currentItem.getItem();
				
				FastList<AxisAlignedBB> pl = new FastList<AxisAlignedBB>();
				block.getPlacementBoxes(pl, e);
				
				if(!pl.isEmpty())
				{
					if(!e.player.worldObj.getBlock(e.target.blockX + Facing.offsetsXForSide[e.target.sideHit], e.target.blockY + Facing.offsetsYForSide[e.target.sideHit], e.target.blockZ + Facing.offsetsZForSide[e.target.sideHit])
					.isReplaceable(e.player.worldObj, e.target.blockX + Facing.offsetsXForSide[e.target.sideHit], e.target.blockY + Facing.offsetsYForSide[e.target.sideHit], e.target.blockZ + Facing.offsetsZForSide[e.target.sideHit]))
						return;
					
					GL11.glEnable(GL11.GL_BLEND);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					GL11.glColor4f(1F, 1F, 1F, 0.5F);
					GL11.glLineWidth(3F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(false);
					float f1 = 0.002F;
					double pt = e.partialTicks;
					
					double d0 = e.player.lastTickPosX + (e.player.posX - e.player.lastTickPosX) * pt;
					double d1 = e.player.lastTickPosY + (e.player.posY - e.player.lastTickPosY) * pt;
					double d2 = e.player.lastTickPosZ + (e.player.posZ - e.player.lastTickPosZ) * pt;
					
					FastList<AxisAlignedBB> hl = new FastList<AxisAlignedBB>();
					block.drawHighlight(hl, e);
					
					for(int i = 0; i < hl.size(); i++)
					{
						AxisAlignedBB bb = hl.get(i);
						
						GL11.glLineWidth(1F);
						GL11.glColor4f(1F, 1F, 1F, 0.7F);
						
						bb = bb.getOffsetBoundingBox(e.target.blockX, e.target.blockY, e.target.blockZ);
						bb = bb.getOffsetBoundingBox(Facing.offsetsXForSide[e.target.sideHit], Facing.offsetsYForSide[e.target.sideHit], Facing.offsetsZForSide[e.target.sideHit]);
						bb = bb.getOffsetBoundingBox(-d0, -d1, -d2);
	 					
	 					RenderGlobal.drawOutlinedBoundingBox(bb.expand(f1, f1, f1), -1);
					}
					
					if(itemBlockLM.canPlace(e.player.worldObj, e.target.blockX, e.target.blockY, e.target.blockZ, e.target.sideHit, e.player, e.currentItem))
					{
						for(int i = 0; i < pl.size(); i++)
						{
							AxisAlignedBB bb0 = pl.get(i);
							
							if(bb0 != null)
							{
								bb0 = bb0.getOffsetBoundingBox(e.target.blockX, e.target.blockY, e.target.blockZ);
								bb0 = bb0.getOffsetBoundingBox(Facing.offsetsXForSide[e.target.sideHit], Facing.offsetsYForSide[e.target.sideHit], Facing.offsetsZForSide[e.target.sideHit]);
								bb0 = bb0.getOffsetBoundingBox(-d0, -d1, -d2);
								
								RenderGlobal.drawOutlinedBoundingBox(bb0.expand(f1, f1, f1), -1);
							}
						}
					}
					
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_BLEND);
					
					if(!hl.isEmpty()) e.setCanceled(true);
				}
			}
		}
	}
}