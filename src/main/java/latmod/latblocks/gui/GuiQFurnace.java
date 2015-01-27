package latmod.latblocks.gui;
import latmod.core.FastList;
import latmod.core.gui.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileQFurnace;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQFurnace extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/qfurnace.png");
	
	public final TextureCoords
	texFuel = new TextureCoords(texLoc, 176, 0),
	texProgress = new TextureCoords(texLoc, 176, 14);
	
	public TileQFurnace furnace;
	public WidgetLM barFuel, barProgress;
	
	public GuiQFurnace(final ContainerQFurnace c)
	{
		super(c, texLoc);
		furnace = (TileQFurnace)c.inv;
		
		widgets.add(barFuel = new WidgetLM(this, 57, 36, 14, 14)
		{
			public void addMouseOverText(FastList<String> l)
			{
				double d = (furnace.fuel / TileQFurnace.MAX_PROGRESS);
				d = ((int)(d * 10D)) / 10D;
				l.add(d + " items");
			}
		});
		widgets.add(barProgress = new WidgetLM(this, 80, 35, 22, 15)
		{
			public void addMouseOverText(FastList<String> l)
			{
				if(furnace.result != null)
				{
					int d = (int)(furnace.progress * 100D / TileQFurnace.MAX_PROGRESS);
					l.add(d + "%");
				}
			}
		});
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		if(furnace.fuel > 0) barFuel.render(texFuel);
		barProgress.render(texProgress, furnace.progress / TileQFurnace.MAX_PROGRESS, 1D);
	}
}