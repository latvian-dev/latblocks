package latmod.latblocks.tile;
import latmod.core.*;
import latmod.core.client.LMGuiButtons;
import latmod.core.tile.*;
import latmod.core.util.MathHelperLM;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class TileQChest extends TileInvLM implements IGuiTile, ISidedInventory, ISecureTile
{
	public static final String ITEM_TAG = "ChestData";
	
	public ForgeDirection rotation = ForgeDirection.NORTH;
	
	public TileQChest()
	{ super(7 * 13); }
	
	public boolean rerenderBlock()
	{ return true; }
	
	public void onLoaded()
	{
		super.onLoaded();
		getMeta();
		rotation = ForgeDirection.VALID_DIRECTIONS[blockMetadata];
	}
	
	public boolean onRightClick(EntityPlayer ep, ItemStack is, int side, float x, float y, float z)
	{
		if(isServer() && !ep.isSneaking())
		{
			if(!security.canInteract(ep))
				printOwner(ep);
			else LatCoreMC.openGui(ep, this, null);
		}
		else if(isServer() && security.canInteract(ep) && InvUtils.isWrench(is))
		{
			dropItems = false;
			ItemStack drop = new ItemStack(LatBlocksItems.b_qchest, 1, 0);
			
			if(customName != null || InvUtils.getFirstFilledIndex(this, null, -1) != -1)
			{
				NBTTagCompound tag = new NBTTagCompound();
				writeTileData(tag);
				tag.removeTag("CustomName");
				drop.setTagCompound(new NBTTagCompound());
				drop.stackTagCompound.setTag(ITEM_TAG, tag);
				if(customName != null) drop.setStackDisplayName(customName);
			}
			
			InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, drop, 10);
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{ return 24D * 24D; }
	
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
			
			if(is.hasDisplayName())
				customName = is.getDisplayName();
			else
				customName = null;
			
			markDirty();
		}
	}
	
	public void handleButton(String button, int mouseButton, EntityPlayer ep)
	{
		if(button.equals(LMGuiButtons.SECURITY))
		{
			if(ep != null && security.isOwner(ep))
				security.level = (mouseButton == 0) ? security.level.next(LMSecurity.Level.VALUES) : security.level.prev(LMSecurity.Level.VALUES);
			else printOwner(ep);
		}
	}
	
	public int[] getAccessibleSlotsFromSide(int s)
	{ return security.level.isPublic() ? ALL_SLOTS : NO_SLOTS; }
	
	public boolean canInsertItem(int i, ItemStack is, int s)
	{ return security.level.isPublic(); }
	
	public boolean canExtractItem(int i, ItemStack is, int s)
	{ return security.level.isPublic(); }
}