package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.MouseButton;
import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.client.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.client.gui.GuiLM;
import com.feed_the_beast.ftbl.api.client.gui.GuiLang;
import com.feed_the_beast.ftbl.api.client.gui.widgets.ButtonLM;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Created by LatvianModder on 11.07.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiNetherChest extends GuiLM
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(LatBlocks.MOD_ID, "textures/gui/nether_chest.png");

    public final ContainerNetherChest container;
    public final ButtonLM buttonPrevPage, buttonNextPage;

    public GuiNetherChest(ContainerNetherChest c)
    {
        container = c;
        width = 174D;
        height = 188D;

        buttonPrevPage = new ButtonLM(151, 7, 16, 16)
        {
            @Override
            public void onClicked(@Nonnull GuiLM gui, @Nonnull MouseButton button)
            {
                playClickSound();
                mc.thePlayer.connection.sendPacket(new CPacketEnchantItem(container.windowId, 0));
            }
        };

        buttonPrevPage.title = GuiLang.button_prev_page.translate();

        buttonNextPage = new ButtonLM(151, 79, 16, 16)
        {
            @Override
            public void onClicked(@Nonnull GuiLM gui, @Nonnull MouseButton button)
            {
                playClickSound();
                mc.thePlayer.connection.sendPacket(new CPacketEnchantItem(container.windowId, 1));
            }
        };

        buttonNextPage.title = GuiLang.button_next_page.translate();
    }

    @Override
    public void addWidgets()
    {
        add(buttonPrevPage);
        add(buttonNextPage);
    }

    @Override
    public void drawBackground()
    {
        FTBLibClient.setTexture(TEXTURE);
        int ax = (int) getAX();
        int ay = (int) getAY();
        GuiScreen.drawModalRectWithCustomSizedTexture(ax, ay, 0F, 0F, (int) width, (int) height, 256F, 256F);
        drawCenteredString(font, Integer.toString(container.tile.currentPage + 1), ax + 159, ay + 37, 0xFFFFFFFF);
        drawCenteredString(font, Integer.toString(container.getMaxPages()), ax + 159, ay + 66, 0xFFFFFFFF);
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}