package latmod.latblocks.gui;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiQChestNet extends GuiLM
{
	public static final ResourceLocation tex = LatBlocks.mod.getLocation("textures/gui/qchest_net.png");
	public static final TextureCoords tex_color = new TextureCoords(tex, 174, 0, 16, 16);
	
	public final TileQChest chest;
	public final FastList<ButtonQInv> qinvs;
	
	public GuiQChestNet(ContainerQChestNet c)
	{
		super(c, tex);
		chest = (TileQChest)c.inv;
		
		xSize = 174;
		ySize = 167;
		
		qinvs = new FastList<ButtonQInv>();
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		qinvs.clear();
		
		FastList<IQuartzNetTile> list = QNetFinder.getTiles(chest.getWorldObj(), chest.xCoord, chest.yCoord, chest.zCoord, 32);
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
		public final TileQChest chest;
		public final IQuartzNetTile inv;
		
		public ButtonQInv(GuiQChestNet g, IQuartzNetTile i)
		{
			super(g, 16, 8, 16, 16);
			chest = g.chest;
			inv = i;
			posX += (g.qinvs.size() % 8) * 18;
			posY += (g.qinvs.size() / 8) * 18;
			title = inv.getQTitle();
			setItem(inv.getQIcon());
		}
		
		public void onButtonPressed(int b)
		{
			NBTTagCompound data = new NBTTagCompound();
			TileEntity te = (TileEntity)inv;
			data.setIntArray("Data", new int[] { te.xCoord, te.yCoord, te.zCoord, b });
			chest.sendClientAction(TileQChest.BUTTON_QNET_CLICK, data);
		}
		
		public int compareTo(ButtonQInv o)
		{ return inv.getQTitle().compareTo(o.inv.getQTitle()); }
	}
}