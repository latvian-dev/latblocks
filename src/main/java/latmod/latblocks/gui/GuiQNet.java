package latmod.latblocks.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.TextureCoords;
import ftb.lib.api.client.GlStateManager;
import ftb.lib.api.gui.GuiContainerLM;
import ftb.lib.api.gui.widgets.ItemButtonLM;
import latmod.latblocks.api.IQuartzNetTile;
import latmod.latblocks.tile.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.*;

@SideOnly(Side.CLIENT)
public class GuiQNet extends GuiContainerLM
{
	public static final ResourceLocation tex = new ResourceLocation("latblocks", "textures/gui/qchest_net.png");
	public static final TextureCoords tex_color = new TextureCoords(tex, 174, 0, 16, 16);
	
	public final TileQTerminal term;
	public final List<ButtonQInv> qinvs;
	
	public GuiQNet(ContainerQNet c)
	{
		super(c, tex);
		term = (TileQTerminal) c.inv;
		
		mainPanel.width = 174;
		mainPanel.height = 167;
		
		qinvs = new ArrayList<>();
	}
	
	public void addWidgets()
	{
		qinvs.clear();
		
		List<IQuartzNetTile> list = QNetFinder.getTiles(term.getWorldObj(), term.xCoord, term.yCoord, term.zCoord, 32);
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
			inv.onQClicked(((GuiQNet) gui).container.player, b);
			NBTTagCompound data = new NBTTagCompound();
			TileEntity te = (TileEntity) inv;
			data.setIntArray("Data", new int[] {te.xCoord, te.yCoord, te.zCoord, b});
			term.clientPressButton(TileQTerminal.BUTTON_QNET, b, data);
		}
		
		public int compareTo(ButtonQInv o)
		{ return title.compareToIgnoreCase(o.title); }
	}
}