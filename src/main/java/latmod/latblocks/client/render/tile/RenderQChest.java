package latmod.latblocks.client.render.tile;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.client.*;
import latmod.latblocks.block.BlockQChest;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class RenderQChest extends TileEntitySpecialRenderer implements IItemRenderer // BlockQChest
{
	public static final RenderQChest instance = new RenderQChest();
	public static final ResourceLocation tex = new ResourceLocation("latblocks", "textures/blocks/chest_model.png");
	public static final ResourceLocation tex_color = new ResourceLocation("latblocks", "textures/blocks/chest_model_color.png");
	public final ModelChest model = new ModelChest();
	public final RenderItem itemRender = new RenderItem();
	
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float pt)
	{
		if(te == null || te.isInvalid()) return;
		TileQChest t = (TileQChest) te;
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translate(x, y + 1D, z + 1D);
		GlStateManager.scale(1F, -1F, -1F);
		
		float rotYaw = 0F;
		
		if(t.blockMetadata == 2) rotYaw = 180F;
		else if(t.blockMetadata == 3) rotYaw = 0F;
		else if(t.blockMetadata == 4) rotYaw = 90F;
		else if(t.blockMetadata == 5) rotYaw = -90F;
		
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		GlStateManager.rotate(rotYaw, 0F, 1F, 0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		
		FontRenderer font = FTBLibClient.mc.fontRenderer;
		
		model.chestLid.rotateAngleX = -t.getLidAngle(pt);
		
		FTBLibClient.setGLColor(t.colorChest.color(), 255);
		FTBLibClient.setTexture(tex_color);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		GlStateManager.color(1F, 1F, 1F, 1F);
		FTBLibClient.setTexture(tex);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		
		GlStateManager.disableRescaleNormal();
		
		if(t.hasCustomInventoryName())
		{
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.translate(model.chestLid.rotationPointX / 16F, model.chestLid.rotationPointY / 16F, model.chestLid.rotationPointZ / 16F);
			GlStateManager.rotate((float) (model.chestLid.rotateAngleX * 180D / Math.PI), 1F, 0F, 0F);
			GlStateManager.translate(-model.chestLid.rotationPointX / 16F, -model.chestLid.rotationPointY / 16F, -model.chestLid.rotationPointZ / 16F);
			GlStateManager.translate(0.5D, 0.23D, 1D / 16D - 0.001D);
			GlStateManager.color(1F, 1F, 1F, 1F);
			
			if(t.textGlows) FTBLibClient.pushMaxBrightness();
			
			int ss = font.getStringWidth(t.getName());
			double d = 1D / Math.max((ss + 30), 64);
			GlStateManager.scale(d, d, d);
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0D, 0D, -0.1D);
			font.drawString(t.getName(), -ss / 2, 0, t.colorText.color());
			GlStateManager.popMatrix();
			
			if(t.textGlows) FTBLibClient.popMaxBrightness();
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
		
		GlStateManager.color(1F, 1F, 1F, 1F);
		
		if(t.iconItem != null && t.iconItem.getItem() != null)
		{
			GlStateManager.pushMatrix();
			
			Block b = Block.getBlockFromItem(t.iconItem.getItem());
			
			if(b != Blocks.air && b.getBlockBoundsMinX() == 0D && b.getBlockBoundsMinY() == 0D && b.getBlockBoundsMinZ() == 0D && b.getBlockBoundsMaxX() == 1D && b.getBlockBoundsMaxY() == 1D && b.getBlockBoundsMaxZ() == 1D)
				GlStateManager.translate(0.5F, 0.80F, 0.105F);
			else GlStateManager.translate(0.5F, 0.85F, 0.04F);
			
			GlStateManager.rotate(180F, 0F, 1F, 0F);
			
			float iS = 0.8F;
			GlStateManager.scale(-iS, -iS, iS);
			FTBLibClient.renderItem(FTBLibClient.mc.theWorld, t.iconItem);
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popMatrix();
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
			colorChest = BlockQChest.tempTile.colorChest.color();
			colorText = BlockQChest.tempTile.colorText.color();
			iconItem = BlockQChest.tempTile.iconItem;
			customName = BlockQChest.tempTile.getName();
			textGlows = BlockQChest.tempTile.textGlows;
		}
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translate(0D, 1D, 1D);
		
		if(type == ItemRenderType.ENTITY) GlStateManager.translate(-0.5D, 0D, -0.5D);
		
		GlStateManager.scale(1F, -1F, -1F);
		
		GlStateManager.translate(0.5F, 0.5F, 0.5F);
		GlStateManager.rotate(-90F, 0F, 1F, 0F);
		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
		
		model.chestLid.rotateAngleX = 0F;
		
		FTBLibClient.setGLColor(colorChest, 255);
		FTBLibClient.setTexture(tex_color);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		GlStateManager.color(1F, 1F, 1F, 1F);
		FTBLibClient.setTexture(tex);
		model.chestBelow.render(0.0625F);
		model.chestLid.render(0.0625F);
		
		GlStateManager.disableRescaleNormal();
		
		if(customName != null && !customName.isEmpty())
		{
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.translate(model.chestLid.rotationPointX / 16F, model.chestLid.rotationPointY / 16F, model.chestLid.rotationPointZ / 16F);
			GlStateManager.rotate((float) (model.chestLid.rotateAngleX * 180D / Math.PI), 1F, 0F, 0F);
			GlStateManager.translate(-model.chestLid.rotationPointX / 16F, -model.chestLid.rotationPointY / 16F, -model.chestLid.rotationPointZ / 16F);
			GlStateManager.translate(0.5D, 0.23D, 1D / 16D - 0.001D);
			GlStateManager.color(1F, 1F, 1F, 1F);
			
			if(textGlows) FTBLibClient.pushMaxBrightness();
			
			int ss = func_147498_b().getStringWidth(customName);
			double d = 1D / Math.max((ss + 30), 64);
			GlStateManager.scale(d, d, d);
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0D, 0D, -0.1D);
			func_147498_b().drawString(customName, -ss / 2, 0, colorText);
			GlStateManager.popMatrix();
			
			if(textGlows) FTBLibClient.popMaxBrightness();
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
		
		GlStateManager.color(1F, 1F, 1F, 1F);
		
		if(iconItem != null && iconItem.getItem() != null)
		{
			GlStateManager.pushMatrix();
			
			try
			{
				Block b = Block.getBlockFromItem(iconItem.getItem());
				
				if(b != Blocks.air && b.getBlockBoundsMinX() == 0D && b.getBlockBoundsMinY() == 0D && b.getBlockBoundsMinZ() == 0D && b.getBlockBoundsMaxX() == 1D && b.getBlockBoundsMaxY() == 1D && b.getBlockBoundsMaxZ() == 1D)
					GlStateManager.translate(0.5F, 0.80F, 0.105F);
				else GlStateManager.translate(0.5F, 0.85F, 0.04F);
				
				GlStateManager.rotate(180F, 0F, 1F, 0F);
				
				float iS = 0.8F;
				GlStateManager.scale(-iS, -iS, iS);
				FTBLibClient.renderItem(FTBLibClient.mc.theWorld, iconItem);
			}
			catch(Exception e)
			{
			}
			
			GlStateManager.popMatrix();
		}
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}
}