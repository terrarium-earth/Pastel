package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

@SuppressWarnings("unused")
public class PastelEnchantmentTags {

    public static final TagKey<Enchantment> SPECTRUM_ENCHANTMENT = of("enchantments");

    public static final TagKey<Enchantment> AUTO_KILLS_SILVERFISH = of("effect/auto_kills_silverfish");
    public static final TagKey<Enchantment> DELETES_OVERFLOW = of("effect/deletes_overflow");
    public static final TagKey<Enchantment> DELETES_OVERFLOW_IN_INVENTORY = of("effect/deletes_overflow_in_inventory");
    public static final TagKey<Enchantment> DIMENSIONAL_TELEPORT = of("effect/dimensional_teleport");
    public static final TagKey<Enchantment> INDESTRUCTIBLE_EFFECT = of("effect/indestructible");
    public static final TagKey<Enchantment> INVENTORY_INSERTION_EFFECT = of("effect/inventory_insertion");
    public static final TagKey<Enchantment> NO_BLOCK_DROPS = of("effect/no_block_drops");
    public static final TagKey<Enchantment> PREVENTS_INCANDESCENT_EXPLOSION = of(
        "effect/prevents_incandescent_explosion");
    public static final TagKey<Enchantment> PREVENTS_ITEM_DAMAGE = of("effect/prevents_item_damage");
    public static final TagKey<Enchantment> RESONANT_BLOCK_DROPS = of("effect/resonant_block_drops");
    public static final TagKey<Enchantment> SMELTS_MORE_LOOT = of("effect/smelts_more_loot");

    private static TagKey<Enchantment> of(String id) {
        return TagKey.create(Registries.ENCHANTMENT, PastelCommon.locate(id));
    }

}
