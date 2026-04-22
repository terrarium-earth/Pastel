package earth.terrarium.pastel.registries;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

@SuppressWarnings("unused")
public class PastelBiomes {

    public static final ResourceKey<Biome> BLACK_LANGAST = getKey("black_langast");
    public static final ResourceKey<Biome> CRYSTAL_GARDENS = getKey("crystal_gardens");
    public static final ResourceKey<Biome> DEEP_BARRENS = getKey("deep_barrens");
    public static final ResourceKey<Biome> DEEP_DRIPSTONE_CAVES = getKey("deep_dripstone_caves");
    public static final ResourceKey<Biome> HOWLING_SPIRES = getKey("howling_spires");
    public static final ResourceKey<Biome> DRAGONROT_SWAMP = getKey("dragonrot_swamp");
    public static final ResourceKey<Biome> NOXSHROOM_FOREST = getKey("noxshroom_forest");
    public static final ResourceKey<Biome> RAZOR_EDGE = getKey("razor_edge");

    public static final ResourceKey<Biome> AZURE_SPIRES = getKey("azure_spires");
    public static final ResourceKey<Biome> VIRIDIAN_CAVERNS = getKey("viridian_caverns");

    private static ResourceKey<Biome> getKey(String name) {
        return ResourceKey.create(Registries.BIOME, PastelCommon.locate(name));
    }

    public static void registerBiomePlacements() {
        BiomePlacement.addOverworld(
            AZURE_SPIRES, new Climate.ParameterPoint(
                Climate.Parameter.span(-0.45F, 0.15F), Climate.Parameter.span(-1.0F, -0.35F),
                Climate.Parameter.span(0.03F, 1.0F), Climate.Parameter.span(-0.2225F, 0.05F),
                Climate.Parameter.point(1.0F), Climate.Parameter.span(-1.0F, 1.0F), 0L
            )
        );
    }

}
