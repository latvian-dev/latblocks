package latmod.latblocks.client;

import latmod.core.FastList;
import latmod.latblocks.block.BlockPaintableLB;
import latmod.latcore.LCConfig;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.*;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class LatBlockClientEventHandler
{
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event)
	{
		if(!LCConfig.Client.renderHighlights) return;
		
		if (event.currentItem != null && event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && event.currentItem.getItem() instanceof ItemBlock)
		{
				Block itemBlock = Block.getBlockFromItem(event.currentItem.getItem());
				
				if(itemBlock instanceof BlockPaintableLB)
				{
					BlockPaintableLB block = (BlockPaintableLB)itemBlock;
					
					if(!block.canPlace(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ, event.target.sideHit, event.currentItem)) return;
					
					FastList<AxisAlignedBB> placementBoxes = new FastList<AxisAlignedBB>();
					block.getPlacementBoxes(placementBoxes, event);
					
					if(!placementBoxes.isEmpty())
					{
						ForgeDirection fg = ForgeDirection.VALID_DIRECTIONS[event.target.sideHit];
						
						if(!event.player.worldObj.getBlock(event.target.blockX + fg.offsetX, event.target.blockY + fg.offsetY, event.target.blockZ + fg.offsetZ).isReplaceable(event.player.worldObj, event.target.blockX, event.target.blockY, event.target.blockZ))
							return;
						
						GL11.glEnable(GL11.GL_BLEND);
						OpenGlHelper.glBlendFunc(770, 771, 1, 0);
						GL11.glColor4f(1F, 1F, 1F, 0.5F);
						GL11.glLineWidth(3F);
						GL11.glDisable(GL11.GL_TEXTURE_2D);
						GL11.glDepthMask(false);
						float f1 = 0.002F;
						double pt = event.partialTicks;
						
						double d0 = event.player.lastTickPosX + (event.player.posX - event.player.lastTickPosX) * pt;
						double d1 = event.player.lastTickPosY + (event.player.posY - event.player.lastTickPosY) * pt;
						double d2 = event.player.lastTickPosZ + (event.player.posZ - event.player.lastTickPosZ) * pt;
						
						for(int i = 0; i < placementBoxes.size(); i++)
						{
							AxisAlignedBB bb0 = placementBoxes.get(i);
							
							if(bb0 != null)
							{
								bb0 = bb0.getOffsetBoundingBox(event.target.blockX, event.target.blockY, event.target.blockZ);
								bb0 = bb0.getOffsetBoundingBox(fg.offsetX, fg.offsetY, fg.offsetZ);
								bb0 = bb0.getOffsetBoundingBox(-d0, -d1, -d2);
								
								RenderGlobal.drawOutlinedBoundingBox(bb0.expand(f1, f1, f1), -1);
							}
						}
						
						FastList<AxisAlignedBB> highlightBoxes = new FastList<AxisAlignedBB>();
						block.drawHighlight(highlightBoxes, event);
						
						for(int i = 0; i < highlightBoxes.size(); i++)
						{
							AxisAlignedBB bb = highlightBoxes.get(i);
							
							GL11.glLineWidth(1F);
							GL11.glColor4f(1F, 1F, 1F, 0.7F);
							
							bb = bb.getOffsetBoundingBox(event.target.blockX, event.target.blockY, event.target.blockZ);
							bb = bb.getOffsetBoundingBox(fg.offsetX, fg.offsetY, fg.offsetZ);
							bb = bb.getOffsetBoundingBox(-d0, -d1, -d2);
		 					
		 					RenderGlobal.drawOutlinedBoundingBox(bb.expand(f1, f1, f1), -1);
						}
						
						GL11.glDepthMask(true);
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						GL11.glDisable(GL11.GL_BLEND);
						
						if(!highlightBoxes.isEmpty()) event.setCanceled(true);
					}
			}
		}
	}
}