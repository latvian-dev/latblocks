package latmod.latblocks.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.client.*;
import ftb.lib.api.gui.*;
import ftb.lib.api.gui.callback.*;
import ftb.lib.api.gui.widgets.*;
import ftb.lib.api.item.LMInvUtils;
import latmod.latblocks.LatBlocksLang;
import latmod.latblocks.tile.TileQChest;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiQChest extends GuiContainerLM implements IColorCallback, IClientActionGui
{
	public static final ResourceLocation tex = new ResourceLocation("latblocks", "textures/gui/qchest.png");
	
	public final TileQChest chest;
	public final TextBoxLM textBoxLabel;
	public final ButtonLM buttonSecurity, buttonColChest, buttonColText;
	public final ItemButtonLM buttonSetItem, buttonGlow;
	
	public GuiQChest(ContainerQChest c)
	{
		super(c, tex);
		
		mainPanel.width = 248;
		mainPanel.height = 247;
		chest = (TileQChest) c.inv;
		
		textBoxLabel = new TextBoxLM(this, 7, 6, 235, 18)
		{
			@Override
			public void textChanged()
			{
				chest.clientCustomName(textBoxLabel.getText());
			}
		};
		
		textBoxLabel.charLimit = 45;
		textBoxLabel.textRenderX = 5;
		textBoxLabel.textRenderY = 5;
		textBoxLabel.textColor = 0xFFCECECE;
		
		if(chest.hasCustomInventoryName()) textBoxLabel.setText(chest.getName());
		
		buttonSecurity = new ButtonLM(this, 217, 168, 16, 16)
		{
			@Override
			public void onButtonPressed(int b)
			{
				FTBLibClient.playClickSound();
				chest.clientPressButton("security", b);
			}
			
			@Override
			public void addMouseOverText(List<String> l)
			{
				l.add(chest.security.level.lang.format());
			}
		};
		
		buttonColChest = new ButtonLM(this, 15, 168, 16, 16)
		{
			@Override
			public void onButtonPressed(int b)
			{
				FTBLibClient.playClickSound();
				LMGuis.displayColorSelector(GuiQChest.this, chest.colorChest, 0, false);
			}
			
			@Override
			public void addMouseOverText(List<String> l)
			{
				l.add(title);
				l.add(chest.colorChest.toString());
			}
		};
		
		buttonColChest.title = LatBlocksLang.chest_color.format();
		
		buttonColText = new ButtonLM(this, 15, 192, 16, 16)
		{
			@Override
			public void onButtonPressed(int b)
			{
				FTBLibClient.playClickSound();
				LMGuis.displayColorSelector(GuiQChest.this, chest.colorText, 1, false);
			}
			
			@Override
			public void addMouseOverText(List<String> l)
			{
				l.add(title);
				l.add(chest.colorText.toString());
			}
		};
		
		buttonColText.title = LatBlocksLang.text_color.format();
		
		buttonGlow = new ItemButtonLM(this, 15, 216, 16, 16)
		{
			@Override
			public void onButtonPressed(int b)
			{
				FTBLibClient.playClickSound();
				chest.clientPressButton(TileQChest.BUTTON_GLOW, b);
				refreshWidgets();
			}
		};
		
		buttonGlow.title = LatBlocksLang.text_glow.format();
		
		buttonSetItem = new ItemButtonLM(this, 217, 192, 16, 16)
		{
			@Override
			public void onButtonPressed(int b)
			{
				FTBLibClient.playClickSound();
				
				if(GuiScreen.isShiftKeyDown()) setItem(null);
				else
				{
					ItemStack is = FTBLibClient.mc.thePlayer.inventory.getItemStack();
					if(is != null) setItem(is);
				}
			}
			
			@Override
			public void addMouseOverText(List<String> l)
			{
				l.add(title);
				if(item != null) l.add(item.getDisplayName());
			}
			
			@Override
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
		
		buttonSetItem.title = LatBlocksLang.chest_icon.format();
		buttonSetItem.setItem(chest.iconItem);
	}
	
	@Override
	public void addWidgets()
	{
		buttonGlow.setItem(new ItemStack(chest.textGlows ? Items.glowstone_dust : Items.gunpowder));
		
		mainPanel.add(textBoxLabel);
		mainPanel.add(buttonSecurity);
		mainPanel.add(buttonColChest);
		mainPanel.add(buttonColText);
		mainPanel.add(buttonGlow);
		mainPanel.add(buttonSetItem);
	}
	
	@Override
	public void drawBackground()
	{
		super.drawBackground();
		buttonSecurity.render(chest.security.level.getIcon());
		FTBLibClient.setGLColor(chest.colorChest.color(), 250);
		buttonColChest.render(GuiIcons.color_blank);
		FTBLibClient.setGLColor(chest.colorText.color(), 250);
		buttonColText.render(GuiIcons.color_blank);
		GlStateManager.color(1F, 1F, 1F, 1F);
		
		buttonGlow.renderWidget();
		buttonSetItem.renderWidget();
		
		if(buttonSetItem.item == null) buttonSetItem.render(GuiIcons.cancel);
	}
	
	@Override
	public void drawText(List<String> l)
	{
		textBoxLabel.renderWidget();
		super.drawText(l);
	}
	
	@Override
	public void onColorSelected(ColorSelected c)
	{
		if(c.set)
		{
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("C", c.color.color());
			data.setByte("ID", (byte) c.ID.hashCode());
			chest.clientPressButton(TileQChest.BUTTON_COL, 0, data);
		}
		
		mc.displayGuiScreen(this);
		refreshWidgets();
	}
	
	@Override
	public void onClientDataChanged()
	{
		refreshWidgets();
	}
}