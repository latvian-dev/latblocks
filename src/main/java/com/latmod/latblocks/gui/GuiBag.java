package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.MouseButton;
import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.client.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.client.gui.GuiIcons;
import com.feed_the_beast.ftbl.api.client.gui.GuiLM;
import com.feed_the_beast.ftbl.api.client.gui.GuiLang;
import com.feed_the_beast.ftbl.api.client.gui.widgets.ButtonLM;
import com.feed_the_beast.ftbl.api.client.gui.widgets.TextBoxLM;
import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.gui.GuiSelectColor;
import com.feed_the_beast.ftbl.util.TextureCoords;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.net.MessageChangeColor;
import com.latmod.latblocks.net.MessageChangeDisplayName;
import com.latmod.latblocks.net.MessageChangePrivacy;
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
    public static final TextureCoords TAB_OFF = new TextureCoords(TEXTURE, 174, 0, 32, 10, 256, 256);
    public static final TextureCoords TAB_ON = new TextureCoords(TEXTURE, 174, 10, 32, 10, 256, 256);

    public class TabButton extends ButtonLM
    {
        public byte tabIndex;

        public TabButton(double x, double y, byte i)
        {
            super(x, y, 32, 10);
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
    public final TextBoxLM textBox;

    public GuiBag(ContainerBag c)
    {
        container = c;
        width = 174D;
        height = 220D;

        tabButtons = new ArrayList<>();

        for(byte t = 0; t < container.bag.getTabCount(); t++)
        {
            tabButtons.add(new TabButton(5 + 33 * t, 26, t));
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

                MessageChangePrivacy m = new MessageChangePrivacy();

                if(button.isLeft())
                {
                    m.level = (container.bag.getPrivacyLevel().ordinal() + 1) % EnumPrivacyLevel.VALUES.length;
                }
                else
                {
                    m.level = container.bag.getPrivacyLevel().ordinal() - 1;
                    if(m.level < 0)
                    {
                        m.level = EnumPrivacyLevel.VALUES.length - 1;
                    }
                }

                m.sendToServer();
            }

            @Override
            public void addMouseOverText(GuiLM gui, List<String> l)
            {
                l.add(title);
                l.add(container.bag.getPrivacyLevel().langKey.translate());
            }
        };

        buttonPrivacy.title = EnumPrivacyLevel.enumLangKey.translate();

        textBox = new TextBoxLM(7, 7, 124, 16)
        {
            @Override
            public void returnPressed()
            {
                MessageChangeDisplayName m = new MessageChangeDisplayName();
                m.name = getText();

                if(!m.name.isEmpty())
                {
                    m.sendToServer();
                }
            }
        };
    }

    @Override
    public void addWidgets()
    {
        addAll(tabButtons);
        add(buttonColor);
        add(buttonPrivacy);
        add(textBox);
    }

    @Override
    public void drawBackground()
    {
        FTBLibClient.setTexture(TEXTURE);
        GuiScreen.drawModalRectWithCustomSizedTexture((int) getAX(), (int) getAY(), 0F, 0F, (int) width, (int) height, 256F, 256F);

        buttonColor.render(GuiIcons.color_rgb);
        buttonPrivacy.render(container.bag.getPrivacyLevel().getIcon());
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}
