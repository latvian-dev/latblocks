package latmod.latblocks.client;

import latmod.core.client.LMRenderHelper;
import latmod.latblocks.block.BlockPCarpet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
		if (event.currentItem != null)
		{
			if (event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				if (event.currentItem.getItem() instanceof ItemBlock)
				{
					if (event.target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
					{
						Block block = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
						
						if(block.getMaterial() != Material.air)
						{
							AxisAlignedBB bb = getBox(event.player, event.target, Block.getBlockFromItem(event.currentItem.getItem()));
							
							if(bb != null)
							{
								double iPX = event.player.prevPosX + (event.player.posX - event.player.prevPosX) * event.partialTicks;
								double iPY = event.player.prevPosY + (event.player.posY - event.player.prevPosY) * event.partialTicks;
								double iPZ = event.player.prevPosZ + (event.player.posZ - event.player.prevPosZ) * event.partialTicks;
								
								GL11.glDepthMask(false);
								GL11.glDisable(GL11.GL_CULL_FACE);
								GL11.glPushMatrix();
								
								GL11.glTranslated(-iPX, -iPY, -iPZ);
								
								bb.addCoord(event.target.blockX, event.target.blockY, event.target.blockZ);
								//GL11.glTranslated(event.target.blockX - iPX, event.target.blockY - iPY, event.target.blockZ - iPZ);
								ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[event.target.sideHit];
								bb.addCoord(fd.offsetX, fd.offsetY, fd.offsetZ);
								//GL11.glTranslated(fd.offsetX, fd.offsetY, fd.offsetZ);
								GL11.glScalef(1F, 1F, 1F);
								
								//GL11.glRotatef(rotationAngle, sideHit.offsetX, sideHit.offsetY, sideHit.offsetZ);
								//GL11.glRotatef(facingCorrectionAngle, sideHit.offsetX, sideHit.offsetY, sideHit.offsetZ);
								//GL11.glRotatef(90, xRotate, yRotate, zRotate);
								//GL11.glTranslated(0, 0, 0.5f * zCorrection);
								GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
								//RenderUtils.renderPulsingQuad(texture, 1f);
								GL11.glColor4f(1F, 1F, 1F, 0.5F);
								
								LMRenderHelper.drawOutlinedBoundingBoxGL(bb);
								
								GL11.glPopMatrix();
								GL11.glEnable(GL11.GL_CULL_FACE);
								GL11.glDepthMask(true);
								
								//event.setCanceled(true);
							}
						}
					}
				}
			}
		}
	}
	
	public AxisAlignedBB getBox(EntityPlayer ep, MovingObjectPosition mop, Block block)
	{
		if(block instanceof BlockPCarpet)
			return AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D);
		
		return null;
	}
}