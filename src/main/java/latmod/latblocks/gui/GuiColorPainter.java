package latmod.latblocks.gui;
import latmod.ftbu.core.EnumDyeColor;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.net.*;
import latmod.ftbu.core.util.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.item.ItemColorPainter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiColorPainter extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/colorPainter.png");
	public static final TextureCoords colTex = new TextureCoords(texLoc, 156, 0, 16, 16);
	
	public final ButtonLM buttons[] = new ButtonLM[16];
	
	public GuiColorPainter(EntityPlayer ep)
	{
		super(new ContainerEmpty(ep, null), texLoc);
		xSize = 156;
		ySize = 80;
		
		for(int i = 0; i < 16; i++)
		{
			final int id = i;
			int x = (i % 8) * 18 + 7;
			int y = (i / 8) * 18 + 6;
			
			buttons[id] = new ButtonLM(this, x, y, 16, 16)
			{
				public void onButtonPressed(int b)
				{
					NBTTagCompound tag = new NBTTagCompound();
					tag.setByte("Dmg", (byte)id);
					LMNetHelper.sendToServer(new MessageClientItemAction(ItemColorPainter.ACTION, tag));
					container.player.closeScreen();
				}
			};
			
			buttons[id].title = EnumDyeColor.VALUES[id].toString();
		}
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		for(int i = 0; i < buttons.length; i++)
			l.add(buttons[i]);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		for(int i = 0; i < buttons.length; i++)
		{
			LatCore.Colors.setGLColor(EnumDyeColor.VALUES[i].color.getRGB(), 255);
			buttons[i].render(colTex);
		}
		
		LatCore.Colors.recolor();
	}
}