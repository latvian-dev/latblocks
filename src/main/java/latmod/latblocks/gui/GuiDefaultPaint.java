package latmod.latblocks.gui;
import latmod.ftbu.core.SidedDirection;
import latmod.ftbu.core.gui.*;
import latmod.ftbu.core.inv.LMInvUtils;
import latmod.ftbu.core.net.*;
import latmod.ftbu.core.paint.*;
import latmod.ftbu.core.util.FastList;
import latmod.ftbu.core.world.*;
import latmod.latblocks.LatBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.*;

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
		Paint[] paint = new Paint[6];
		Paint.readFromNBT(p.commonData, LatBlocksNetHandler.DEF_PAINT_TAG, paint);
		
		for(int i = 0; i < 6; i++)
		{
			if(paint[i] != null)
				buttons[i].setItem(new ItemStack(paint[i].block, 1, paint[i].meta));
		}
	}
	
	public void addWidgets(FastList<WidgetLM> l)
	{
		l.addAll(buttons);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		for(int i = 0; i < buttons.length; i++)
			buttons[i].render();
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
			
			LatBlocksNetHandler.openDefPaintGui = false;
			
			for(int i = 0; i < 6; i++)
			{
				ItemStack is1 = gui.buttons[i].item;
				if(is1 == null) LatBlocksNetHandler.currentPaint[i] = null;
				else LatBlocksNetHandler.currentPaint[i] = new Paint(Block.getBlockFromItem(is1.getItem()), is1.getItemDamage());
			}
			
			LMNetHelper.sendToServer(new MessageCustomClientAction(LatBlocks.mod.modID));
			
			title = (item == null) ? "" : item.getDisplayName();
		}
	}
}