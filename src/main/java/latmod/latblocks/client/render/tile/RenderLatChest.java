package latmod.latblocks.client.render.tile;
import latmod.ftbu.core.client.*;
import latmod.ftbu.core.util.LatCore;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderLatChest extends TileRenderer<TileQChest>
{
	public static final RenderLatChest instance = new RenderLatChest();
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/blocks/chest_model.png");
	public final ModelChest model = new ModelChest();
	
	public void renderTile(TileQChest t, double x, double y, double z, float pt)
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslated(x, y + 1D, z + 1D);
		GL11.glScalef(1F, -1F, -1F);
		
		float rotYaw = 0F;

		if(t.blockMetadata == 2) rotYaw = 180F;
		else if(t.blockMetadata == 3) rotYaw = 0F;
		else if(t.blockMetadata == 4) rotYaw = 90F;
		else if(t.blockMetadata == 5) rotYaw = -90F;
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(rotYaw, 0F, 1F, 0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		bindTexture(tex);
		
		model.chestLid.rotateAngleX = -t.getLidAngle(pt);
		LatCore.Colors.setGLColor(t.colorChest, 255);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
		if(t.customName != null)
		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslatef( model.chestLid.rotationPointX / 16F,  model.chestLid.rotationPointY / 16F,  model.chestLid.rotationPointZ / 16F);
			GL11.glRotated(model.chestLid.rotateAngleX * 180D / Math.PI, 1D, 0D, 0D);
			GL11.glTranslatef(-model.chestLid.rotationPointX / 16F, -model.chestLid.rotationPointY / 16F, -model.chestLid.rotationPointZ / 16F);
			GL11.glTranslated(0.5D, 0.23D, 1D / 16D - 0.001D);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			
			if(t.textGlows) LatCoreMCClient.pushMaxBrightness();
			
			int ss = func_147498_b().getStringWidth(t.customName);
			double d = 1D / Math.max((ss + 30), 64);
			GL11.glScaled(d, d, d);
			
			for(int i = 0; i < 6; i++)
			{
				GL11.glPushMatrix();
				GL11.glTranslated(0D, 0D, -i * 0.1D);
				func_147498_b().drawString(t.customName, -ss / 2, 0, t.colorText);
				GL11.glPopMatrix();
			}
			
			if(t.textGlows) LatCoreMCClient.popMaxBrightness();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}