package latmod.latblocks.gui;
import latmod.ftbu.core.gui.*;
import latmod.latblocks.LatBlocks;
import latmod.latblocks.tile.TileNoteBoard;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class GuiNoteBoard extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/noteBoard.png");
	
	public final TileNoteBoard board;
	public final int noteID;
	
	public ButtonLM buttonAccept;
	public TextBoxLM titleBox;
	
	public GuiNoteBoard(ContainerEmpty c, TileNoteBoard t, int nid)
	{
		super(c, texLoc);
		xSize = 163;
		ySize = 28;
		board = t;
		noteID = nid;
		
		widgets.add(buttonAccept = new ButtonLM(this, 140, 6, 16, 16)
		{
			public void onButtonPressed(int b)
			{
				NBTTagCompound tag = new NBTTagCompound();
				
				tag.setByte("ID", (byte)noteID);
				tag.setString("Txt", titleBox.text);
				
				board.sendClientAction(TileNoteBoard.ACTION_CHANGE_TEXT, tag);
				container.player.closeScreen();
			}
		});
		
		buttonAccept.title = EnumChatFormatting.GREEN + "Save";
		
		widgets.add(titleBox = new TextBoxLM(this, 6, 5, 132, 18)
		{
			public void returnPressed()
			{
				isSelected = false;
				buttonAccept.onButtonDoublePressed(0);
			}
		});
		
		titleBox.text = board.notes[noteID];
		titleBox.charLimit = 30;
	}
	
	public void drawGuiContainerBackgroundLayer(float f, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(f, mx, my);
		
		buttonAccept.render(Icons.accept);
	}
	
	public void drawText(int mx, int my)
	{
		super.drawText(mx, my);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		titleBox.render(10, 10, 0xFFFFFFFF);
	}
}