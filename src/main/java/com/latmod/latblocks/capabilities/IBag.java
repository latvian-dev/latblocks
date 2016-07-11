package com.latmod.latblocks.capabilities;

import com.feed_the_beast.ftbl.api.security.EnumPrivacyLevel;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created by LatvianModder on 11.07.2016.
 */
public interface IBag
{
    int getColor();

    void setColor(int color);

    @Nullable
    UUID getOwner();

    void setOwner(@Nullable UUID id);

    @Nonnull
    EnumPrivacyLevel getPrivacyLevel();

    void setPrivacyLevel(@Nonnull EnumPrivacyLevel level);

    int getTabCount();

    @Nullable
    IItemHandler getInventoryFromTab(byte tab);

    byte getCurrentTab();

    void setCurrentTab(byte tab);
}