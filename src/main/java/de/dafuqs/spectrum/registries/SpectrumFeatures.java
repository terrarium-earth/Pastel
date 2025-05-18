package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.worldgen.features.AirCheckDiskFeature;
import de.dafuqs.spectrum.worldgen.features.AshDunesFeature;
import de.dafuqs.spectrum.worldgen.features.AshDunesFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.BlockStateFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.ColumnsFeature;
import de.dafuqs.spectrum.worldgen.features.ColumnsFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.CrystalFormationFeature;
import de.dafuqs.spectrum.worldgen.features.CrystalFormationFeatureFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.ExposedFossilFeature;
import de.dafuqs.spectrum.worldgen.features.GiantGilledFungusFeature;
import de.dafuqs.spectrum.worldgen.features.GilledFungusFeature;
import de.dafuqs.spectrum.worldgen.features.GilledFungusFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.JadeiteLotusFeature;
import de.dafuqs.spectrum.worldgen.features.JadeiteLotusFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.NephriteBlossomFeature;
import de.dafuqs.spectrum.worldgen.features.NephriteBlossomFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.PillarFeature;
import de.dafuqs.spectrum.worldgen.features.RandomBlockProximityPatchFeature;
import de.dafuqs.spectrum.worldgen.features.RandomBlockProximityPatchFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.RandomBudsFeature;
import de.dafuqs.spectrum.worldgen.features.RandomBudsFeaturesConfig;
import de.dafuqs.spectrum.worldgen.features.SolidBlockCheckGeodeFeature;
import de.dafuqs.spectrum.worldgen.features.TriStateVineFeature;
import de.dafuqs.spectrum.worldgen.features.TriStateVineFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.WallPatchFeature;
import de.dafuqs.spectrum.worldgen.features.WallPatchFeatureConfig;
import de.dafuqs.spectrum.worldgen.features.WeightedRandomFeature;
import de.dafuqs.spectrum.worldgen.features.WeightedRandomFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class SpectrumFeatures {

	public static Feature<WeightedRandomFeatureConfig> WEIGHTED_RANDOM_FEATURE;
	public static Feature<GeodeConfiguration> AIR_CHECK_GEODE;
	public static Feature<RandomBudsFeaturesConfig> RANDOM_BUDS;
	public static Feature<OreConfiguration> AIR_CHECK_DISK;
	public static Feature<GilledFungusFeatureConfig> GILLED_FUNGUS;
	public static Feature<GilledFungusFeatureConfig> GIANT_GILLED_FUNGUS;
	public static Feature<NephriteBlossomFeatureConfig> NEPHRITE_BLOSSOM;
	public static Feature<JadeiteLotusFeatureConfig> JADEITE_LOTUS;
	public static Feature<TriStateVineFeatureConfig> TRISTATE_VINE;
	public static Feature<BlockStateFeatureConfig> PILLAR;
	public static Feature<ColumnsFeatureConfig> COLUMNS;
	public static Feature<CrystalFormationFeatureFeatureConfig> BLOB;
	public static Feature<RandomBlockProximityPatchFeatureConfig> RANDOM_BLOCK_PROXIMITY_PATCH;
	public static Feature<FossilFeatureConfiguration> EXPOSED_FOSSIL;
	public static Feature<WallPatchFeatureConfig> WALL_PATCH;
	public static Feature<AshDunesFeatureConfig> ASH_DUNES;

	public static void register() {
		WEIGHTED_RANDOM_FEATURE = registerFeature("weighted_random_feature", new WeightedRandomFeature(WeightedRandomFeatureConfig.CODEC));
		AIR_CHECK_GEODE = registerFeature("air_check_geode", new SolidBlockCheckGeodeFeature(GeodeConfiguration.CODEC));
		RANDOM_BUDS = registerFeature("random_buds", new RandomBudsFeature(RandomBudsFeaturesConfig.CODEC));
		AIR_CHECK_DISK = registerFeature("air_check_disk", new AirCheckDiskFeature(OreConfiguration.CODEC));
		GILLED_FUNGUS = registerFeature("gilled_fungus", new GilledFungusFeature(GilledFungusFeatureConfig.CODEC));
		GIANT_GILLED_FUNGUS = registerFeature("giant_gilled_fungus", new GiantGilledFungusFeature(GilledFungusFeatureConfig.CODEC));
		NEPHRITE_BLOSSOM = registerFeature("nephrite_blossom", new NephriteBlossomFeature(NephriteBlossomFeatureConfig.CODEC));
		JADEITE_LOTUS = registerFeature("jadeite_lotus", new JadeiteLotusFeature(JadeiteLotusFeatureConfig.CODEC));
		TRISTATE_VINE = registerFeature("tristate_vine", new TriStateVineFeature(TriStateVineFeatureConfig.CODEC));
		PILLAR = registerFeature("pillar", new PillarFeature(BlockStateFeatureConfig.CODEC));
		COLUMNS = registerFeature("columns", new ColumnsFeature(ColumnsFeatureConfig.CODEC));
		BLOB = registerFeature("crystal_formation", new CrystalFormationFeature(CrystalFormationFeatureFeatureConfig.CODEC));
		RANDOM_BLOCK_PROXIMITY_PATCH = registerFeature("random_block_proximity_patch", new RandomBlockProximityPatchFeature(RandomBlockProximityPatchFeatureConfig.CODEC));
		EXPOSED_FOSSIL = registerFeature("exposed_fossil", new ExposedFossilFeature(FossilFeatureConfiguration.CODEC));
		WALL_PATCH = registerFeature("wall_patch", new WallPatchFeature(WallPatchFeatureConfig.CODEC));
		ASH_DUNES = registerFeature("ash_dunes", new AshDunesFeature(AshDunesFeatureConfig.CODEC));
	}
	
	private static <C extends FeatureConfiguration, F extends Feature<C>> F registerFeature(String name, F feature) {
		return Registry.register(BuiltInRegistries.FEATURE, SpectrumCommon.locate(name), feature);
	}
	
}
