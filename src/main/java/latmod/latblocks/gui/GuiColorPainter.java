package latmod.latblocks.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.*;
import ftb.lib.api.MouseButton;
import ftb.lib.api.client.*;
import ftb.lib.api.gui.GuiLM;
import ftb.lib.api.gui.widgets.ButtonLM;
import ftb.lib.mod.net.MessageClientItemAction;
import latmod.latblocks.item.ItemColorPainter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.*;

@SideOnly(Side.CLIENT)
public class GuiColorPainter extends GuiLM
{
	public static final ResourceLocation texLoc = new ResourceLocation("latblocks", "textures/gui/colorPainter.png");
	public static final TextureCoords colTex = new TextureCoords(texLoc, 156, 0, 16, 16);
	
	public final Map<EnumMCColor, ButtonLM> buttons;
	
	public GuiColorPainter(EntityPlayer ep)
	{
		super(null, texLoc);
		mainPanel.width = 156;
		mainPanel.width = 80;
		
		buttons = new EnumMap<>(EnumMCColor.class);
		
		for(int i = 0; i < 16; i++)
		{
			final int id = i;
			int x = (i % 8) * 18 + 7;
			int y = (i / 8) * 18 + 6;
			
			buttons.put(EnumMCColor.VALUES[id], new ButtonLM(this, x, y, 16, 16)
			{
				@Override
				public void onClicked(MouseButton button)
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Dmg", (byte) id);
					new MessageClientItemAction(ItemColorPainter.ACTION, tag).sendToServer();
					gui.close(null);
				}
			});
			
			buttons.get(EnumMCColor.VALUES[id]).title = EnumMCColor.VALUES[id].lang.format();
		}
	}
	
	@Override
	public void addWidgets()
	{
		mainPanel.addAll(buttons.values());
	}
	
	@Override
	public void drawBackground()
	{
		super.drawBackground();
		
		for(Map.Entry<EnumMCColor, ButtonLM> e : buttons.entrySet())
		{
			FTBLibClient.setGLColor(e.getKey().color, 255);
			e.getValue().render(colTex);
		}
		
		GlStateManager.color(1F, 1F, 1F, 1F);
	}
}