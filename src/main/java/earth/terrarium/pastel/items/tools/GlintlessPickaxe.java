package earth.terrarium.pastel.items.tools;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class GlintlessPickaxe extends PastelPickaxeItem {

    public GlintlessPickaxe(Tier material, Properties settings) {
        super(material, settings);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        var defaults = getDefaultEnchantments();
        var comp = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (
            var entry : comp.entrySet()
        ) {
            var key = entry
                .getKey()
                .unwrapKey();
            if (key.isEmpty()) continue;
            if (entry.getIntValue() > defaults.getOrDefault(key.get(), 0))
                return true;
        }
        return false;
    }
}
