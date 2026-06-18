package earth.terrarium.pastel.items.tooltip;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public class CraftingTabletTooltipData implements TooltipComponent {

    private final ItemStack itemStack;

    private final Component description;

    public CraftingTabletTooltipData(Recipe<?> recipe, Level world) {
        this.itemStack = recipe.getResultItem(world.registryAccess());
        this.description = Component
            .translatable(
                "item.pastel.crafting_tablet.tooltip.recipe",
                this.itemStack.getCount(),
                this.itemStack.getHoverName()
            );
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public Component getDescription() {
        return this.description;
    }

}
