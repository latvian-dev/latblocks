package latmod.latblocks.tile;
import latmod.ftbu.core.*;
import latmod.ftbu.core.client.LMGuiButtons;
import latmod.ftbu.core.tile.*;
import latmod.ftbu.core.util.MathHelperLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class TileQChest extends TileInvLM implements IGuiTile, ISidedInventory, ISecureTile
{
	public static final int INV_W = 13;
	public static final int INV_H = 7;
	
	public static final String ITEM_TAG = "ChestData";
	public static final String BUTTON_GLOW = "qchest.glow";
	public static final String BUTTON_COL_CHEST = "qchest.ccol";
	public static final String BUTTON_COL_TEXT = "qchest.tcol";
	public static final float MAX_ANGLE = (float)(MathHelperLM.HALF_PI / 4D * 3D);
	
	public int colorChest = 0xFFFFFFFF, colorText = 0xFF000000;
	public boolean textGlows;
	public int playersUsing = 0;
	public float lidAngle = 0F;
	
	public TileQChest()
	{ super(INV_W * INV_H); }
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		colorChest = tag.hasKey("CColor") ? tag.getInteger("CColor") : 0xFFFFFFFF;
		colorText = tag.hasKey("TColor") ? tag.getInteger("TColor") : 0xFFFFFFFF;
		textGlows = tag.getBoolean("Glows");
		playersUsing = tag.getByte("PlayersUsing");
		if(playersUsing < 0) playersUsing = 0;
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setInteger("CColor", colorChest);
		tag.setInteger("TColor", colorText);
		tag.setBoolean("Glows", textGlows);
		if(playersUsing > 0) tag.setByte("PlayersUsing", (byte)playersUsing);
	}
	
	public void onUpdate()
	{
		if(!isServer())
		{
			if(playersUsing > 0) lidAngle += MAX_ANGLE / 15F;
			else lidAngle -= MAX_ANGLE / 15F;
			lidAngle = MathHelperLM.clampFloat(lidAngle, 0F, MAX_ANGLE);
			//angles = playersUsing > 0 ? MAX_ANGLE : 0;
		}
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer() && !ep.isSneaking()) LatCoreMC.openGui(ep, this, null);
		else if(isServer() && security.canInteract(ep) && InvUtils.isWrench(is))
		{
			dropItems = false;
			ItemStack drop = new ItemStack(LatBlocksItems.b_qchest, 1, 0);
			
			NBTTagCompound tag = new NBTTagCompound();
			writeTileData(tag);
			drop.setTagCompound(new NBTTagCompound());
			drop.stackTagCompound.setTag(ITEM_TAG, tag);
			
			InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, drop, 10);
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{ return 128D * 128D; }
	
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerQChest(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiQChest(new ContainerQChest(ep, this)); }
	
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);

		if(!worldObj.isRemote)
		{
			setMeta(MathHelperLM.get2DRotation(ep).ordinal());
			
			if(is.hasTagCompound() && is.stackTagCompound.hasKey(ITEM_TAG))
				readTileData(is.stackTagCompound.getCompoundTag(ITEM_TAG));
			
			markDirty();
		}
	}
	
	public void handleButton(String button, int mouseButton, EntityPlayer ep)
	{
		if(button.equals(LMGuiButtons.SECURITY))
		{
			if(ep != null && security.isOwner(ep))
			{
				security.level = (mouseButton == 0) ? security.level.next(LMSecurity.Level.VALUES) : security.level.prev(LMSecurity.Level.VALUES);
				notifyNeighbors();
			}
			else printOwner(ep);
		}
		else if(button.equals(BUTTON_GLOW))
			textGlows = !textGlows;
		else if(button.startsWith(BUTTON_COL_CHEST))
			colorChest = Integer.parseInt(button.substring(BUTTON_COL_CHEST.length()));
		else if(button.startsWith(BUTTON_COL_TEXT))
			colorText = Integer.parseInt(button.substring(BUTTON_COL_TEXT.length()));
	}
	
	public int[] getAccessibleSlotsFromSide(int s)
	{ return security.level.isPublic() ? ALL_SLOTS : NO_SLOTS; }
	
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return security.level.isPublic(); }
	
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return security.level.isPublic(); }
	
	public boolean canPlayerInteract(EntityPlayer ep, boolean breakBlock)
	{ return security.canInteract(ep); }
	
	public void onPlayerNotOwner(EntityPlayer ep, boolean breakBlock)
	{ printOwner(ep); }
	
	public void openInventory()
	{
		playersUsing++;
		if(isServer()) markDirty();
	}

	public void closeInventory()
	{
		playersUsing--;
		if(playersUsing < 0)
			playersUsing = 0;
		if(isServer()) markDirty();
	}
	
	//TODO: Make me smooth!
	public float getLidAngle(float pt)
	{
		return lidAngle;
	}
}
