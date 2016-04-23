package latmod.latblocks.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ftb.lib.api.block.ItemBlockLM;
import ftb.lib.api.client.GlStateManager;
import latmod.latblocks.block.BlockPaintableLB;
import latmod.latblocks.block.ItemBlockLB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class LatBlockClientEventHandler
{
	public static final LatBlockClientEventHandler instance = new LatBlockClientEventHandler();
	private final ArrayList<AxisAlignedBB> placementBoxes = new ArrayList<>();
	
	@SubscribeEvent
	public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent e)
	{
		if(!LatBlocksClient.renderHighlights.getAsBoolean()) return;
		
		if(e.currentItem != null && e.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && e.currentItem.getItem() instanceof ItemBlock)
		{
			Block itemBlock = Block.getBlockFromItem(e.currentItem.getItem());
			
			if(itemBlock instanceof BlockPaintableLB)
			{
				BlockPaintableLB block = (BlockPaintableLB) itemBlock;
				ItemBlockLM itemBlockLM = (ItemBlockLM) e.currentItem.getItem();
				
				placementBoxes.clear();
				block.getPlacementBoxes(placementBoxes, e);
				
				if(!placementBoxes.isEmpty())
				{
					if(!e.player.worldObj.getBlock(e.target.blockX + Facing.offsetsXForSide[e.target.sideHit], e.target.blockY + Facing.offsetsYForSide[e.target.sideHit], e.target.blockZ + Facing.offsetsZForSide[e.target.sideHit]).isReplaceable(e.player.worldObj, e.target.blockX + Facing.offsetsXForSide[e.target.sideHit], e.target.blockY + Facing.offsetsYForSide[e.target.sideHit], e.target.blockZ + Facing.offsetsZForSide[e.target.sideHit]))
						return;
					
					GlStateManager.enableBlend();
					GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GlStateManager.color(1F, 1F, 1F, 0.5F);
					GL11.glLineWidth(3F);
					GlStateManager.disableTexture2D();
					GlStateManager.depthMask(false);
					float f1 = 0.002F;
					double pt = e.partialTicks;
					
					double d0 = e.player.lastTickPosX + (e.player.posX - e.player.lastTickPosX) * pt;
					double d1 = e.player.lastTickPosY + (e.player.posY - e.player.lastTickPosY) * pt;
					double d2 = e.player.lastTickPosZ + (e.player.posZ - e.player.lastTickPosZ) * pt;
					
					ArrayList<AxisAlignedBB> hl = new ArrayList<>();
					block.drawHighlight(hl, e);
					
					for(int i = 0; i < hl.size(); i++)
					{
						AxisAlignedBB bb = hl.get(i);
						
						GL11.glLineWidth(1F);
						GlStateManager.color(1F, 1F, 1F, 0.7F);
						
						bb = bb.getOffsetBoundingBox(e.target.blockX, e.target.blockY, e.target.blockZ);
						bb = bb.getOffsetBoundingBox(Facing.offsetsXForSide[e.target.sideHit], Facing.offsetsYForSide[e.target.sideHit], Facing.offsetsZForSide[e.target.sideHit]);
						bb = bb.getOffsetBoundingBox(-d0, -d1, -d2);
						
						RenderGlobal.drawOutlinedBoundingBox(bb.expand(f1, f1, f1), -1);
					}
					
					if(((ItemBlockLB) itemBlockLM).canPlace(e.player.worldObj, e.target.blockX, e.target.blockY, e.target.blockZ, e.target.sideHit, e.player, e.currentItem))
					{
						for(int i = 0; i < placementBoxes.size(); i++)
						{
							AxisAlignedBB bb0 = placementBoxes.get(i);
							
							if(bb0 != null)
							{
								bb0 = bb0.getOffsetBoundingBox(e.target.blockX, e.target.blockY, e.target.blockZ);
								bb0 = bb0.getOffsetBoundingBox(Facing.offsetsXForSide[e.target.sideHit], Facing.offsetsYForSide[e.target.sideHit], Facing.offsetsZForSide[e.target.sideHit]);
								bb0 = bb0.getOffsetBoundingBox(-d0, -d1, -d2);
								
								RenderGlobal.drawOutlinedBoundingBox(bb0.expand(f1, f1, f1), -1);
							}
						}
					}
					
					GlStateManager.depthMask(true);
					GlStateManager.enableTexture2D();
					GlStateManager.enableBlend();
					
					if(!hl.isEmpty()) e.setCanceled(true);
				}
			}
		}
	}
}