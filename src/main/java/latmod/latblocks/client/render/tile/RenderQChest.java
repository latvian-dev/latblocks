package latmod.latblocks.client.render.tile;
import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import latmod.ftbu.util.client.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.block.BlockQChest;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class RenderQChest extends TileRenderer<TileQChest> implements IItemRenderer // BlockQChest
{
	public static final RenderQChest instance = new RenderQChest();
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/blocks/chest_model.png");
	public static final ResourceLocation tex_color = LatBlocks.mod.getLocation("textures/blocks/chest_model_color.png");
	public final ModelChest model = new ModelChest();
	public final RenderItem itemRender = new RenderItem();
	
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
		
		FontRenderer font = func_147498_b();
		
		model.chestLid.rotateAngleX = -t.getLidAngle(pt);
		
		LatCoreMCClient.setGLColor(t.colorChest, 255);
		bindTexture(tex_color);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		bindTexture(tex);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		
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
			
			int ss = font.getStringWidth(t.customName);
			double d = 1D / Math.max((ss + 30), 64);
			GL11.glScaled(d, d, d);
			
			GL11.glPushMatrix();
			GL11.glTranslated(0D, 0D, -0.1D);
			font.drawString(t.customName, -ss / 2, 0, t.colorText);
			GL11.glPopMatrix();
			
			if(t.textGlows) LatCoreMCClient.popMaxBrightness();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		if(t.iconItem != null && t.iconItem.getItem() != null)
		{
			GL11.glPushMatrix();
			
			Block b = Block.getBlockFromItem(t.iconItem.getItem());
			
			if(b != Blocks.air
			&& b.getBlockBoundsMinX() == 0D
			&& b.getBlockBoundsMinY() == 0D
			&& b.getBlockBoundsMinZ() == 0D
			&& b.getBlockBoundsMaxX() == 1D
			&& b.getBlockBoundsMaxY() == 1D
			&& b.getBlockBoundsMaxZ() == 1D)
				GL11.glTranslatef(0.5F, 0.80F, 0.105F);
			else
				GL11.glTranslatef(0.5F, 0.85F, 0.04F);
			
			GL11.glRotatef(180F, 0F, 1F, 0F);
			
			float iS = 0.8F;
			GL11.glScalef(-iS, -iS, iS);
			LMRenderHelper.renderItem(LatCoreMCClient.mc.theWorld, t.iconItem, true, true);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{ return true; }
	
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{ return true; }
	
	public void renderItem(ItemRenderType type, ItemStack is, Object... data)
	{
		int colorChest = 0xFFFFFFFF;
		int colorText = 0xFFFFFFFF;
		ItemStack iconItem = null;
		String customName = null;
		boolean textGlows = false;
		
		if(is.hasTagCompound() && is.stackTagCompound.hasKey(TileQChest.ITEM_TAG))
		{
			BlockQChest.tempTile.readTileData(is.stackTagCompound.getCompoundTag(TileQChest.ITEM_TAG));
			colorChest = BlockQChest.tempTile.colorChest;
			colorText = BlockQChest.tempTile.colorText;
			iconItem = BlockQChest.tempTile.iconItem;
			customName = BlockQChest.tempTile.customName;
			textGlows = BlockQChest.tempTile.textGlows;
		}
		
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslated(0D, 1D, 1D);
		
		if(type == ItemRenderType.ENTITY)
			GL11.glTranslated(-0.5D, 0D, -0.5D);
		
		GL11.glScalef(1F, -1F, -1F);
		
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(-90F, 0F, 1F, 0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		model.chestLid.rotateAngleX = 0F;
		
		LatCoreMCClient.setGLColor(colorChest, 255);
		bindTexture(tex_color);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		bindTexture(tex);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
		if(customName != null && !customName.isEmpty())
		{
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslatef( model.chestLid.rotationPointX / 16F,  model.chestLid.rotationPointY / 16F,  model.chestLid.rotationPointZ / 16F);
			GL11.glRotated(model.chestLid.rotateAngleX * 180D / Math.PI, 1D, 0D, 0D);
			GL11.glTranslatef(-model.chestLid.rotationPointX / 16F, -model.chestLid.rotationPointY / 16F, -model.chestLid.rotationPointZ / 16F);
			GL11.glTranslated(0.5D, 0.23D, 1D / 16D - 0.001D);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			
			if(textGlows) LatCoreMCClient.pushMaxBrightness();
			
			int ss = func_147498_b().getStringWidth(customName);
			double d = 1D / Math.max((ss + 30), 64);
			GL11.glScaled(d, d, d);
			
			GL11.glPushMatrix();
			GL11.glTranslated(0D, 0D, -0.1D);
			func_147498_b().drawString(customName, -ss / 2, 0, colorText);
			GL11.glPopMatrix();
			
			if(textGlows) LatCoreMCClient.popMaxBrightness();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		if(iconItem != null && iconItem.getItem() != null)
		{
			GL11.glPushMatrix();
			
			try
			{
				Block b = Block.getBlockFromItem(iconItem.getItem());
				
				if(b != Blocks.air
				&& b.getBlockBoundsMinX() == 0D
				&& b.getBlockBoundsMinY() == 0D
				&& b.getBlockBoundsMinZ() == 0D
				&& b.getBlockBoundsMaxX() == 1D
				&& b.getBlockBoundsMaxY() == 1D
				&& b.getBlockBoundsMaxZ() == 1D)
					GL11.glTranslatef(0.5F, 0.80F, 0.105F);
				else
					GL11.glTranslatef(0.5F, 0.85F, 0.04F);
				
				GL11.glRotatef(180F, 0F, 1F, 0F);
				
				float iS = 0.8F;
				GL11.glScalef(-iS, -iS, iS);
				LMRenderHelper.renderItem(LatCoreMCClient.mc.theWorld, iconItem, true, true);
			}
			catch(Exception e)
			{
			}
			
			GL11.glPopMatrix();
		}
		
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	public FontRenderer func_147498_b()
	{
		FontRenderer f = super.func_147498_b();
		return (f == null) ? LatCoreMCClient.mc.fontRenderer : f;
	}
}