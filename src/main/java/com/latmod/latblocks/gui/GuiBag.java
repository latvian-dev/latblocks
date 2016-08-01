package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.MouseButton;
import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.client.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.client.gui.GuiIcons;
import com.feed_the_beast.ftbl.api.client.gui.GuiLM;
import com.feed_the_beast.ftbl.api.client.gui.GuiLang;
import com.feed_the_beast.ftbl.api.client.gui.widgets.ButtonLM;
import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.gui.GuiSelectColor;
import com.feed_the_beast.ftbl.util.TextureCoords;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.net.MessageChangeColor;
import com.latmod.lib.LMColor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LatvianModder on 11.07.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiBag extends GuiLM
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(LatBlocks.MOD_ID, "textures/gui/bag.png");
    public static final TextureCoords TAB_OFF = new TextureCoords(TEXTURE, 174, 0, 21, 16, 256, 256);
    public static final TextureCoords TAB_ON = new TextureCoords(TEXTURE, 174, 16, 21, 16, 256, 256);

    public class TabButton extends ButtonLM
    {
        public byte tabIndex;

        public TabButton(int x, int y, byte i)
        {
            super(x, y, 21, 16);
            tabIndex = i;
            title = "#" + (i + 1);
        }

        @Override
        public void onClicked(@Nonnull GuiLM gui, @Nonnull MouseButton b)
        {
            playClickSound();
            mc.playerController.sendEnchantPacket(container.windowId, tabIndex);
        }

        @Override
        public void renderWidget(GuiLM gui)
        {
            render(tabIndex == container.bag.getCurrentTab() ? TAB_ON : TAB_OFF);
        }
    }

    public final ContainerBag container;
    public final List<TabButton> tabButtons;
    public final ButtonLM buttonColor, buttonPrivacy;

    public GuiBag(ContainerBag c)
    {
        super(174, 210);
        container = c;

        tabButtons = new ArrayList<>();

        for(byte t = 0; t < container.bag.getTabCount(); t++)
        {
            tabButtons.add(new TabButton(7 + 25 * t, 7, t));
        }

        buttonColor = new ButtonLM(133, 7, 16, 16)
        {
            @Override
            public void onClicked(@Nonnull GuiLM gui, @Nonnull MouseButton b)
            {
                GuiLM.playClickSound();

                LMColor.RGB col = new LMColor.RGB();
                col.setRGBA(container.bag.getColor());

                GuiSelectColor.display(null, col, (id, obj) ->
                {
                    MessageChangeColor m = new MessageChangeColor();
                    m.color = obj.hashCode();
                    m.sendToServer();
                    //container.bag.setColor(m.color);
                    mc.displayGuiScreen(GuiBag.this.getWrapper());
                });
            }
        };

        buttonColor.title = GuiLang.label_color.translate();

        buttonPrivacy = new ButtonLM(151, 7, 16, 16)
        {
            @Override
            public void onClicked(@Nonnull GuiLM gui, @Nonnull MouseButton button)
            {
                GuiLM.playClickSound();
                mc.playerController.sendEnchantPacket(container.windowId, button.isLeft() ? 10 : 11);
            }

            @Override
            public void addMouseOverText(GuiLM gui, List<String> l)
            {
                l.add(title);
                l.add(container.bag.getPrivacyLevel().langKey.translate());
            }
        };

        buttonPrivacy.title = EnumPrivacyLevel.enumLangKey.translate();
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
        FTBLibClient.setTexture(TEXTURE);
        GuiScreen.drawModalRectWithCustomSizedTexture(getAX(), getAY(), 0F, 0F, width, height, 256F, 256F);

        buttonColor.render(GuiIcons.color_rgb);
        buttonPrivacy.render(container.bag.getPrivacyLevel().getIcon());
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}
