package earth.terrarium.pastel.items.tooltip;

import earth.terrarium.pastel.api.item.ItemStorage;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record ItemStorageTooltipData(ItemStorage storage) implements TooltipComponent {
}
