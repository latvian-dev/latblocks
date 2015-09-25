package latmod.latblocks.gui;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import latmod.core.util.*;
import latmod.ftbu.api.callback.*;
import latmod.ftbu.inv.LMInvUtils;
import latmod.ftbu.util.client.LMGuiButtons;
import latmod.ftbu.util.gui.*;
import latmod.latblocks.*;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiQChest extends GuiLM implements IColorCallback, IClientActionGui
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qchest.png");
	
	public final TileQChest chest;
	public final TextBoxLM textBoxLabel;
	public final ButtonLM buttonSecurity, buttonColChest, buttonColText;
	public final ItemButtonLM buttonSetItem, buttonGlow;
	
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
				ColorSelected.displayGui(GuiQChest.this, chest.colorChest, 0, false);
			}
			
			public void addMouseOverText(FastList<String> l)
			{
				l.add(title);
				l.add(LMColorUtils.getHex(chest.colorChest));
			}
		};
		
		buttonColText = new ButtonLM(this, 15, 192, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				ColorSelected.displayGui(GuiQChest.this, chest.colorText, 1, false);
			}
			
			public void addMouseOverText(FastList<String> l)
			{ l.add(LMColorUtils.getHex(chest.colorText)); }
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
				if(item != null) l.add(item.getDisplayName());
			}
			
			public void setItem(ItemStack is)
			{
				chest.iconItem = LMInvUtils.singleCopy(is);
				super.setItem(chest.iconItem);
				
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
	
	public void addWidgets()
	{
		buttonColChest.title = LatBlocksItems.b_qchest.getLocalizedName();
		buttonColText.title = "ABC";
		buttonGlow.setItem(new ItemStack(chest.textGlows ? Items.glowstone_dust : Items.gunpowder));
		
		mainPanel.add(textBoxLabel);
		mainPanel.add(buttonSecurity);
		mainPanel.add(buttonColChest);
		mainPanel.add(buttonColText);
		mainPanel.add(buttonGlow);
		mainPanel.add(buttonSetItem);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		buttonSecurity.render(GuiIcons.security[chest.security.level.ID]);
		LMColorUtils.setGLColor(chest.colorChest, 250);
		buttonColChest.render(GuiIcons.color_blank);
		LMColorUtils.setGLColor(chest.colorText, 250);
		buttonColText.render(GuiIcons.color_blank);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		buttonGlow.render();
		buttonSetItem.render();
		
		if(buttonSetItem.item == null)
			buttonSetItem.render(GuiIcons.cancel);
	}
	
	public void drawText(FastList<String> l)
	{
		textBoxLabel.render(11, 11, 0xCECECE);
		super.drawText(l);
	}
	
	public void onColorSelected(ColorSelected c)
	{
		if(c.set)
		{
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("C", c.color);
			data.setByte("ID", (byte)c.ID.hashCode());
			chest.clientPressButton(TileQChest.BUTTON_COL, 0, data);
		}
		
		mc.displayGuiScreen(this);
		refreshWidgets();
	}
	
	public boolean handleDragNDrop(GuiContainer g, int x, int y, ItemStack is, int b)
	{
		if(is != null && buttonSetItem.mouseOver())
		{
			ItemStack is1 = LMInvUtils.singleCopy(is);
			is.stackSize = 0;
			buttonSetItem.setItem(is1);
			return true;
		}
		
		return false;
	}
	
	public void onClientDataChanged()
	{
		refreshWidgets();
	}
}