package latmod.latblocks.gui;
import latmod.ftbu.core.client.LMGuiButtons;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQChest;
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
		
		textBoxLabel = new TextBoxLM(this, 7, 6, 215, 18)
		{
			public void textChanged()
			{
				chest.clientCustomName(textBoxLabel.text);
			}
		};
		
		textBoxLabel.charLimit = 40;
		
		if(chest.customName != null)
			textBoxLabel.text = chest.customName;
		
		buttonSecurity = new ButtonLM(this, 225, 7, 16, 16)
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
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		l.add(textBoxLabel);
		l.add(buttonSecurity);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		buttonSecurity.render(Icons.security[chest.security.level.ID]);
	}
	
	public void drawText(FastList<String> l)
	{
		textBoxLabel.render(11, 11, 0xCECECE);
		super.drawText(l);
	}
}