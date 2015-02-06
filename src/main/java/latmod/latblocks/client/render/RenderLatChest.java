package latmod.latblocks.client.render;
import latmod.latblocks.tile.TileLatChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderLatChest extends TileEntitySpecialRenderer
{
	public RenderLatChest()
	{
	}
	
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f)
	{
		if(te == null || !(te instanceof TileLatChest)) return;
		
		TileLatChest t = ((TileLatChest)te);
		
		if(t.customName != null)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y + 1F, (float)z + 1F);
			GL11.glScalef(1F, -1F, -1F);
			
			int rot = t.rotation.ordinal();
			float rotYaw = 0F;

			if(rot == 2) rotYaw = 180F;
			else if(rot == 3) rotYaw = 0F;
			else if(rot == 4) rotYaw = 90F;
			else if(rot == 5) rotYaw = -90F;
			
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(rotYaw, 0F, 1F, 0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslated(0.5D, 0.23D, 1D / 16D - 0.001D);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			int ss = func_147498_b().getStringWidth(t.customName);
			double d = 1D / Math.max((ss + 30), 64);
			GL11.glScaled(d, d, d);
			
			for(int i = 0; i < 6; i++)
			{
				GL11.glPushMatrix();
				GL11.glTranslated(0D, 0D, -i * 0.1D);
				func_147498_b().drawString(t.customName, -ss / 2, 0, 0xFF0A0A0A);
				GL11.glPopMatrix();
			}
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			
			GL11.glPopMatrix();
		}
	}
}