package latmod.latblocks.tile;

import java.util.*;

import latmod.core.LatCoreMC;
import latmod.core.gui.ContainerEmpty;
import latmod.core.tile.*;
import latmod.core.util.MathHelperLM;
import latmod.latblocks.block.Placement;
import latmod.latblocks.gui.GuiNoteBoard;
import mcp.mobius.waila.api.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import cpw.mods.fml.relauncher.*;

public class TileNoteBoard extends TileSinglePaintable implements IGuiTile, IWailaTile.Body
{
	public static final String ACTION_CHANGE_TEXT = "ChangeNoteText";
	
	public final String[] notes = new String[16];
	
	public TileNoteBoard()
	{ Arrays.fill(notes, ""); }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		
		for(int i = 0; i < notes.length; i++)
			notes[i] = tag.getString("Note" + i);
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		
		for(int i = 0; i < notes.length; i++)
			if(!notes[i].isEmpty())
				tag.setString("Note" + i, notes[i]);
	}
	
	public int getIndex(MovingObjectPosition mop)
	{
		if(mop == null) return 0;
		
		double rx = mop.hitVec.xCoord - mop.blockX;
		double ry = mop.hitVec.yCoord - mop.blockY;
		double rz = mop.hitVec.zCoord - mop.blockZ;
		
		double rx1 = 0D;
		double ry1 = 0D;
		
		if(mop.sideHit == Placement.D_UP || mop.sideHit == Placement.D_DOWN)
		{
			rx1 = rx;
			ry1 = rz;
		}
		else
		{
			ry1 = 1D - ry;
			
			if(mop.sideHit == Placement.D_SOUTH)
				rx1 = rx;
			else if(mop.sideHit == Placement.D_NORTH)
				rx1 = 1F - rx;
			if(mop.sideHit == Placement.D_EAST)
				rx1 = 1F - rz;
			else if(mop.sideHit == Placement.D_WEST)
				rx1 = rz;
		}
		
		return (int)(rx1 * 4D) + (int)(ry1 * 4D) * 4;
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float hitX, float hitY, float hitZ)
	{
		if(side == Facing.oppositeSide[blockMetadata])
		{
			if(isServer())
			{
				int index = getIndex(MathHelperLM.getMOPFrom(xCoord, yCoord, zCoord, side, hitX, hitY, hitZ));
				if(index >= 0 && index < 16)
				{
					NBTTagCompound data = new NBTTagCompound();
					data.setByte("I", (byte)index);
					LatCoreMC.openGui(ep, this, data);
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerEmpty(ep, null); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiNoteBoard(new ContainerEmpty(ep, null), this, data.getByte("I")); }
	
	public void onClientAction(EntityPlayer ep, String action, NBTTagCompound data)
	{
		if(action.equals(ACTION_CHANGE_TEXT))
		{
			notes[data.getByte("ID")] = data.getString("Txt");
			markDirty();
		}
		else super.onClientAction(ep, action, data);
	}
	
	public void addWailaBody(IWailaDataAccessor data, IWailaConfigHandler config, List<String> info)
	{
		if(data.getPosition().sideHit == Facing.oppositeSide[blockMetadata])
		{
			int index = getIndex(data.getPosition());
			
			if(index >= 0 && index < 16 && !notes[index].isEmpty())
				info.add(notes[index]);
		}
	}
}