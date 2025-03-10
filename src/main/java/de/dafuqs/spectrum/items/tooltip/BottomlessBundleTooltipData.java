package de.dafuqs.spectrum.items.tooltip;

import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.minecraft.item.tooltip.*;

public record BottomlessBundleTooltipData(ItemVariant variant, long amount) implements TooltipData {
}
