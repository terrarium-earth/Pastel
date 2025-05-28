package earth.terrarium.pastel.items.tooltip;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record BottomlessBundleTooltipData(ItemStack variant, long amount) implements TooltipComponent {
}
