package de.dafuqs.spectrum.items.food;

import de.dafuqs.spectrum.api.render.SlotBackgroundEffectProvider;
import de.dafuqs.spectrum.items.conditional.CloakedItem;
import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NectardewBurgeonItem extends CloakedItem implements SlotBackgroundEffectProvider {

    private final Component lore;

    public NectardewBurgeonItem(Properties settings, String lore, ResourceLocation cloakAdvancementIdentifier, Item cloakItem) {
        super(settings, cloakAdvancementIdentifier, cloakItem);
        this.lore = Component.translatable(lore).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(lore);
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        return isVisibleTo(player) ? SlotEffect.PULSE : SlotEffect.NONE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return isVisibleTo(player) ? SpectrumStatusEffects.ETERNAL_SLUMBER_COLOR : 0x0;
    }
}
