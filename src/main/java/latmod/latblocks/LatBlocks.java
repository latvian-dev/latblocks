package latmod.latblocks;

import com.feed_the_beast.ftbl.util.CreativeTabLM;
import com.feed_the_beast.ftbl.util.LMMod;
import latmod.latblocks.block.LBBlocks;
import latmod.latblocks.item.LBItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by LatvianModder on 12.05.2016.
 */
@Mod(modid = LatBlocks.MOD_ID, name = "LatBlocks", version = "2.0.0")
public class LatBlocks
{
    public static final String MOD_ID = "latblocks";

    @Mod.Instance(MOD_ID)
    public static LatBlocks inst;

    @SidedProxy(serverSide = "latmod.latblocks.LatBlocksCommon", clientSide = "latmod.latblocks.client.LatBlocksClient")
    public static LatBlocksCommon proxy;

    public static LMMod mod;
    public static CreativeTabLM tab, tabGlowium;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        mod = LMMod.create(MOD_ID);
        tab = new CreativeTabLM("latblocks");
        tabGlowium = new CreativeTabLM("latblocks.glowium");

        LBBlocks.init();
        LBItems.init();

        proxy.preInit();
        mod.onPostLoaded();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
        mod.loadRecipes();
    }
}
