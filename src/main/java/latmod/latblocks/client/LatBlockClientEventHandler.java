package latmod.latblocks.client;

import latmod.core.client.LMRenderHelper;
import latmod.latblocks.block.BlockPCarpet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

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
					if(drawSelectionBox(event.context, event.player, Block.getBlockFromItem(event.currentItem.getItem()), event.target, 0, event.partialTicks))
						;//event.setCanceled(true);
				}
			}
		}
	}
	
	private boolean drawSelectionBox(RenderGlobal context, EntityPlayer ep, Block blockHolding, MovingObjectPosition mop, int i, double partialTicks)
	{
		if (i == 0 && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			Block block = ep.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
			
			if(block.getMaterial() != Material.air)
			{
				AxisAlignedBB bb = getBox(ep, mop, blockHolding);
				
				if(bb != null)
				{
					/*GL11.glEnable(GL11.GL_BLEND);
					OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					GL11.glColor4f(1F, 1F, 1F, 0.5F);
					GL11.glLineWidth(3.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(false);
					double f1 = 0.002D;
					
					bb.addCoord(mop.blockX, mop.blockY, mop.blockZ);
					ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[mop.sideHit];
					bb.addCoord(fd.offsetX, fd.offsetY, fd.offsetZ);
					
					double d0 = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * partialTicks;
					double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * partialTicks;
					double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * partialTicks;
					RenderGlobal.drawOutlinedBoundingBox(bb.expand(f1, f1, f1).getOffsetBoundingBox(-d0, -d1, -d2), 0xFFFF0000);
					
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_BLEND);
					*/
					
					GL11.glDepthMask(false);
		            GL11.glDisable(GL11.GL_CULL_FACE);
		            GL11.glPushMatrix();
		            GL11.glTranslated(mop.blockX, mop.blockY, mop.blockZ);
		            GL11.glScalef(1F, 1F, 1F);
		            //GL11.glRotatef(rotationAngle, sideHit.offsetX, sideHit.offsetY, sideHit.offsetZ);
		            //GL11.glRotatef(facingCorrectionAngle, sideHit.offsetX, sideHit.offsetY, sideHit.offsetZ);
		            //GL11.glRotatef(90, xRotate, yRotate, zRotate);
		            //GL11.glTranslated(0, 0, 0.5f * zCorrection);
		            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		            //RenderUtils.renderPulsingQuad(texture, 1f);
		            
		            LMRenderHelper.drawOutlinedBoundingBoxGL(bb);
		            
		            GL11.glPopMatrix();
		            GL11.glEnable(GL11.GL_CULL_FACE);
		            GL11.glDepthMask(true);
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public AxisAlignedBB getBox(EntityPlayer ep, MovingObjectPosition mop, Block block)
	{
		if(block instanceof BlockPCarpet)
			return AxisAlignedBB.getBoundingBox(0D, 0D, 0D, 1D, 1D, 1D);
		
		return null;
	}
}