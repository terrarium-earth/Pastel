package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.Preenchanted;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

// Waiting for https://github.com/FabricMC/fabric/pull/1804
// Who's still waiting this with me in 2025??
public class BedrockShearsItem extends ShearsItem implements Preenchanted {

    public BedrockShearsItem(Properties settings) {
        super(settings);
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
        return Map.of(Enchantments.EFFICIENCY, 6);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

}
