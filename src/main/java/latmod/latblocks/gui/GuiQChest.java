package latmod.latblocks.gui;
import latmod.ftbu.core.client.LMGuiButtons;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.inv.LMInvUtils;
import latmod.ftbu.core.util.*;
import latmod.ftbu.mod.client.gui.GuiSelectColor;
import latmod.latblocks.*;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQChest extends GuiLM implements GuiSelectColor.ColorSelectorCallback
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qchest.png");
	
	public final TileQChest chest;
	public final TextBoxLM textBoxLabel;
	public final ButtonLM buttonSecurity, buttonColChest, buttonColText;
	public final ItemButtonLM buttonSetItem, buttonGlow;
	
	private static final ItemStack lampOn = new ItemStack((Block)Block.blockRegistry.getObject("lit_redstone_lamp"));
	private static final ItemStack lampOff = new ItemStack(Blocks.redstone_lamp);
	
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
				GuiSelectColor.displayGui(GuiQChest.this, chest.colorChest, 0, false);
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
				GuiSelectColor.displayGui(GuiQChest.this, chest.colorText, 1, false);
			}
			
			public void addMouseOverText(FastList<String> l)
			{ l.add(LatCore.Colors.getHex(chest.colorText)); }
		};
		
		buttonGlow = new ItemButtonLM(this, 15, 216, 16, 16)
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
					setItem(null);
				else
				{
					ItemStack is = gui.getHeldItem();
					if(is != null) setItem(is);
				}
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add((item == null) ? Blocks.air.getLocalizedName() : item.getDisplayName());
			}
			
			public void setItem(ItemStack is)
			{
				super.setItem(is);
				
				chest.iconItem = is;
				
				if(chest.iconItem != null)
				{
					NBTTagCompound data = new NBTTagCompound();
					chest.iconItem.writeToNBT(data);
					chest.clientPressButton(TileQChest.BUTTON_SET_ITEM, 0, data);
				}
				else
				{
					chest.clientPressButton(TileQChest.BUTTON_SET_ITEM, 0, null);
				}
			}
		};
		
		buttonSetItem.setItem(chest.iconItem);
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		buttonColChest.title = LatBlocksItems.b_qchest.getLocalizedName();
		buttonColText.title = "ABC";
		buttonGlow.setItem(chest.textGlows ? lampOn : lampOff);
		
		l.add(textBoxLabel);
		l.add(buttonSecurity);
		l.add(buttonColChest);
		l.add(buttonColText);
		l.add(buttonGlow);
		l.add(buttonSetItem);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		buttonSecurity.render(Icons.security[chest.security.level.ID]);
		LatCore.Colors.setGLColor(chest.colorChest, 250);
		buttonColChest.render(Icons.color_blank);
		LatCore.Colors.setGLColor(chest.colorText, 250);
		buttonColText.render(Icons.color_blank);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		buttonGlow.render();
		buttonSetItem.render();
		if(buttonSetItem.item == null)
			buttonSetItem.render(Icons.remove_gray);
	}
	
	public void drawText(FastList<String> l)
	{
		textBoxLabel.render(11, 11, 0xCECECE);
		super.drawText(l);
	}
	
	public void onColorSelected(GuiSelectColor.ColorSelected c)
	{
		if(c.set)
		{
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("C", c.color);
			data.setByte("ID", (byte)c.ID);
			chest.clientPressButton(TileQChest.BUTTON_COL, 0, data);
		}
		
		mc.displayGuiScreen(this);
		refreshWidgets();
	}
	
	public boolean handleDragNDrop(GuiContainer g, int x, int y, ItemStack is, int b)
	{
		if(is != null && buttonSetItem.isAt(x - guiLeft, y - guiTop))
		{
			ItemStack is1 = LMInvUtils.singleCopy(is);
			is.stackSize = 0;
			buttonSetItem.setItem(is1);
			return true;
		}
		
		return false;
	}
}