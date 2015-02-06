package latmod.latblocks.tile;
import latmod.core.*;
import latmod.core.tile.*;
import latmod.latblocks.LatBlocksItems;
import latmod.latblocks.gui.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.*;

public class TileLatChest extends TileInvLM implements IGuiTile
{
	public static final String ITEM_TAG = "ChestData";
	
	public ForgeDirection rotation = ForgeDirection.NORTH;
	
	public TileLatChest()
	{ super(54); }
	
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
			else LatCoreMC.openGui(ep, this, 0);
		}
		else if(isServer() && security.canInteract(ep) && LatCoreMC.isWrench(is))
		{
			onBroken();
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{ return 24D * 24D; }
	
	public Container getContainer(EntityPlayer ep, int ID)
	{ return new ContainerLatChest(ep, this); }
	
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer ep, int ID)
	{ return new GuiLatChest(new ContainerLatChest(ep, this)); }
	
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
	
	public void onBroken()
	{
		dropItems = false;
		ItemStack is = new ItemStack(LatBlocksItems.b_chest, 1, 0);
		
		if(customName != null || InvUtils.getFirstFilledIndex(this, null, -1) != -1)
		{
			NBTTagCompound tag = new NBTTagCompound();
			writeTileData(tag);
			tag.removeTag("CustomName");
			is.setTagCompound(new NBTTagCompound());
			is.stackTagCompound.setTag(ITEM_TAG, tag);
			if(customName != null) is.setStackDisplayName(customName);
		}
		
		InvUtils.dropItem(worldObj, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, is, 10);
		
		super.onBroken();
	}
}