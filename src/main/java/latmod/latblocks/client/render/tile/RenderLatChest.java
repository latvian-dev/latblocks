package latmod.latblocks.client.render.tile;
import latmod.core.LatCoreMC;
import latmod.core.client.TileRenderer;
import latmod.latblocks.tile.TileQChest;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderLatChest extends TileRenderer<TileQChest>
{
	public static final RenderLatChest instance = new RenderLatChest();
	
	public void renderTile(TileQChest t, double x, double y, double z, float f)
	{
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
			
			boolean glow = t.customName.contains("~g");
			String s = t.customName.replace("~g", "").replace("~", LatCoreMC.FORMATTING);
			
			GL11.glPushMatrix();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(rotYaw, 0F, 1F, 0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslated(0.5D, 0.23D, 1D / 16D - 0.001D);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			
			if(glow) LatCoreMC.Client.pushMaxBrightness();
			
			int ss = func_147498_b().getStringWidth(s);
			double d = 1D / Math.max((ss + 30), 64);
			GL11.glScaled(d, d, d);
			
			for(int i = 0; i < 6; i++)
			{
				GL11.glPushMatrix();
				GL11.glTranslated(0D, 0D, -i * 0.1D);
				func_147498_b().drawString(s, -ss / 2, 0, 0xFFFFFFFF);
				GL11.glPopMatrix();
			}
			
			if(glow) LatCoreMC.Client.popMaxBrightness();
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			
			GL11.glPopMatrix();
		}
	}
}