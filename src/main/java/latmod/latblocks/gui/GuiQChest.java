package latmod.latblocks.gui;
import java.util.ArrayList;

import latmod.core.client.LMGuiButtons;
import latmod.core.gui.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQChest;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQChest extends GuiLM
{
	public TileQChest chest;
	public TextBoxLM textBoxLabel;
	public ButtonLM buttonSecurity;
	
	public GuiQChest(ContainerQChest c)
	{
		super(c, LatBlocks.mod.getLocation("textures/gui/qchest.png"));
		
		xSize = 248;
		ySize = 247;
		chest = (TileQChest)c.inv;
		
		widgets.add(textBoxLabel = new TextBoxLM(this, 7, 6, 215, 18)
		{
			public void textChanged()
			{
				chest.clientCustomName(textBoxLabel.text);
			}
		});
		
		textBoxLabel.charLimit = 40;
		
		if(chest.customName != null)
			textBoxLabel.text = chest.customName;
		
		widgets.add(buttonSecurity = new ButtonLM(this, 225, 7, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				playClickSound();
				chest.clientPressButton(LMGuiButtons.SECURITY, b);
			}
		});
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		buttonSecurity.render(Icons.security[chest.security.level.ID]);
	}
	
	public void drawScreen(int mx, int my, float f)
	{
		super.drawScreen(mx, my, f);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		textBoxLabel.render(11, 11, 0xCECECE);
		
		ArrayList<String> al = new ArrayList<String>();
		
		if(buttonSecurity.mouseOver(mx, my))
			al.add(chest.security.level.getText());
		
		if(!al.isEmpty()) drawHoveringText(al, mx, my, fontRendererObj);
	}
}