package latmod.latblocks.item;

import com.feed_the_beast.ftbl.api.FTBLibCapabilities;
import com.feed_the_beast.ftbl.api.paint.PaintHelper;
import com.feed_the_beast.ftbl.api.paint.PainterItemStorage;
import com.feed_the_beast.ftbl.util.MathHelperMC;
import latmod.latblocks.LatBlocks;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 16.05.2016.
 */
public class ItemPainter extends ItemLB
{
    private class PainterCap implements ICapabilitySerializable<NBTTagInt>
    {
        private PainterItemStorage cap;

        public PainterCap()
        {
            cap = new PainterItemStorage()
            {
                @Override
                public void damagePainter(ItemStack is, EntityPlayer player)
                {
                    if(!infinite)
                    {
                        super.damagePainter(is, player);
                    }
                }
            };
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return capability == FTBLibCapabilities.PAINTER_ITEM;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            return (capability == FTBLibCapabilities.PAINTER_ITEM) ? (T) cap : null;
        }

        @Override
        public NBTTagInt serializeNBT()
        {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagInt nbt)
        {
            cap.deserializeNBT(nbt);
        }
    }

    public final boolean infinite;

    public ItemPainter(boolean i)
    {
        infinite = i;
        setMaxStackSize(1);

        if(!infinite)
        {
            setMaxDamage(250);
        }
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new PainterCap();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void loadModels()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(LatBlocks.MOD_ID, "painter"), "variant=" + getRegistryName().getResourcePath()));
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(ItemStack is, EntityPlayer ep, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return PaintHelper.onItemUse(is, ep, MathHelperMC.rayTrace(pos, facing, hitX, hitY, hitZ));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack is, World w, EntityPlayer ep, EnumHand hand)
    {
        return PaintHelper.onItemRightClick(is, ep);
    }
}
