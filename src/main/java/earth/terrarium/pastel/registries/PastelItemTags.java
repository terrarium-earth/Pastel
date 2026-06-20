package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

import static earth.terrarium.pastel.PastelCommon.locate;

public class PastelItemTags {

    // "c" namespace
    public static final TagKey<Item> SKULLS = common("skulls");

    public static final TagKey<Item> FRUITS = common("foods/fruit");

    public static final TagKey<Item> SHEARS = common("tools/shear");

    // "pastel" namespace
    public static final TagKey<Item> COOKBOOKS = of("cookbooks");

    public static final TagKey<Item> FISHING_RODS = of("fishing_rods");

    public static final TagKey<Item> COLORED_PLANKS = of("colored_planks");

    public static final TagKey<Item> GEMSTONE_SHARDS = of("gemstone_shards");

    public static final TagKey<Item> COMING_SOON_TOOLTIP = of("coming_soon_tooltip");

    public static final TagKey<Item> PIGLIN_SAFE_EQUIPMENT = of("piglin_safe_equipment");

    public static final TagKey<Item> ENCHANTABLE_BOOKS = of("enchantable_books");

    public static final TagKey<Item> MEMORY_BONDING_AGENTS_CONCEALABLE = of("memory_bonding_agents_concealable");

    public static final TagKey<Item> INDESTRUCTIBLE_BLACKLISTED = of("indestructible_blacklisted");

    public static final TagKey<Item> NO_CINDERHEARTH_DOUBLING = of("no_cinderhearth_doubling");

    public static final TagKey<Item> SHOOTING_STARS = of("shooting_stars");

    public static final TagKey<Item> GLASS_ARROWS = of("glass_arrows");

    public static final TagKey<Item> KINDLING_FOOD = of("kindling_food");

    public static final TagKey<Item> COLORED_FENCES = of("colored_fences");

    public static final TagKey<Item> COLORED_FENCE_GATES = of("colored_fence_gates");

    public static final TagKey<Item> REQUIRES_OMNI_ACCELERATOR_PVP_ENABLED = of(
        "requires_omni_accelerator_pvp_enabled"
    );

    public static final TagKey<Item> EMISSIVE = of("emissive");

    public static final TagKey<Item> PASTEL_NODE_UPGRADES = of("pastel_node_upgrades");

    public static final TagKey<Item> TAG_FILTERING_ITEMS = of("tag_filtering_items");

    public static final TagKey<Item> WEEPING_GALA_LOGS = of("weeping_gala_logs");

    public static final TagKey<Item> TEA_TABLE_DRINKS = of("tea_table_drinks");

    public static final TagKey<Item> METAL_ARMOR = of("metal_armor");

    public static final TagKey<Item> CRYSTAL_EMPOWER_BLACKLIST = of("crystal_empower_blacklist");

    public static final TagKey<Item> WORKSTAFFS = of("workstaffs");

    public static final TagKey<Item> PRODUCTIVITY_EXCLUDED = of("productivity_excluded");

    public static final TagKey<Item> RESPLENDENT_FEATHERS = of("resplendent_feathers");

    public static final TagKey<Item> COMMON_MEATS = of("common_meats");

    public static final TagKey<Item> WATER_MEATS = of("water_meats");

    public static final TagKey<Item> LEAN_MEATS = of("lean_meats");

    public static final TagKey<Item> DRINKABLE_SPIRITS = of("drinkable_spirits");

    public static final TagKey<Item> CHESTNUT_NOXCAP_STEMS = of("chestnut_noxcap_stems");

    public static final TagKey<Item> EBONY_NOXCAP_STEMS = of("ebony_noxcap_stems");

    public static final TagKey<Item> IVORY_NOXCAP_STEMS = of("ivory_noxcap_stems");

    public static final TagKey<Item> SLATE_NOXCAP_STEMS = of("slate_noxcap_stems");

    public static final TagKey<Item> PIGMENTS = of("pigments");

