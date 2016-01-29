package latmod.latblocks.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.*;
import ftb.lib.api.client.*;
import ftb.lib.api.gui.GuiLM;
import ftb.lib.api.gui.widgets.ButtonLM;
import ftb.lib.mod.net.MessageClientItemAction;
import latmod.latblocks.item.ItemColorPainter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiColorPainter extends GuiLM
{
	public static final ResourceLocation texLoc = new ResourceLocation("latblocks", "textures/gui/colorPainter.png");
	public static final TextureCoords colTex = new TextureCoords(texLoc, 156, 0, 16, 16);
	
	public final ButtonLM buttons[] = new ButtonLM[16];
	
	public GuiColorPainter(EntityPlayer ep)
	{
		super(null, texLoc);
		mainPanel.width = 156;
		mainPanel.width = 80;
		
		for(int i = 0; i < 16; i++)
		{
			final int id = i;
			int x = (i % 8) * 18 + 7;
			int y = (i / 8) * 18 + 6;
			
			buttons[id] = new ButtonLM(this, x, y, 16, 16)
			{
				public void onButtonPressed(int b)
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Dmg", (byte) id);
					new MessageClientItemAction(ItemColorPainter.ACTION, tag).sendToServer();
					gui.close(null);
				}
			};
			
			buttons[id].title = EnumMCColor.VALUES[id].toString();
		}
	}
	
	public void addWidgets()
	{
		mainPanel.addAll(buttons);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		for(int i = 0; i < buttons.length; i++)
		{
			FTBLibClient.setGLColor(EnumMCColor.VALUES[i].color, 255);
			buttons[i].render(colTex);
		}
		
		GlStateManager.color(1F, 1F, 1F, 1F);
	}
}