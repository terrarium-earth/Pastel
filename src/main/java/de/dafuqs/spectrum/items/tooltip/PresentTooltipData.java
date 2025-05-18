package de.dafuqs.spectrum.items.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record PresentTooltipData(List<ItemStack> itemStacks) implements TooltipComponent {
}
