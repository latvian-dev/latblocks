package latmod.latblocks.tile;
import cpw.mods.fml.relauncher.*;
import ftb.lib.item.LMInvUtils;
import latmod.ftbu.api.tile.*;
import latmod.ftbu.tile.TileInvLM;
import latmod.ftbu.util.*;
import latmod.ftbu.util.client.LMGuiButtons;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import latmod.lib.MathHelperLM;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileQChest extends TileInvLM implements IGuiTile, ISidedInventory, ISecureTile, IQuartzNetTile
{
	public static final int INV_W = 13;
	public static final int INV_H = 7;
	
	public static final String ITEM_TAG = "ChestData";
	public static final String BUTTON_GLOW = "qchest.glow";
	public static final String BUTTON_COL = "qchest.col";
	public static final String BUTTON_SET_ITEM = "qchest.item";
	public static final float MAX_ANGLE = 2F;
	
	public int colorChest, colorText;
	public boolean textGlows;
	public ItemStack iconItem;
	
	public int playersUsing = 0;
	public float lidAngle = 0F;
	public float prevLidAngle = 0F;
	
	public TileQChest()
	{
		super(INV_W * INV_H);
		colorChest = 0xB5B5B5;
		colorText = 0x222222;
		customName = "Unnamed";
	}
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		colorChest = tag.hasKey("CColor") ? tag.getInteger("CColor") : 0xFFFFFFFF;
		colorText = tag.hasKey("TColor") ? tag.getInteger("TColor") : 0xFFFFFFFF;
		textGlows = tag.getBoolean("Glows");
		iconItem = tag.hasKey("Icon") ? ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Icon")) : null;
	}
	
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setInteger("CColor", colorChest);
		tag.setInteger("TColor", colorText);
		tag.setBoolean("Glows", textGlows);
		
		if(iconItem != null)
		{
			NBTTagCompound itemTag = new NBTTagCompound();
			iconItem.writeToNBT(itemTag);
			tag.setTag("Icon", itemTag);
		}
	}
	
	public void readTileClientData(NBTTagCompound tag)
	{
		super.readTileClientData(tag);
		playersUsing = tag.getByte("PlayersUsing");
		if(playersUsing < 0) playersUsing = 0;
	}
	
	public void writeTileClientData(NBTTagCompound tag)
	{
		super.writeTileClientData(tag);
		if(playersUsing > 0) tag.setByte("PlayersUsing", (byte)playersUsing);
	}
	
	public void onUpdate()
	{
		prevLidAngle = lidAngle;
		
		float inc = 0.2F;
		if(playersUsing > 0) lidAngle += inc;
		else lidAngle -= inc;
		
		lidAngle = MathHelperLM.clampFloat(lidAngle, 0F, MAX_ANGLE);
		
		if(lidAngle > 0F && prevLidAngle == 0F)
			worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		
		if(prevLidAngle == MAX_ANGLE && lidAngle < MAX_ANGLE)
			worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(!isServer()) return true;
		else if(!security.canInteract(ep)) { printOwner(ep); return true; }
		else if(!ep.isSneaking()) LatCoreMC.openGui(ep, this, null);
		else if(LMInvUtils.isWrench(is))
		{
			dropItems = false;
			ItemStack drop = new ItemStack(LatBlocksItems.b_qchest, 1, 0);
			
			NBTTagCompound tag = new NBTTagCompound();
			writeTileData(tag);
			drop.setTagCompound(new NBTTagCompound());
			drop.stackTagCompound.setTag(ITEM_TAG, tag);
			
			LMInvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, drop, 10);
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
		
		//if(isServer())
		{
			if(is.hasTagCompound() && is.stackTagCompound.hasKey(ITEM_TAG))
			{
				readTileData(is.stackTagCompound.getCompoundTag(ITEM_TAG));
				markDirty();
			}
		}
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
	
	public float getLidAngle(float pt)
	{ return MathHelperLM.clampFloat(lidAngle + (lidAngle - prevLidAngle) * pt, 0F, MAX_ANGLE); }
	
	public String getQTitle()
	{ return getInventoryName(); }
	
	public int getQColor()
	{ return colorChest; }
	
	public ItemStack getQIcon()
	{ return iconItem; }
	
	public void onQClicked(EntityPlayer ep, int button)
	{
		if(!isServer()) return;
		if(!security.canInteract(ep))
		{ printOwner(ep); return; }
		LatCoreMC.openGui(ep, this, null);
	}
	
	public void handleButton(String button, int mouseButton, NBTTagCompound data, EntityPlayerMP ep)
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
		else if(button.equals(BUTTON_COL))
		{
			int col = data.getInteger("C");
			int i = data.getByte("ID");
			if(i == 0) colorChest = col;
			else colorText = col;
		}
		else if(button.equals(BUTTON_SET_ITEM))
			iconItem = (data == null) ? null : ItemStack.loadItemStackFromNBT(data);
	}
}
