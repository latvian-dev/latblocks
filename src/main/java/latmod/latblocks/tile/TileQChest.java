package latmod.latblocks.tile;

import cpw.mods.fml.relauncher.*;
import ftb.lib.*;
import ftb.lib.api.item.LMInvUtils;
import ftb.lib.api.tile.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import latmod.lib.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileQChest extends TileInvLM implements IGuiTile, ISidedInventory, ISecureTile//FIXME:, IQuartzNetTile
{
	public static final int INV_W = 13;
	public static final int INV_H = 7;
	
	public static final String ITEM_TAG = "ChestData";
	public static final String BUTTON_GLOW = "qchest.glow";
	public static final String BUTTON_COL = "qchest.col";
	public static final String BUTTON_SET_ITEM = "qchest.item";
	public static final float MAX_ANGLE = 2F;
	
	public LMColor.RGB colorChest, colorText;
	public boolean textGlows;
	public ItemStack iconItem;
	
	public int playersUsing = 0;
	public float lidAngle = 0F;
	public float prevLidAngle = 0F;
	
	public TileQChest()
	{
		super(INV_W * INV_H);
		colorChest = new LMColor.RGB();
		colorChest.setRGBA(0xFFFFFFFF);
		
		colorText = new LMColor.RGB();
		colorText.setRGBA(0xFF222222);
		setName("Unnamed");
	}
	
	@Override
	public boolean rerenderBlock()
	{ return true; }
	
	@Override
	public void readTileData(NBTTagCompound tag)
	{
		super.readTileData(tag);
		colorChest.setRGBA(tag.getInteger("CColor"));
		colorText.setRGBA(tag.getInteger("TColor"));
		textGlows = tag.getBoolean("Glows");
		iconItem = tag.hasKey("Icon") ? ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Icon")) : null;
	}
	
	@Override
	public void writeTileData(NBTTagCompound tag)
	{
		super.writeTileData(tag);
		tag.setInteger("CColor", colorChest.color());
		tag.setInteger("TColor", colorText.color());
		tag.setBoolean("Glows", textGlows);
		
		if(iconItem != null)
		{
			NBTTagCompound itemTag = new NBTTagCompound();
			iconItem.writeToNBT(itemTag);
			tag.setTag("Icon", itemTag);
		}
	}
	
	@Override
	public void readTileClientData(NBTTagCompound tag)
	{
		super.readTileClientData(tag);
		playersUsing = tag.getByte("PlayersUsing");
		if(playersUsing < 0) playersUsing = 0;
	}
	
	@Override
	public void writeTileClientData(NBTTagCompound tag)
	{
		super.writeTileClientData(tag);
		if(playersUsing > 0) tag.setByte("PlayersUsing", (byte) playersUsing);
	}
	
	@Override
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
	
	@Override
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(!isServer()) return true;
		else if(!security.canInteract(ep))
		{
			printOwner(ep);
			return true;
		}
		else if(!ep.isSneaking()) FTBLib.openGui(ep, this, null);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{ return 128D * 128D; }
	
	@Override
	public Container getContainer(EntityPlayer ep, NBTTagCompound data)
	{ return new ContainerQChest(ep, this); }
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, NBTTagCompound data)
	{ return new GuiQChest(new ContainerQChest(ep, this)); }
	
	@Override
	public void onPlacedBy(EntityPlayer ep, ItemStack is)
	{
		super.onPlacedBy(ep, is);
		
		FTBLib.dev_logger.info("Placed by " + ep.getDisplayName() + " @ " + (worldObj.isRemote ? Side.CLIENT : Side.SERVER));
		
		//if(isServer())
		{
			if(is.hasTagCompound() && is.stackTagCompound.hasKey(ITEM_TAG))
			{
				readTileData(is.stackTagCompound.getCompoundTag(ITEM_TAG));
				markDirty();
			}
		}
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int s)
	{ return security.level.isPublic() ? ALL_SLOTS : NO_SLOTS; }
	
	@Override
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return security.level.isPublic(); }
	
	@Override
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return security.level.isPublic(); }
	
	@Override
	public boolean canPlayerInteract(EntityPlayer ep, boolean breakBlock)
	{ return security.canInteract(ep); }
	
	@Override
	public void onPlayerNotOwner(EntityPlayer ep, boolean breakBlock)
	{ printOwner(ep); }
	
	@Override
	public void openInventory()
	{
		playersUsing++;
		if(isServer()) markDirty();
	}
	
	@Override
	public void closeInventory()
	{
		playersUsing--;
		if(playersUsing < 0) playersUsing = 0;
		if(isServer()) markDirty();
	}
	
	public float getLidAngle(float pt)
	{ return MathHelperLM.clampFloat(lidAngle + (lidAngle - prevLidAngle) * pt, 0F, MAX_ANGLE); }
	
	public ItemStack getQIconItem()
	{
		ItemStack is;
		
		if(iconItem == null)
		{
			is = new ItemStack(LatBlocksItems.b_qchest, 1, 0);
			NBTTagCompound tag = new NBTTagCompound();
			writeTileData(tag);
			is.setTagCompound(new NBTTagCompound());
			is.stackTagCompound.setTag(ITEM_TAG, tag);
			return is;
		}
		
		is = LMInvUtils.singleCopy(iconItem);
		is.setStackDisplayName(new ItemStack(LatBlocksItems.b_qchest, 1, 0).getDisplayName());
		
		return is;
	}
	
	public void onQClicked(EntityPlayer ep, int button)
	{
		if(!isServer()) return;
		if(!security.canInteract(ep))
		{
			printOwner(ep);
			return;
		}
		FTBLib.openGui(ep, this, null);
	}
	
	@Override
	public void handleButton(String button, int mouseButton, NBTTagCompound data, EntityPlayerMP ep)
	{
		if(button.equals("security"))
		{
			if(ep != null && security.isOwner(ep))
			{
				security.level = (mouseButton == 0) ? security.level.next(PrivacyLevel.VALUES_3) : security.level.prev(PrivacyLevel.VALUES_3);
				notifyNeighbors();
			}
			else printOwner(ep);
		}
		else if(button.equals(BUTTON_GLOW)) textGlows = !textGlows;
		else if(button.equals(BUTTON_COL))
		{
			int col = 0xFF000000 | data.getInteger("C");
			int i = data.getByte("ID");
			if(i == 0) colorChest.setRGBA(col);
			else colorText.setRGBA(col);
		}
		else if(button.equals(BUTTON_SET_ITEM)) iconItem = (data == null) ? null : ItemStack.loadItemStackFromNBT(data);
	}
}
