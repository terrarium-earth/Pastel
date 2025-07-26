package earth.terrarium.pastel.registries;

import com.mojang.serialization.MapCodec;
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
import earth.terrarium.pastel.logistics.api.Payload;
import earth.terrarium.pastel.recipe.RecipeScaling;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Optional;

@SuppressWarnings("unused")
public class PastelRegistries {

    // TODO: do all these registries need to be synced?
    public static final Registry<FusionShrineRecipeWorldEffect> WORLD_EFFECT = register(
        PastelRegistryKeys.WORLD_EFFECT, true);
    public static final Registry<GemstoneColor> GEMSTONE_COLOR = register(PastelRegistryKeys.GEMSTONE_COLOR, true);
    public static final Registry<GlassArrowVariant> GLASS_ARROW_VARIANT = register(
        PastelRegistryKeys.GLASS_ARROW_VARIANT, true);
    public static final Registry<InkColor> INK_COLOR = register(PastelRegistryKeys.INK_COLOR, true);
    public static final Registry<KindlingVariant> KINDLING_VARIANT = register(
        PastelRegistryKeys.KINDLING_VARIANT, true);
    public static final Registry<LizardFrillVariant> LIZARD_FRILL_VARIANT = register(
        PastelRegistryKeys.LIZARD_FRILL_VARIANT, true);
    public static final Registry<LizardHornVariant> LIZARD_HORN_VARIANT = register(
        PastelRegistryKeys.LIZARD_HORN_VARIANT, true);
    public static final Registry<PastelUpgradeSignature> PASTEL_UPGRADE = register(
        PastelRegistryKeys.PASTEL_UPGRADE, false);
    public static final Registry<RecipeScaling> RECIPE_SCALING = register(PastelRegistryKeys.RECIPE_SCALING, true);

    public static final Registry<StampDataCategory> STAMP_DATA_CATEGORY = register(
        PastelRegistryKeys.STAMP_DATA_CATEGORY, true);
    public static final Registry<Payload<?, ?>> LOGISTIC_PAYLOAD = register(
        PastelRegistryKeys.LOGISTIC_PAYLOAD, true);

    public static final Registry<MapCodec<? extends ResonanceProcessor>> RESONANCE_PROCESSOR_TYPE = register(
        PastelRegistryKeys.RESONANCE_PROCESSOR_TYPE, false);

    public static void register(NewRegistryEvent event) {
        event.register(WORLD_EFFECT);
        event.register(GEMSTONE_COLOR);
        event.register(GLASS_ARROW_VARIANT);
        event.register(INK_COLOR);
        event.register(KINDLING_VARIANT);
        event.register(LIZARD_FRILL_VARIANT);
        event.register(LIZARD_HORN_VARIANT);
        event.register(PASTEL_UPGRADE);
        event.register(RECIPE_SCALING);
        event.register(STAMP_DATA_CATEGORY);
        event.register(RESONANCE_PROCESSOR_TYPE);
    }

    public static void registerDyn(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
            PastelRegistryKeys.RESONANCE_PROCESSOR, ResonanceProcessor.CODEC, ResonanceProcessor.CODEC);
    }

    private static <T> Registry<T> register(ResourceKey<? extends Registry<T>> key, boolean synced) {
        return new RegistryBuilder<>(key).sync(synced)
                                         .create();
    }

    public static <T> T getRandomTagEntry(Registry<T> registry, TagKey<T> tag, RandomSource random, T fallback) {
        Optional<HolderSet.Named<T>> tagEntries = registry.getTag(tag);
        if (tagEntries.isPresent()) {
            return tagEntries.get()
                             .get(random.nextInt(tagEntries.get()
                                                           .size()))
                             .value();
        } else {
            return fallback;
        }
    }

}
