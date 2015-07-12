package latmod.latblocks.gui;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQFurnace;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQFurnace extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/qfurnace.png");
	public static final TextureCoords texFuel = new TextureCoords(texLoc, 176, 0, 14, 14);
	public static final TextureCoords texProgress = new TextureCoords(texLoc, 176, 14, 22, 15);
	
	public final TileQFurnace furnace;
	public final WidgetLM barFuel, barProgress;
	
	public GuiQFurnace(final ContainerQFurnace c)
	{
		super(c, texLoc);
		furnace = (TileQFurnace)c.inv;
		
		barFuel = new WidgetLM(this, 57, 36, texFuel.width, texFuel.height)
		{
			public void addMouseOverText(FastList<String> l)
			{
				double d = (furnace.fuel / TileQFurnace.MAX_PROGRESS);
				d = ((int)(d * 10D)) / 10D;
				l.add(d + " items");
			}
		};
		
		barProgress = new WidgetLM(this, 80, 35, texProgress.width, texProgress.height)
		{
			public void addMouseOverText(FastList<String> l)
			{
				if(furnace.result != null)
				{
					int d = (int)(furnace.progress * 100D / TileQFurnace.MAX_PROGRESS);
					l.add(d + "%");
				}
			}
		};
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		l.add(barFuel);
		l.add(barProgress);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		if(furnace.fuel > 0) barFuel.render(texFuel);
		if(furnace.progress > 0)
		{
			setTexture(texture);
			double d = furnace.progress / TileQFurnace.MAX_PROGRESS;
			drawTexturedRectD(guiLeft + barProgress.posX, guiTop + barProgress.posY, zLevel, texProgress.width * d, texProgress.height, texProgress.minU, texProgress.minV, texProgress.minU + (texProgress.maxU - texProgress.minU) * d, texProgress.maxV);
		}
	}
}