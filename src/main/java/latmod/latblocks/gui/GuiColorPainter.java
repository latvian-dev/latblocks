package latmod.latblocks.gui;
import latmod.core.EnumDyeColor;
import latmod.core.client.LMRenderHelper;
import latmod.core.gui.*;
import latmod.core.net.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.item.ItemColorPainter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiColorPainter extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/colorPainter.png");
	public static final TextureCoords colTex = new TextureCoords(texLoc, 156, 0);
	
	public final ButtonLM buttons[] = new ButtonLM[16];
	
	public GuiColorPainter(EntityPlayer ep)
	{
		super(new ContainerEmpty(ep, null), texLoc);
		xSize = 156;
		ySize = 80;
		
		for(int i = 0; i < 16; i++)
		{
			final int id = i;
			int x = (i % 8) * 18 + 7;
			int y = (i / 8) * 18 + 6;
			
			widgets.add(buttons[id] = new ButtonLM(this, x, y, 16, 16)
			{
				public void onButtonPressed(int b)
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Dmg", (byte)id);
					MessageLM.NET.sendToServer(new MessageClientItemAction(ItemColorPainter.ACTION, tag));
					container.player.closeScreen();
				}
			});
			
			buttons[id].title = EnumDyeColor.VALUES[id].toString();
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		for(int i = 0; i < buttons.length; i++)
		{
			LMRenderHelper.colorize(EnumDyeColor.VALUES[i].color.getRGB());
			buttons[i].render(colTex);
		}
		
		LMRenderHelper.recolor();
	}
	
	public void drawText(int mx, int my)
	{
		super.drawText(mx, my);
		GL11.glDisable(GL11.GL_LIGHTING);
	}
}