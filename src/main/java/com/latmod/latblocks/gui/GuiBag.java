package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.gui.GuiIcons;
import com.feed_the_beast.ftbl.api.gui.GuiLM;
import com.feed_the_beast.ftbl.api.gui.GuiLang;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.api.gui.widgets.ButtonLM;
import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import com.feed_the_beast.ftbl.gui.GuiSelectColor;
import com.latmod.latblocks.LatBlocks;
import com.latmod.latblocks.net.MessageChangeColor;
import com.latmod.lib.LMColor;
import com.latmod.lib.TextureCoords;
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
    public static final TextureCoords TAB_OFF = TextureCoords.fromCoords(TEXTURE, 174, 0, 21, 16, 256, 256);
    public static final TextureCoords TAB_ON = TextureCoords.fromCoords(TEXTURE, 174, 16, 21, 16, 256, 256);

    public class TabButton extends ButtonLM
    {
        public byte tabIndex;

        public TabButton(int x, int y, byte i)
        {
            super(x, y, 21, 16, "#" + (i + 1));
            tabIndex = i;
        }

        @Override
        public void onClicked(@Nonnull GuiLM gui, @Nonnull IMouseButton b)
        {
            playClickSound();
            mc.playerController.sendEnchantPacket(container.windowId, tabIndex);
        }

        @Override
        public void renderWidget(GuiLM gui)
        {
            render(tabIndex == container.bag.currentTab ? TAB_ON : TAB_OFF);
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

        for(byte t = 0; t < container.bag.inv.size(); t++)
        {
            tabButtons.add(new TabButton(7 + 25 * t, 7, t));
        }

        buttonColor = new ButtonLM(133, 7, 16, 16, GuiLang.label_color.translate())
        {
            @Override
            public void onClicked(@Nonnull GuiLM gui, @Nonnull IMouseButton b)
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

        buttonPrivacy = new ButtonLM(151, 7, 16, 16, EnumPrivacyLevel.enumLangKey.translate())
        {
            @Override
            public void onClicked(@Nonnull GuiLM gui, @Nonnull IMouseButton button)
            {
                GuiLM.playClickSound();
                mc.playerController.sendEnchantPacket(container.windowId, button.isLeft() ? 10 : 11);
            }

            @Override
            public void addMouseOverText(@Nonnull GuiLM gui, @Nonnull List<String> l)
            {
                l.add(getTitle());
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
        FTBLibClient.setTexture(TEXTURE);
        GuiScreen.drawModalRectWithCustomSizedTexture(getAX(), getAY(), 0F, 0F, width, height, 256F, 256F);

        buttonColor.render(GuiIcons.color_rgb);
        buttonPrivacy.render(container.bag.privacyLevel.getIcon());
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}
