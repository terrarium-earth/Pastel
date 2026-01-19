package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import static earth.terrarium.pastel.PastelCommon.locate;

@SuppressWarnings("unused")
public class PastelEnchantmentTags {

    public static final TagKey<Enchantment> PASTEL_ENCHANTMENT = of("enchantments");
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

    public static class ExclusiveSet {
        public static final TagKey<Enchantment> CLOVERS_FAVOR = getExclusiveSetTag(PastelEnchantments.CLOVERS_FAVOR);
        public static final TagKey<Enchantment> FOUNDRY = getExclusiveSetTag(PastelEnchantments.FOUNDRY);
        public static final TagKey<Enchantment> IMPROVED_CRITICAL = getExclusiveSetTag(PastelEnchantments.IMPROVED_CRITICAL);
        public static final TagKey<Enchantment> INDESTRUCTIBLE = getExclusiveSetTag(PastelEnchantments.INDESTRUCTIBLE);
        public static final TagKey<Enchantment> INERTIA = getExclusiveSetTag(PastelEnchantments.INERTIA);
        public static final TagKey<Enchantment> PEST_CONTROL = getExclusiveSetTag(PastelEnchantments.PEST_CONTROL);
        public static final TagKey<Enchantment> RAZING = getExclusiveSetTag(PastelEnchantments.RAZING);
        public static final TagKey<Enchantment> RESONANCE = getExclusiveSetTag(PastelEnchantments.RESONANCE);
        public static final TagKey<Enchantment> SNIPING = getExclusiveSetTag(PastelEnchantments.SNIPING);
        public static final TagKey<Enchantment> TIGHT_GRIP = getExclusiveSetTag(PastelEnchantments.TIGHT_GRIP);
        public static final TagKey<Enchantment> TREASURE_HUNTER = getExclusiveSetTag(PastelEnchantments.TREASURE_HUNTER);
    }
    
    private static TagKey<Enchantment> of(String id) {
        return TagKey.create(Registries.ENCHANTMENT, PastelCommon.locate(id));
    }
    
    public static TagKey<Enchantment> getExclusiveSetTag(ResourceKey<Enchantment> key) {
        return TagKey.create(
            Registries.ENCHANTMENT, locate("exclusive_set/" + key.location()
                                                                 .getPath())
        );
    }

}
