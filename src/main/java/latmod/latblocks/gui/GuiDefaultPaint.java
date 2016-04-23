package latmod.latblocks.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.api.gui.GuiLM;
import ftb.lib.api.gui.widgets.ItemButtonLM;
import ftb.lib.api.item.LMInvUtils;
import latmod.latblocks.LatBlockEventHandler;
import latmod.latblocks.api.*;
import latmod.latblocks.net.MessageDefaultPaint;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

@SideOnly(Side.CLIENT)
public class GuiDefaultPaint extends GuiLM
{
	public final List<PaintButton> buttons;
	
	public GuiDefaultPaint(ContainerDefaultPaint c)
	{
		super(null, new ResourceLocation("latblocks", "textures/gui/defPaint.png"));
		mainPanel.width = 176;
		mainPanel.height = 166;
		
		buttons = new ArrayList<>();
		buttons.add(new PaintButton(this, 62, 35)); // FRONT
		buttons.add(new PaintButton(this, 112, 35)); // BACK
		buttons.add(new PaintButton(this, 62, 12)); // TOP
		buttons.add(new PaintButton(this, 62, 58)); // BOTTOM
		buttons.add(new PaintButton(this, 39, 35)); // LEFT
		buttons.add(new PaintButton(this, 85, 35)); // RIGHT
		
		LatBlockEventHandler.LatBlockProperties props = LatBlockEventHandler.LatBlockProperties.get(c.player);
		
		if(props != null)
		{
			for(int i = 0; i < 6; i++)
			{
				buttons.get(i).setItem((props.paint[i] == null) ? null : props.paint[i].getItemStack());
			}
		}
	}
	
	@Override
	public void addWidgets()
	{
		mainPanel.addAll(buttons);
	}
	
	@Override
	public void drawBackground()
	{
		super.drawBackground();
		
		for(PaintButton b : buttons)
			b.renderWidget();
	}
	
	public class PaintButton extends ItemButtonLM
	{
		public PaintButton(GuiDefaultPaint g, int x, int y)
		{
			super(g, x, y, 16, 16);
		}
		
		@Override
		public void onButtonPressed(int b)
		{
			ItemStack is = LMInvUtils.singleCopy(getHeldItem());
			
			if(isShiftKeyDown())
			{
				for(PaintButton button : buttons)
					button.setItem(is);
			}
			else setItem(is);
		}
		
		@Override
		public void setItem(ItemStack is)
		{
			if(is != null)
			{
				Block b = Block.getBlockFromItem(is.getItem());
				if(b == Blocks.air) return;
				int m = is.getItemDamage();
				if(b.hasTileEntity(m) || b instanceof ICustomPaintBlock || b instanceof INoPaintBlock || b.getRenderType() != 0)
					return;
			}
			
			super.setItem(is);
			
			title = (is == null) ? "-" : is.getDisplayName();
			
			Paint[] paint = new Paint[6];
			
			for(int i = 0; i < 6; i++)
			{
				ItemStack is1 = buttons.get(i).item;
				if(is1 != null) paint[i] = new Paint(Block.getBlockFromItem(is1.getItem()), is1.getItemDamage());
			}
			
			new MessageDefaultPaint(paint).sendToServer();
		}
	}
}