package latmod.latblocks.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.TextureCoords;
import ftb.lib.api.client.FTBLibClient;
import ftb.lib.api.gui.GuiLM;
import ftb.lib.api.gui.widgets.WidgetLM;
import latmod.latblocks.tile.TileQFurnace;
import net.minecraft.util.ResourceLocation;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiQFurnace extends GuiLM
{
	public static final ResourceLocation texLoc = new ResourceLocation("latblocks", "textures/gui/qfurnace.png");
	public static final TextureCoords texFuel = new TextureCoords(texLoc, 176, 0, 14, 14);
	public static final TextureCoords texProgress = new TextureCoords(texLoc, 176, 14, 22, 15);
	
	public final TileQFurnace furnace;
	public final WidgetLM barFuel, barProgress;
	
	public GuiQFurnace(final ContainerQFurnace c)
	{
		super(null, texLoc);
		furnace = (TileQFurnace) c.inv;
		
		barFuel = new WidgetLM(this, 57, 36, texFuel.widthI(), texFuel.heightI())
		{
			public void addMouseOverText(List<String> l)
			{
				double d = (furnace.fuel / TileQFurnace.MAX_PROGRESS);
				d = ((int) (d * 10D)) / 10D;
				l.add(d + " items");
			}
		};
		
		barProgress = new WidgetLM(this, 80, 35, texProgress.widthI(), texProgress.heightI())
		{
			public void addMouseOverText(List<String> l)
			{
				if(furnace.result != null)
				{
					int d = (int) (furnace.progress * 100D / TileQFurnace.MAX_PROGRESS);
					l.add(d + "%");
				}
			}
		};
	}
	
	public void addWidgets()
	{
		mainPanel.add(barFuel);
		mainPanel.add(barProgress);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		if(furnace.fuel > 0) barFuel.render(texFuel);
		if(furnace.progress > 0)
		{
			FTBLibClient.setTexture(texture);
			double d = furnace.progress / TileQFurnace.MAX_PROGRESS;
			drawTexturedRectD(mainPanel.getAX() + barProgress.posX, mainPanel.getAY() + barProgress.posY, zLevel, texProgress.width * d, texProgress.height, texProgress.minU, texProgress.minV, texProgress.minU + (texProgress.maxU - texProgress.minU) * d, texProgress.maxV);
		}
	}
}