package latmod.latblocks.gui;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQChestNet extends GuiLM
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qchest_net.png");
	public static final TextureCoords tex_color = new TextureCoords(tex, 156, 0, 16, 16);
	
	public final IQuartzNetTile parent;
	public final TileEntity tile;
	
	public final FastList<ButtonQInv> qinvs;
	
	public GuiQChestNet(IQuartzNetTile i)
	{
		super(null, tex);
		parent = i;
		tile = (TileEntity)parent;
		
		xSize = 156;
		ySize = 82;
		
		qinvs = new FastList<ButtonQInv>();
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		qinvs.clear();
		
		FastList<IQuartzNetTile> list = QNetFinder.getTiles(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, 32);
		for(IQuartzNetTile inv : list)
			qinvs.add(new ButtonQInv(this, inv));
		
		l.addAll(qinvs);
		qinvs.sort(null);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		for(ButtonQInv b : qinvs)
		{
			LatCore.Colors.setGLColor(b.inv.getQColor(), 255);
			b.render(tex_color);
			b.render();
		}
		
		LatCore.Colors.recolor();
	}
	
	public static class ButtonQInv extends ItemButtonLM implements Comparable<ButtonQInv>
	{
		public final IQuartzNetTile inv;
		
		public ButtonQInv(GuiQChestNet g, IQuartzNetTile i)
		{
			super(g, 7, 6, 16, 16);
			inv = i;
			posX += (g.qinvs.size() % 8) * 18;
			posY += (g.qinvs.size() / 8) * 18;
			title = inv.getQTitle();
			setItem(inv.getQIcon());
		}
		
		public void onButtonPressed(int b)
		{ inv.openQGui(gui.container.player); }
		
		public int compareTo(ButtonQInv o)
		{ return inv.getQTitle().compareTo(o.inv.getQTitle()); }
	}
}