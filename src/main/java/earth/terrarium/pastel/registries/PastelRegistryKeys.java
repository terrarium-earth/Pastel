package earth.terrarium.pastel.registries;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.item.StampDataCategory;
import earth.terrarium.pastel.api.pastel.PastelUpgradeSignature;
import earth.terrarium.pastel.api.recipe.FusionShrineRecipeWorldEffect;
import earth.terrarium.pastel.entity.variants.KindlingVariant;
import earth.terrarium.pastel.entity.variants.LizardFrillVariant;
import earth.terrarium.pastel.entity.variants.LizardHornVariant;
import earth.terrarium.pastel.items.tools.GlassArrowVariant;
import earth.terrarium.pastel.recipe.RecipeScaling;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class PastelRegistryKeys {

    public static final ResourceKey<Registry<FusionShrineRecipeWorldEffect>> WORLD_EFFECT = of("world_effect");
    public static final ResourceKey<Registry<GemstoneColor>> GEMSTONE_COLOR = of("gemstone_color");
    public static final ResourceKey<Registry<GlassArrowVariant>> GLASS_ARROW_VARIANT = of("glass_arrow_variant");
    public static final ResourceKey<Registry<InkColor>> INK_COLOR = of("ink_color");
    public static final ResourceKey<Registry<KindlingVariant>> KINDLING_VARIANT = of("kindling_variant");
    public static final ResourceKey<Registry<LizardFrillVariant>> LIZARD_FRILL_VARIANT = of("lizard_frill_variant");
    public static final ResourceKey<Registry<LizardHornVariant>> LIZARD_HORN_VARIANT = of("lizard_horn_variant");
    public static final ResourceKey<Registry<PastelUpgradeSignature>> PASTEL_UPGRADE = of("pastel_upgrade");
    public static final ResourceKey<Registry<RecipeScaling>> RECIPE_SCALING = of("recipe_scaling");
    public static final ResourceKey<Registry<StampDataCategory>> STAMP_DATA_CATEGORY = of("stamp_data_category");

    public static final ResourceKey<Registry<MapCodec<? extends ResonanceProcessor>>> RESONANCE_PROCESSOR_TYPE = of(
        "resonance_processor_type");
    public static final ResourceKey<Registry<ResonanceProcessor>> RESONANCE_PROCESSOR = of("resonance_processor");

    private static <T> ResourceKey<Registry<T>> of(String name) {
        return ResourceKey.createRegistryKey(PastelCommon.locate(name));
    }

}