    public static class MobHeads {
        public static final TagKey<Item> AXOLOTL_HEADS = getMobHeadKey("axolotl");
        public static final TagKey<Item> FISH_HEADS = getMobHeadKey("fish");
        public static final TagKey<Item> FOX_HEADS = getMobHeadKey("fox");
        public static final TagKey<Item> GUARDIAN_HEADS = getMobHeadKey("guardian");
        public static final TagKey<Item> BOVINE_HEADS = getMobHeadKey("bovine");
        public static final TagKey<Item> EQUIDAE_HEADS = getMobHeadKey("equidae");
        public static final TagKey<Item> PARROT_HEADS = getMobHeadKey("parrot");
        public static final TagKey<Item> SHULKER_HEADS = getMobHeadKey("shulker");
        public static final TagKey<Item> SLIME_HEADS = getMobHeadKey("slime");
        public static final TagKey<Item> SPIDER_HEADS = getMobHeadKey("spider");
        public static final TagKey<Item> ZOMBIE_HEADS = getMobHeadKey("zombie");
    }

    public static class EnchantableWith {
        public static final TagKey<Item> BIG_CATCH = getEnchantableTag(PastelEnchantments.BIG_CATCH);

        public static final TagKey<Item> CLOVERS_FAVOR = getEnchantableTag(PastelEnchantments.CLOVERS_FAVOR);

        public static final TagKey<Item> DISARMING = getEnchantableTag(PastelEnchantments.DISARMING);

        public static final TagKey<Item> EXUBERANCE = getEnchantableTag(PastelEnchantments.EXUBERANCE);

        public static final TagKey<Item> FIRST_STRIKE = getEnchantableTag(PastelEnchantments.FIRST_STRIKE);

        public static final TagKey<Item> FOUNDRY = getEnchantableTag(PastelEnchantments.FOUNDRY);

        public static final TagKey<Item> IMPROVED_CRITICAL = getEnchantableTag(PastelEnchantments.IMPROVED_CRITICAL);

        public static final TagKey<Item> INDESTRUCTIBLE = getEnchantableTag(PastelEnchantments.INDESTRUCTIBLE);

        public static final TagKey<Item> INERTIA = getEnchantableTag(PastelEnchantments.INERTIA);

        public static final TagKey<Item> INEXORABLE = getEnchantableTag(PastelEnchantments.INEXORABLE);

        public static final TagKey<Item> INVENTORY_INSERTION = getEnchantableTag(
            PastelEnchantments.INVENTORY_INSERTION
        );

        public static final TagKey<Item> PEST_CONTROL = getEnchantableTag(PastelEnchantments.PEST_CONTROL);

        public static final TagKey<Item> RAZING = getEnchantableTag(PastelEnchantments.RAZING);

        public static final TagKey<Item> RESONANCE = getEnchantableTag(PastelEnchantments.RESONANCE);

        public static final TagKey<Item> SERENDIPITY_REEL = getEnchantableTag(PastelEnchantments.SERENDIPITY_REEL);

        public static final TagKey<Item> STEADFAST = getEnchantableTag(PastelEnchantments.STEADFAST);

        public static final TagKey<Item> TIGHT_GRIP = getEnchantableTag(PastelEnchantments.TIGHT_GRIP);

        public static final TagKey<Item> TREASURE_HUNTER = getEnchantableTag(PastelEnchantments.TREASURE_HUNTER);

        public static final TagKey<Item> VOIDING = getEnchantableTag(PastelEnchantments.VOIDING);
    }

    private static TagKey<Item> getMobHeadKey(String mobName) {
        return of("mob_heads/" + mobName + "_heads");
    }

    public static TagKey<Item> getEnchantableTag(ResourceKey<Enchantment> key) {
        return TagKey
            .create(
                Registries.ITEM,
                locate(
                    "enchantable/" + key
                        .location()
                        .getPath()
                )
            );
    }

    private static TagKey<Item> of(String id) {
        return TagKey.create(Registries.ITEM, PastelCommon.locate(id));
    }

    private static TagKey<Item> common(String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", id));
    }

}
