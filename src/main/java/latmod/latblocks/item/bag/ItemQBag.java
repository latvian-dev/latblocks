package latmod.latblocks.item.bag;

import latmod.ftbu.core.LMSecurity;
import latmod.ftbu.core.inv.LMInvUtils;
import latmod.ftbu.core.item.IClientActionItem;
import latmod.ftbu.core.util.*;
import latmod.ftbu.mod.FTBU;
import latmod.latblocks.*;
import latmod.latblocks.item.ItemLB;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class ItemQBag extends ItemLB implements IClientActionItem
{
	public static final String ACTION_SET_COLOR = "latblocks.qbag.col";
	public static final String ACTION_SET_NAME = "latblocks.qbag.name";
	public static final String ACTION_SET_SECURITY = "latblocks.qbag.security";
	
	public static final String TAG_DATA = "Bag";
	public static final String TAG_ID = "ID";
	public static final String TAG_SECURITY = "Security";
	public static final String TAG_COLOR = "Col";
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_mask;
	
	public ItemQBag(String s)
	{
		super(s);
		setMaxStackSize(1);
		requiresMultipleRenderPasses = true;
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "LSL", "LCL", "LLL",
				'L', Items.leather,
				'S', Items.string,
				'C', LatBlocksItems.b_qchest);
	}
	
	public static NBTTagCompound getData(ItemStack is)
	{ return (is.hasTagCompound() && is.stackTagCompound.hasKey(TAG_DATA)) ? is.stackTagCompound.getCompoundTag(TAG_DATA) : null; }
	
	public static void setData(ItemStack is, NBTTagCompound tag)
	{
		if(!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());
		is.stackTagCompound.setTag(TAG_DATA, tag);
	}
	
	public static int getBagID(ItemStack is)
	{
		NBTTagCompound tag = getData(is);
		return (tag == null) ? 0 : tag.getInteger(TAG_ID);
	}
	
	public static int getColor(ItemStack is)
	{
		NBTTagCompound tag = getData(is);
		return (tag == null) ? 0xFFFFFFFF : tag.getInteger(TAG_COLOR);
	}
	
	public static LMSecurity getSecurity(ItemStack is)
	{
		NBTTagCompound tag = getData(is);
		
		if(tag != null)
		{
			LMSecurity s = new LMSecurity(null);
			s.readFromNBT(tag, TAG_SECURITY);
			return s;
		}
		
		return null;
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep)
	{
		if(w.isRemote) return is;
		
		NBTTagCompound tag = getData(is);
		
		if(tag != null)
		{
			int ID = getBagID(is);
			
			if(ID <= 0)
			{
				is.stackSize--;
				LMInvUtils.giveItem(ep, new ItemStack(this));
				return is;
			}
			
			LMSecurity s = getSecurity(is);
			
			if(!s.canInteract(ep))
			{
				s.printOwner(ep);
				return is;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("ID", ID);
			LatBlocksGuiHandler.instance.openGui(ep, LatBlocksGuiHandler.QUARTZ_BAG, data);
		}
		else
		{
			tag = new NBTTagCompound();
			tag.setInteger(TAG_ID, ++QBagDataHandler.lastBagID);
			QBagDataHandler.getItems(QBagDataHandler.lastBagID);
			
			LMSecurity s = new LMSecurity(ep);
			s.writeToNBT(tag, TAG_SECURITY);
			tag.setInteger(TAG_COLOR, w.rand.nextInt());
			
			//ep.openContainer.detectAndSendChanges();
			
			setData(is, tag);
		}
		
		return is;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		itemIcon = ir.registerIcon(mod.assets + itemName);
		icon_mask = ir.registerIcon(mod.assets + itemName + "_mask");
	}
	
	public int getRenderPasses(int m)
	{ return 2; }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int i, int r)
	{ return (r == 0) ? icon_mask : itemIcon; }
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int r)
	{
		if(r == 0)
		{
			NBTTagCompound tag = getData(is);
			if(tag != null) return LatCore.Colors.getRGBA(tag.getInteger(TAG_COLOR), 255);
			return 0xFFFFFFFF;
		}
		
		return 0xFFFFFFFF;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack is, EntityPlayer ep, FastList<String> l)
	{
		NBTTagCompound tag = getData(is);
		
		if(tag != null)
		{
			LMSecurity s = getSecurity(is);
			
			if(s.level.isPublic()) l.add("" + s.getOwner());
			else l.add(s.getOwner() + " [" + s.level.getText() + "]");
			l.add("Color: " + LatCore.Colors.getHex(getColor(is)));
			
			if(FTBU.proxy.isShiftDown())
				l.add("BagID: " + getBagID(is));
		}
	}
	
	public ItemStack onClientAction(ItemStack is, EntityPlayer ep, String action, NBTTagCompound data)
	{
		NBTTagCompound tag = getData(is);
		
		if(tag != null)
		{
			LMSecurity s = new LMSecurity(null);
			s.readFromNBT(tag, TAG_SECURITY);
			
			if(s.isOwner(ep))
			{
				if(action.equals(ACTION_SET_COLOR))
				{
					tag.setInteger(TAG_COLOR, data.getInteger("C"));
					setData(is, tag);
				}
				else if(action.equals(ACTION_SET_NAME))
				{
					String n = data.getString("N");
					if(n.isEmpty()) LMInvUtils.removeDisplayName(is);
					else is.setStackDisplayName(n);
				}
				else if(action.equals(ACTION_SET_SECURITY))
				{
					s.level = s.level.next(LMSecurity.Level.VALUES);
					s.writeToNBT(tag, TAG_SECURITY);
					setData(is, tag);
				}
			}
			else s.printOwner(ep);
		}
		
		return is;
	}
	
	public int getEntityLifespan(ItemStack is, World w)
	{ return Integer.MAX_VALUE; }
	
	public boolean hasCustomEntity(ItemStack is)
	{ return getData(is) != null; }
	
	public Entity createEntity(World w, Entity e, ItemStack is)
	{
		EntityItemQBag ei = new EntityItemQBag(w, e.posX, e.posY, e.posZ, is);
		ei.motionX = e.motionX;
		ei.motionY = e.motionY;
		ei.motionZ = e.motionZ;
		return ei;
	}
}