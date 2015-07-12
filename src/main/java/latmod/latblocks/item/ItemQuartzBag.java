package latmod.latblocks.item;

import latmod.ftbu.core.*;
import latmod.ftbu.core.item.IClientActionItem;
import latmod.ftbu.core.util.*;
import latmod.latblocks.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class ItemQuartzBag extends ItemLB implements IClientActionItem
{
	public static final String ACTION_SET_COLOR = "latblocks.qbag.col";
	public static final String ACTION_SET_NAME = "latblocks.qbag.name";
	public static final String ACTION_SET_SECURITY = "latblocks.qbag.security";
	
	public static final String TAG_DATA = "Bag";
	public static final String TAG_SECURITY = "Security";
	public static final String TAG_COLOR = "Col";
	public static final String TAG_INV = "Inv";
	
	@SideOnly(Side.CLIENT)
	public IIcon icon_mask;
	
	public ItemQuartzBag(String s)
	{
		super(s);
		setMaxStackSize(1);
		requiresMultipleRenderPasses = true;
	}
	
	public void loadRecipes()
	{
		mod.recipes.addRecipe(new ItemStack(this), "LDL", "LCL", "LLL",
				'L', Items.leather,
				'D', ODItems.DIAMOND,
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
	
	public static int getColor(ItemStack is)
	{
		NBTTagCompound tag = getData(is);
		if(tag != null) return tag.getInteger(TAG_COLOR);
		return 0xFFFFFFFF;
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
		
		if(tag == null)
		{
			tag = new NBTTagCompound();
			
			LMSecurity s = new LMSecurity(ep);
			s.writeToNBT(tag, TAG_SECURITY);
			tag.setInteger(TAG_COLOR, w.rand.nextInt());
			
			ep.openContainer.detectAndSendChanges();
			
			setData(is, tag);
		}
		else
		{
			LMSecurity s = getSecurity(is);
			
			if(!s.canInteract(ep))
			{
				s.printOwner(ep);
				return is;
			}
			
			LatBlocksGuiHandler.instance.openGui(ep, LatBlocksGuiHandler.QUARTZ_BAG, null);
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
					is.setStackDisplayName(data.getString("N"));
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
	
	public boolean hasCustomEntity(ItemStack stack)
	{ return true; }
	
	public Entity createEntity(World w, Entity e, ItemStack is)
	{
		EntityBagItem ei = new EntityBagItem(w, e.posX, e.posY, e.posZ, is);
		ei.motionX = e.motionX;
		ei.motionY = e.motionY;
		ei.motionZ = e.motionZ;
		return ei;
	}
	
	public static class EntityBagItem extends EntityItem
	{
		public EntityBagItem(World w)
		{ super(w); }
		
		public EntityBagItem(World w, double x, double y, double z, ItemStack is)
		{
			super(w, x, y, z, is);
			delayBeforeCanPickup = 40;
		}
		
		public boolean isEntityInvulnerable()
		{ return true; }
	}
}