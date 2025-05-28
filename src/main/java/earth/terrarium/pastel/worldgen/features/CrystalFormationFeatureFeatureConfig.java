package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CrystalFormationFeatureFeatureConfig(IntProvider iterationCountProvider,
												   BlockStateProvider blockStateProvider,
												   HolderSet<Block> canStartOnBlocks,
												   HolderSet<Block> canExtendOnBlocks, Boolean canGrowUpwards,
												   Boolean canGrowDownwards) implements FeatureConfiguration {
	
	public static final Codec<CrystalFormationFeatureFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			IntProvider.POSITIVE_CODEC.fieldOf("iterations").forGetter((config) -> config.iterationCountProvider),
			BlockStateProvider.CODEC.fieldOf("state_provider").forGetter((config) -> config.blockStateProvider),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_start_on").forGetter((config) -> config.canStartOnBlocks),
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_extend_on").forGetter((config) -> config.canStartOnBlocks),
			Codec.BOOL.fieldOf("can_grow_upwards").forGetter((config) -> config.canGrowUpwards),
			Codec.BOOL.fieldOf("can_grow_downwards").forGetter((config) -> config.canGrowDownwards)
	).apply(instance, CrystalFormationFeatureFeatureConfig::new));

}
