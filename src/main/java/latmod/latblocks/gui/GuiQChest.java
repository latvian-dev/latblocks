package latmod.latblocks.gui;
import latmod.ftbu.core.InvUtils;
import latmod.ftbu.core.client.LMGuiButtons;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.*;
import latmod.ftbu.mod.client.gui.GuiSelectColor;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQChest extends GuiLM implements GuiSelectColor.ColorSelectorCallback
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qchest.png");
	public static final TextureCoords color_tex = new TextureCoords(tex, 248, 0, 8, 8);
	
	public final TileQChest chest;
	public final TextBoxLM textBoxLabel;
	public final ButtonLM buttonSecurity, buttonColChest, buttonColText, buttonGlow;
	public final ItemButtonLM buttonSetItem;
	
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
		
		buttonSecurity = new ButtonLM(this, 217, 168, 16, 16)
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
		
		buttonColChest = new ButtonLM(this, 15, 168, 16, 16)
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
		
		buttonColText = new ButtonLM(this, 15, 192, 16, 16)
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
		
		buttonGlow = new ButtonLM(this, 15, 216, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				chest.clientPressButton(TileQChest.BUTTON_GLOW, b);
				refreshWidgets();
			}
		};
		
		buttonSetItem = new ItemButtonLM(this, 217, 192, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				
				if(GuiScreen.isShiftKeyDown())
					chest.iconItem = null;
				else if(gui.container.player.inventory.getItemStack() != null)
					chest.iconItem = InvUtils.singleCopy(gui.container.player.inventory.getItemStack());
				
				setItem(chest.iconItem);
				
				if(chest.iconItem != null)
				{
					NBTTagCompound data = new NBTTagCompound();
					chest.iconItem.writeToNBT(data);
					chest.clientPressButton(TileQChest.BUTTON_SET_ITEM, b, data);
				}
				else
				{
					chest.clientPressButton(TileQChest.BUTTON_SET_ITEM, b, null);
				}
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add("Icon");
				l.add((item == null) ? "No item selected" : item.getDisplayName());
			}
		};
		
		buttonSetItem.setItem(chest.iconItem);
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
		l.add(buttonSetItem);
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
		GL11.glColor4f(1F, 1F, 1F, 1F);
		buttonSetItem.render();
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
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("C", color);
			data.setByte("ID", (byte)ID);
			chest.clientPressButton(TileQChest.BUTTON_COL, 0, data);
		}
		
		mc.displayGuiScreen(this);
		refreshWidgets();
	}
}