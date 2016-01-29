package latmod.latblocks.api;

import cpw.mods.fml.relauncher.*;
import net.minecraft.util.IIcon;

/**
 * Created by LatvianModder on 29.01.2016.
 */
public interface ICustomPaintBlockIcon
{
	@SideOnly(Side.CLIENT)
	IIcon getCustomPaintIcon(int side, Paint p);
}
