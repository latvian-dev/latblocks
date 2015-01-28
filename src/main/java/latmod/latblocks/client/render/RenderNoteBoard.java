package latmod.latblocks.client.render;

import latmod.latblocks.tile.TileNoteBoard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderNoteBoard extends TileEntitySpecialRenderer
{
	public void renderTileEntityAt(TileEntity te, double rx, double ry, double rz, float rt)
	{
		TileNoteBoard t = (TileNoteBoard)te;
		
		MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
		
		if(t != null && t.isValid() && t.blockMetadata != -1 && mop != null && mop.blockX == t.xCoord && mop.blockY == t.yCoord && mop.blockZ == t.zCoord)
		{
			int index = t.getIndex(mop);
			
			if(index >= 0 && index < 16 && !t.notes[index].isEmpty())
			{
				String text = t.notes[index];
				
				FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
				float f = 1.6F;
				float f1 = 0.016666668F * f;
				GL11.glPushMatrix();
				GL11.glTranslated(rx + 0.5D, ry + 0.75D, rz + 0.5D);
				
				ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[t.blockMetadata];
				GL11.glTranslated(fd.offsetX * 0.25D, fd.offsetY * 0.25D, fd.offsetZ * 0.25D);
				
				GL11.glNormal3f(0F, 1F, 0F);
				GL11.glRotatef(-RenderManager.instance.playerViewY, 0F, 1F, 0F);
				GL11.glRotatef(RenderManager.instance.playerViewX, 1F, 0F, 0F);
				GL11.glScalef(-f1, -f1, f1);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(false);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				Tessellator ts = Tessellator.instance;
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				ts.startDrawingQuads();
				int j = fr.getStringWidth(text) / 2;
				ts.setColorRGBA_F(0F, 0F, 0F, 0.25F);
				ts.addVertex(-j - 1D, -1D, 0D);
				ts.addVertex(-j - 1D, 8D, 0D);
				ts.addVertex(j + 1D, 8D, 0D);
				ts.addVertex(j + 1D, -1D, 0D);
				ts.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				fr.drawString(text, -fr.getStringWidth(text) / 2, 0, 553648127);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glDepthMask(true);
				fr.drawString(text, -fr.getStringWidth(text) / 2, 0, -1);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glPopMatrix();
			}
		}
	}
}