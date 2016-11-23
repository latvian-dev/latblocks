package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.gui.IGui;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.lib.client.TextureCoords;
import com.feed_the_beast.ftbl.lib.config.PropertyColor;
import com.feed_the_beast.ftbl.lib.gui.ButtonLM;
import com.feed_the_beast.ftbl.lib.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.feed_the_beast.ftbl.lib.gui.GuiIcons;
import com.feed_the_beast.ftbl.lib.gui.GuiLM;
import com.feed_the_beast.ftbl.lib.gui.GuiLang;
import com.feed_the_beast.ftbl.lib.gui.misc.GuiSelectors;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.net.MessageChangeBagColor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 11.07.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiBag extends GuiLM
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(LatBlocks.MOD_ID, "textures/gui/bag.png");
    private static final TextureCoords TAB_OFF = TextureCoords.fromCoords(TEXTURE, 174, 0, 21, 16, 256, 256);
    private static final TextureCoords TAB_ON = TextureCoords.fromCoords(TEXTURE, 174, 16, 21, 16, 256, 256);

    private class TabButton extends ButtonLM
    {
        private byte tabIndex;

        private TabButton(int x, int y, byte i)
        {
            super(x, y, 21, 16, "#" + (i + 1));
            tabIndex = i;
        }

        @Override
        public void onClicked(IGui gui, IMouseButton b)
        {
            GuiHelper.playClickSound();
            mc.playerController.sendEnchantPacket(container.windowId, 200 + tabIndex);
        }

        @Override
        public void renderWidget(IGui gui)
        {
            render(tabIndex == container.bag.currentTab ? TAB_ON : TAB_OFF);
        }
    }

    private final ContainerBag container;
    private final List<TabButton> tabButtons;
    private final ButtonLM buttonColor, buttonPrivacy;

    public GuiBag(ContainerBag c)
    {
        super(174, 210);
        container = c;

        tabButtons = new ArrayList<>();

        for(byte t = 0; t < container.bag.inv.size(); t++)
        {
            tabButtons.add(new TabButton(7 + 25 * t, 7, t));
        }

        buttonColor = new ButtonLM(133, 7, 16, 16, GuiLang.LABEL_COLOR.translate())
        {
            @Override
            public void onClicked(IGui gui, IMouseButton b)
            {
                GuiHelper.playClickSound();

                GuiSelectors.selectColor(new PropertyColor(container.bag.colorID), (value, set) ->
                {
                    if(set)
                    {
                        new MessageChangeBagColor((byte) value.getInt()).sendToServer();
                    }

                    GuiBag.this.openGui();
                });
            }
        };

        buttonPrivacy = new ButtonLM(151, 7, 16, 16, EnumPrivacyLevel.ENUM_LANG_KEY.translate())
        {
            @Override
            public void onClicked(IGui gui, IMouseButton button)
            {
                GuiHelper.playClickSound();
                mc.playerController.sendEnchantPacket(container.windowId, button.isLeft() ? 210 : 211);
            }

            @Override
            public void addMouseOverText(IGui gui, List<String> l)
            {
                l.add(getTitle(gui));
                l.add(container.bag.privacyLevel.getLangKey().translate());
            }
        };
    }

    @Override
    public void addWidgets()
    {
        addAll(tabButtons);
        add(buttonColor);
        add(buttonPrivacy);
    }

    @Override
    public void drawBackground()
    {
        mc.getTextureManager().bindTexture(TEXTURE);
        GuiScreen.drawModalRectWithCustomSizedTexture(getAX(), getAY(), 0F, 0F, getWidth(), getHeight(), 256F, 256F);

        buttonColor.render(GuiIcons.COLOR_RGB);
        buttonPrivacy.render(container.bag.privacyLevel.getIcon());
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}
