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
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumFeatures {

	private static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(Registries.FEATURE, SpectrumCommon.MOD_ID);

	public static Feature<WeightedRandomFeatureConfig> WEIGHTED_RANDOM_FEATURE = new WeightedRandomFeature(WeightedRandomFeatureConfig.CODEC);
	public static Feature<GeodeConfiguration> AIR_CHECK_GEODE = new SolidBlockCheckGeodeFeature(GeodeConfiguration.CODEC);
	public static Feature<RandomBudsFeaturesConfig> RANDOM_BUDS = new RandomBudsFeature(RandomBudsFeaturesConfig.CODEC);
	public static Feature<OreConfiguration> AIR_CHECK_DISK = new AirCheckDiskFeature(OreConfiguration.CODEC);
	public static Feature<GilledFungusFeatureConfig> GILLED_FUNGUS = new GilledFungusFeature(GilledFungusFeatureConfig.CODEC);
	public static Feature<GilledFungusFeatureConfig> GIANT_GILLED_FUNGUS = new GilledFungusFeature(GilledFungusFeatureConfig.CODEC);
	public static Feature<NephriteBlossomFeatureConfig> NEPHRITE_BLOSSOM = new NephriteBlossomFeature(NephriteBlossomFeatureConfig.CODEC);
	public static Feature<JadeiteLotusFeatureConfig> JADEITE_LOTUS = new JadeiteLotusFeature(JadeiteLotusFeatureConfig.CODEC);
	public static Feature<TriStateVineFeatureConfig> TRISTATE_VINE = new TriStateVineFeature(TriStateVineFeatureConfig.CODEC);
	public static Feature<BlockStateFeatureConfig> PILLAR = new PillarFeature(BlockStateFeatureConfig.CODEC);
	public static Feature<ColumnsFeatureConfig> COLUMNS = new ColumnsFeature(ColumnsFeatureConfig.CODEC);
	public static Feature<CrystalFormationFeatureFeatureConfig> BLOB = new CrystalFormationFeature(CrystalFormationFeatureFeatureConfig.CODEC);
	public static Feature<RandomBlockProximityPatchFeatureConfig> RANDOM_BLOCK_PROXIMITY_PATCH = new RandomBlockProximityPatchFeature(RandomBlockProximityPatchFeatureConfig.CODEC);
	public static Feature<FossilFeatureConfiguration> EXPOSED_FOSSIL = new ExposedFossilFeature(FossilFeatureConfiguration.CODEC);
	public static Feature<WallPatchFeatureConfig> WALL_PATCH = new WallPatchFeature(WallPatchFeatureConfig.CODEC);
	public static Feature<AshDunesFeatureConfig> ASH_DUNES = new AshDunesFeature(AshDunesFeatureConfig.CODEC);

	public static void register(IEventBus bus) {
		registerFeature("weighted_random_feature", WEIGHTED_RANDOM_FEATURE);
		registerFeature("air_check_geode", AIR_CHECK_GEODE);
		registerFeature("random_buds", RANDOM_BUDS);
		registerFeature("air_check_disk", AIR_CHECK_DISK);
		registerFeature("gilled_fungus", GILLED_FUNGUS);
		registerFeature("giant_gilled_fungus", GIANT_GILLED_FUNGUS);
		registerFeature("nephrite_blossom", NEPHRITE_BLOSSOM);
		registerFeature("jadeite_lotus", JADEITE_LOTUS);
		registerFeature("tristate_vine", TRISTATE_VINE);
		registerFeature("pillar", PILLAR);
		registerFeature("columns", COLUMNS);
		registerFeature("crystal_formation", BLOB);
		registerFeature("random_block_proximity_patch", RANDOM_BLOCK_PROXIMITY_PATCH);
		registerFeature("exposed_fossil", EXPOSED_FOSSIL);
		registerFeature("wall_patch", WALL_PATCH);
		registerFeature("ash_dunes", ASH_DUNES);
		REGISTER.register(bus);
	}
	
	private static <C extends FeatureConfiguration, F extends Feature<C>> void registerFeature(String name, F feature) {
		REGISTER.register(name, () -> feature);
	}
	
}
