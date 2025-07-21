package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.Preenchanted;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public class PastelPickaxeItem extends PickaxeItem implements Preenchanted {

    public PastelPickaxeItem(Tier material, Properties settings) {
        super(material, settings);
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
        return Map.of();
    }
}
