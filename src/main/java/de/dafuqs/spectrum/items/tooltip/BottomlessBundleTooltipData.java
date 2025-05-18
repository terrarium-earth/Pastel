package de.dafuqs.spectrum.items.tooltip;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record BottomlessBundleTooltipData(ItemVariant variant, long amount) implements TooltipComponent {
}
