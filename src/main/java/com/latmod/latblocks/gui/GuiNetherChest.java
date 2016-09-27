package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.gui.IGui;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.lib.gui.ButtonLM;
import com.feed_the_beast.ftbl.lib.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.feed_the_beast.ftbl.lib.gui.GuiLM;
import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import com.latmod.latblocks.LatBlocks;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        super(174, 188);
        container = c;

        buttonPrevPage = new ButtonLM(151, 7, 16, 16, GuiLang.BUTTON_PREV_PAGE.translate())
        {
            @Override
            public void onClicked(IGui gui, IMouseButton button)
            {
                GuiHelper.playClickSound();
                mc.thePlayer.connection.sendPacket(new CPacketEnchantItem(container.windowId, 0));
            }
        };

        buttonNextPage = new ButtonLM(151, 79, 16, 16, GuiLang.BUTTON_NEXT_PAGE.translate())
        {
            @Override
            public void onClicked(IGui gui, IMouseButton button)
            {
                GuiHelper.playClickSound();
                mc.thePlayer.connection.sendPacket(new CPacketEnchantItem(container.windowId, 1));
            }
        };
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
        int ax = getAX();
        int ay = getAY();
        GuiScreen.drawModalRectWithCustomSizedTexture(ax, ay, 0F, 0F, getWidth(), getHeight(), 256F, 256F);
        GuiHelper.drawCenteredString(getFont(), Integer.toString(container.tile.currentPage + 1), ax + 159, ay + 37, 0xFFFFFFFF);
        GuiHelper.drawCenteredString(getFont(), Integer.toString(container.getMaxPages()), ax + 159, ay + 66, 0xFFFFFFFF);

        if(getMouseWheel() != 0 && isMouseOver(this))
        {
            if(getMouseWheel() > 0)
            {
                mc.thePlayer.connection.sendPacket(new CPacketEnchantItem(container.windowId, 0));
            }
            else
            {
                mc.thePlayer.connection.sendPacket(new CPacketEnchantItem(container.windowId, 1));
            }
        }
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}
