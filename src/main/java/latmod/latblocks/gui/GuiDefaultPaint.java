package latmod.latblocks.gui;
import cpw.mods.fml.relauncher.*;
import ftb.lib.SidedDirection;
import ftb.lib.gui.GuiLM;
import ftb.lib.gui.widgets.ItemButtonLM;
import ftb.lib.item.LMInvUtils;
import latmod.ftbu.api.paint.*;
import latmod.ftbu.world.*;
import latmod.latblocks.*;
import latmod.latblocks.net.MessageDefaultPaint;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiDefaultPaint extends GuiLM
{
	public static final ResourceLocation texLoc = LatBlocks.mod.getLocation("textures/gui/defPaint.png");
	
	public final PaintButton[] buttons;
	
	public GuiDefaultPaint(ContainerDefaultPaint c)
	{
		super(c, texLoc);
		xSize = 176;
		ySize = 166;
		hideNEI = false;
		
		buttons = new PaintButton[6];
		buttons[SidedDirection.FRONT.ID] = new PaintButton(this, 62, 35);
		buttons[SidedDirection.BACK.ID] = new PaintButton(this, 112, 35);
		buttons[SidedDirection.TOP.ID] = new PaintButton(this, 62, 12);
		buttons[SidedDirection.BOTTOM.ID] = new PaintButton(this, 62, 58);
		buttons[SidedDirection.LEFT.ID] = new PaintButton(this, 39, 35);
		buttons[SidedDirection.RIGHT.ID] = new PaintButton(this, 85, 35);
		
		LMPlayerClient p = LMWorldClient.inst.getPlayer(c.player);
		
		int[] ai = p.commonPrivateData.getIntArray(LatBlocksGuiHandler.DEF_PAINT_TAG);
		if(ai.length == 12)
		{
			for(int i = 0; i < 6; i++)
			{
				Block b = Block.getBlockById(ai[i * 2 + 0]);
				if(b != Blocks.air) buttons[i].setItem(new ItemStack(b, 1, ai[i * 2 + 1]));
			}
		}
	}
	
	public void addWidgets()
	{
		mainPanel.addAll(buttons);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		for(int i = 0; i < buttons.length; i++)
			buttons[i].renderWidget();
	}
	
	public boolean handleDragNDrop(GuiContainer g, int x, int y, ItemStack is, int b)
	{
		for(int i = 0; i < buttons.length; i++)
		{
			if(buttons[i].mouseOver())
			{
				buttons[i].setItem(LMInvUtils.singleCopy(is));
				is.stackSize = 0;
				return true;
			}
		}
		
		return false;
	}
	
	public static class PaintButton extends ItemButtonLM
	{
		public final GuiDefaultPaint gui;
		
		public PaintButton(GuiDefaultPaint g, int x, int y)
		{
			super(g, x, y, 16, 16);
			gui = g;
		}
		
		public void onButtonPressed(int b)
		{
			ItemStack is = LMInvUtils.singleCopy(gui.getHeldItem());
			
			if(isShiftKeyDown())
			{
				for(int i = 0; i < gui.buttons.length; i++)
					gui.buttons[i].setItem(is);
			}
			else setItem(is);
		}
		
		public void setItem(ItemStack is)
		{
			if(is != null)
			{
				Block b = Block.getBlockFromItem(is.getItem());
				if(b == Blocks.air) return;
				int m = is.getItemDamage();
				if(b.hasTileEntity(m) || b instanceof ICustomPaintBlock || b instanceof INoPaintBlock || b.getRenderType() != 0) return;
			}
			
			super.setItem(is);
			
			title = (is == null) ? "-" : is.getDisplayName();
			
			Paint[] paint = new Paint[6];
			
			for(int i = 0; i < 6; i++)
			{
				ItemStack is1 = gui.buttons[i].item;
				if(is1 != null) paint[i] = new Paint(Block.getBlockFromItem(is1.getItem()), is1.getItemDamage());
			}
			
			new MessageDefaultPaint(paint).sendToServer();
		}
	}
}