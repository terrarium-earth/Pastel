package earth.terrarium.pastel.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.compat.REI.GatedRecipeDisplay;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.registries.PastelAdvancements;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class NaturesStaffConversionsDisplay extends BasicDisplay implements GatedRecipeDisplay {

    private final @Nullable ResourceLocation requiredAdvancementIdentifier;

    public NaturesStaffConversionsDisplay(
        EntryStack<?> in, EntryStack<?> out, @Nullable ResourceLocation requiredAdvancementIdentifier) {
        super(Collections.singletonList(EntryIngredient.of(in)), Collections.singletonList(EntryIngredient.of(out)));
        this.requiredAdvancementIdentifier = requiredAdvancementIdentifier;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.NATURES_STAFF;
    }

    @Override
    public boolean isUnlocked() {
        Minecraft client = Minecraft.getInstance();
        return AdvancementHelper.hasAdvancement(client.player, this.requiredAdvancementIdentifier)
               && AdvancementHelper.hasAdvancement(client.player, PastelAdvancements.UNLOCK_NATURES_STAFF);
    }

    @Override
    public boolean isSecret() {
        return false;
    }

    @Override
    public @Nullable Component getSecretHintText() {
        return null;
    }

}
