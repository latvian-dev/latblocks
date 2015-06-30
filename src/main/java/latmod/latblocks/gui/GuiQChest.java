package latmod.latblocks.gui;
import latmod.ftbu.core.client.LMGuiButtons;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.*;
import latmod.ftbu.mod.client.gui.GuiSelectColor;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQChest extends GuiLM implements GuiSelectColor.ColorSelectorCallback
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qchest.png");
	public static final TextureCoords color_tex = new TextureCoords(tex, 248, 0, 8, 8);
	
	public final TileQChest chest;
	public final TextBoxLM textBoxLabel;
	public final ButtonLM buttonSecurity, buttonColChest, buttonColText, buttonGlow;
	
	public GuiQChest(ContainerQChest c)
	{
		super(c, tex);
		
		xSize = 248;
		ySize = 247;
		chest = (TileQChest)c.inv;
		
		textBoxLabel = new TextBoxLM(this, 7, 6, 235, 18)
		{
			public void textChanged()
			{
				chest.clientCustomName(textBoxLabel.text);
			}
		};
		
		textBoxLabel.charLimit = 45;
		
		if(chest.customName != null)
			textBoxLabel.text = chest.customName;
		
		buttonSecurity = new ButtonLM(this, 215, 166, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				chest.clientPressButton(LMGuiButtons.SECURITY, b);
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(chest.security.level.getText());
			}
		};
		
		buttonColChest = new ButtonLM(this, 17, 166, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				mc.displayGuiScreen(new GuiSelectColor(GuiQChest.this, chest.colorChest, 0));
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(title);
				l.add(LatCore.Colors.getHex(chest.colorChest));
			}
		};
		
		buttonColText = new ButtonLM(this, 17, 195, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				mc.displayGuiScreen(new GuiSelectColor(GuiQChest.this, chest.colorText, 1));
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(title);
				l.add(LatCore.Colors.getHex(chest.colorText));
			}
		};
		
		buttonGlow = new ButtonLM(this, 215, 195, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				chest.clientPressButton(TileQChest.BUTTON_GLOW, b);
				refreshWidgets();
			}
		};
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		buttonColChest.title = "Chest Color";
		buttonColText.title = "Text Color";
		
		l.add(textBoxLabel);
		l.add(buttonSecurity);
		l.add(buttonColChest);
		l.add(buttonColText);
		l.add(buttonGlow);
	}
	
	public void drawBackground()
	{
		buttonGlow.title = chest.textGlows ? "Glow: True" : "Glow: False";
		
		super.drawBackground();
		buttonSecurity.render(Icons.security[chest.security.level.ID]);
		LatCore.Colors.setGLColor(chest.colorChest, 200);
		color_tex.render(this, buttonColChest.posX + 2, buttonColChest.posY + 2, 12, 12);
		LatCore.Colors.setGLColor(chest.colorText, 200);
		color_tex.render(this, buttonColText.posX + 2, buttonColText.posY + 2, 12, 12);
		LatCore.Colors.setGLColor(0xFFFFDE0C, chest.textGlows ? 255 : 100);
		color_tex.render(this, buttonGlow.posX + 2, buttonGlow.posY + 2, 12, 12);
	}
	
	public void drawText(FastList<String> l)
	{
		textBoxLabel.render(11, 11, 0xCECECE);
		super.drawText(l);
	}
	
	public void onColorSelected(boolean set, int color, int ID)
	{
		if(set)
		{
			if(ID == 0) chest.clientPressButton(TileQChest.BUTTON_COL_CHEST + color, 0);
			else if(ID == 1) chest.clientPressButton(TileQChest.BUTTON_COL_TEXT + color, 0);
		}
		
		mc.displayGuiScreen(this);
		refreshWidgets();
	}
}