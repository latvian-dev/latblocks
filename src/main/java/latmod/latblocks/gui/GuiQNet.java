package latmod.latblocks.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.*;
import ftb.lib.gui.GuiLM;
import ftb.lib.gui.widgets.ItemButtonLM;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.*;
import latmod.lib.FastList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiQNet extends GuiLM
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qchest_net.png");
	public static final TextureCoords tex_color = new TextureCoords(tex, 174, 0, 16, 16);
	
	public final TileQTerminal term;
	public final FastList<ButtonQInv> qinvs;
	
	public GuiQNet(ContainerQNet c)
	{
		super(c, tex);
		term = (TileQTerminal)c.inv;
		
		xSize = 174;
		ySize = 167;
		
		qinvs = new FastList<ButtonQInv>();
	}
	
	public void addWidgets()
	{
		qinvs.clear();
		
		FastList<IQuartzNetTile> list = QNetFinder.getTiles(term.getWorldObj(), term.xCoord, term.yCoord, term.zCoord, 32);
		for(IQuartzNetTile inv : list)
			qinvs.add(new ButtonQInv(this, inv));
		
		mainPanel.addAll(qinvs);
		qinvs.sort(null);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		for(ButtonQInv b : qinvs)
			b.renderWidget();
		
		GlStateManager.color(1F, 1F, 1F, 1F);
	}
	
	public static class ButtonQInv extends ItemButtonLM implements Comparable<ButtonQInv>
	{
		public final TileQTerminal term;
		public final IQuartzNetTile inv;
		
		public ButtonQInv(GuiQNet g, IQuartzNetTile i)
		{
			super(g, 16, 8, 16, 16);
			term = g.term;
			inv = i;
			posX += (g.qinvs.size() % 8) * 18;
			posY += (g.qinvs.size() / 8) * 18;
			setItem(inv.getQIconItem());
			title = item.getDisplayName();
		}
		
		public void onButtonPressed(int b)
		{
			inv.onQClicked(gui.container.player, b);
			NBTTagCompound data = new NBTTagCompound();
			TileEntity te = (TileEntity)inv;
			data.setIntArray("Data", new int[] { te.xCoord, te.yCoord, te.zCoord, b });
			term.clientPressButton(TileQTerminal.BUTTON_QNET, b, data);
		}
		
		public int compareTo(ButtonQInv o)
		{ return title.compareToIgnoreCase(o.title); }
	}
}