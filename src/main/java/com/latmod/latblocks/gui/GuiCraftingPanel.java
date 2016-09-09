package com.latmod.latblocks.gui;

import com.feed_the_beast.ftbl.api.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.gui.GuiLM;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 05.08.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiCraftingPanel extends GuiLM
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/crafting_table.png");

    public final ContainerCraftingPanel container;

    public GuiCraftingPanel(ContainerCraftingPanel c)
    {
        super(176, 166);
        container = c;
    }

    @Override
    public void addWidgets()
    {
    }

    @Override
    public void drawForeground()
    {
        int ax = getAX();
        int ay = getAY();
        getFont().drawString(I18n.format("container.crafting"), ax + 28, ay + 6, 4210752);
        getFont().drawString(I18n.format("container.inventory"), ax + 8, ay + getHeight() - 94, 4210752);
    }

    @Override
    public void drawBackground()
    {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(TEXTURE);
        GuiScreen.drawModalRectWithCustomSizedTexture(getAX(), getAY(), 0F, 0F, getWidth(), getHeight(), 256F, 256F);
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}
