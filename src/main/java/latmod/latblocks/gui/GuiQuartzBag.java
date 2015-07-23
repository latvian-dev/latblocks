package latmod.latblocks.gui;
import latmod.ftbu.core.LMSecurity;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.net.*;
import latmod.ftbu.core.util.*;
import latmod.ftbu.mod.client.gui.GuiSelectColor;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.item.bag.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQuartzBag extends GuiLM implements GuiSelectColor.ColorSelectorCallback
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qbag.png");
	public static final TextureCoords color_tex = new TextureCoords(tex, 230, 0, 16, 16);
	
	public final InvQBag bag;
	public final TextBoxLM textBoxLabel;
	public final ButtonLM buttonSecurity, buttonColChest;
	
	public LMSecurity security;
	public int color;
	
	public GuiQuartzBag(ContainerQuartzBag c)
	{
		super(c, tex);
		
		xSize = 230;
		ySize = 229;
		bag = (InvQBag)c.inv;
		
		security = ItemQBag.getSecurity(bag.getItem());
		color = ItemQBag.getColor(bag.getItem());
		
		textBoxLabel = new TextBoxLM(this, 7, 6, 175, 18)
		{
			public void textChanged()
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("N", textBoxLabel.text.trim());
				LMNetHelper.sendToServer(new MessageClientItemAction(ItemQBag.ACTION_SET_NAME, tag));
			}
		};
		
		textBoxLabel.charLimit = 45;
		
		if(bag.getItem().hasDisplayName())
			textBoxLabel.text = bag.getItem().getDisplayName();
		
		buttonSecurity = new ButtonLM(this, 208, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				security.level = security.level.next(LMSecurity.Level.VALUES);
				LMNetHelper.sendToServer(new MessageClientItemAction(ItemQBag.ACTION_SET_SECURITY, null));
			}
			
			public void addMouseOverText(FastList<String> l)
			{ l.add(security.level.getText()); }
		};
		
		buttonColChest = new ButtonLM(this, 186, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				GuiSelectColor.displayGui(GuiQuartzBag.this, color, 0, false);
			}
			
			public void addMouseOverText(FastList<String> l)
			{ l.add(title); }
		};
		
		buttonColChest.title = LatCore.Colors.getHex(color);
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		if(security.isOwner(bag.player))
		{
			l.add(textBoxLabel);
			l.add(buttonSecurity);
			l.add(buttonColChest);
		}
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		buttonSecurity.render(Icons.security[security.level.ID]);
		LatCore.Colors.setGLColor(color, 200);
		buttonColChest.render(color_tex);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Icons.close.render(this, 35 + container.player.inventory.currentItem * 18, 205);
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
			color = c.color;
			buttonColChest.title = LatCore.Colors.getHex(color);
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("C", color);
			LMNetHelper.sendToServer(new MessageClientItemAction(ItemQBag.ACTION_SET_COLOR, data));
		}
		
		mc.displayGuiScreen(this);
		refreshWidgets();
	}
}