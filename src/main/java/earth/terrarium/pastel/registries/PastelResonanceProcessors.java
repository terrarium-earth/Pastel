package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import net.minecraft.resources.ResourceKey;

@SuppressWarnings(
    "unused"
)
public class PastelResonanceProcessors {

    public static final ResourceKey<ResonanceProcessor> PURE_RESONANCES_FROM_ORE = of("pure_resonances_from_ore");

    public static final ResourceKey<ResonanceProcessor> BLACK_MATERIA = of("black_materia");

    public static final ResourceKey<ResonanceProcessor> BRUSHABLE_BLOCKS = of("brushable_blocks");

    public static final ResourceKey<ResonanceProcessor> BUDDING_BLOCKS = of("budding_blocks");

    public static final ResourceKey<ResonanceProcessor> BUDS = of("buds");

    public static final ResourceKey<ResonanceProcessor> CAKE = of("cake");

    public static final ResourceKey<ResonanceProcessor> CLUSTERS = of("clusters");

    public static final ResourceKey<ResonanceProcessor> COMPOSTER = of("composter");

    public static final ResourceKey<ResonanceProcessor> FROGSPAWN = of("frogspawn");

    public static final ResourceKey<ResonanceProcessor> GILDED_BLACKSTONE = of("gilded_blackstone");

    public static final ResourceKey<ResonanceProcessor> INFESTED_BLOCKS = of("infested_blocks");

    public static final ResourceKey<ResonanceProcessor> REINFORCED_DEEPSLATE = of("reinforced_deepslate");

    public static final ResourceKey<ResonanceProcessor> RESPAWN_ANCHOR = of("respawn_anchor");

    public static final ResourceKey<ResonanceProcessor> SCULK_SHRIEKER = of("sculk_shrieker");

    public static final ResourceKey<ResonanceProcessor> SIGNS = of("signs");

    public static final ResourceKey<ResonanceProcessor> SPAWNER = of("spawner");

    public static ResourceKey<ResonanceProcessor> of(String id) {
        return ResourceKey.create(PastelRegistryKeys.RESONANCE_PROCESSOR, PastelCommon.locate(id));
    }

}
