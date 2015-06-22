package latmod.latblocks.gui;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.util.FastList;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileNoteBoard;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiNoteBoard extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/noteBoard.png");
	
	public final TileNoteBoard board;
	public final int noteID;
	
	public final ButtonLM buttonAccept;
	public final TextBoxLM titleBox;
	
	public GuiNoteBoard(ContainerEmpty c, TileNoteBoard t, int nid)
	{
		super(c, texLoc);
		xSize = 163;
		ySize = 28;
		board = t;
		noteID = nid;
		
		buttonAccept = new ButtonLM(this, 140, 6, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				NBTTagCompound tag = new NBTTagCompound();
				
				tag.setByte("ID", (byte)noteID);
				tag.setString("Txt", titleBox.text);
				
				board.sendClientAction(TileNoteBoard.ACTION_CHANGE_TEXT, tag);
				container.player.closeScreen();
			}
		};
		
		buttonAccept.title = EnumChatFormatting.GREEN + "Save";
		
		titleBox = new TextBoxLM(this, 6, 5, 132, 18)
		{
			public void returnPressed()
			{
				isSelected = false;
				buttonAccept.onButtonPressed(0);
			}
		};
		
		titleBox.text = board.notes[noteID];
		titleBox.charLimit = 30;
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		l.add(buttonAccept);
		l.add(titleBox);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		buttonAccept.render(Icons.accept);
	}
	
	public void drawText(FastList<String> l)
	{
		titleBox.render(10, 10, 0xFFFFFFFF);
		super.drawText(l);
	}
}