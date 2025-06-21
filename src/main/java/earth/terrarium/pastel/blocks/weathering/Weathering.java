package earth.terrarium.pastel.blocks.weathering;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface Weathering extends ChangeOverTimeBlock<Weathering.WeatheringLevel> {
	
	Supplier<BiMap<Block, Block>> WEATHERING_LEVEL_INCREASES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
			
			.put(PastelBlocks.POLISHED_SHALE_CLAY.get(), PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.get())
			.put(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.get(), PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY.get())
			
			.put(PastelBlocks.POLISHED_SHALE_CLAY_STAIRS.get(), PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS.get())
			.put(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS.get(), PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_STAIRS.get())
			
			.put(PastelBlocks.POLISHED_SHALE_CLAY_SLAB.get(), PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB.get())
			.put(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB.get(), PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB.get())
			
			.put(PastelBlocks.SHALE_CLAY_BRICKS.get(), PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS.get())
			.put(PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS.get(), PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS.get())
			
			.put(PastelBlocks.SHALE_CLAY_BRICK_STAIRS.get(), PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS.get())
			.put(PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS.get(), PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_STAIRS.get())
			
			.put(PastelBlocks.SHALE_CLAY_BRICK_SLAB.get(), PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB.get())
			.put(PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB.get(), PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_SLAB.get())
			
			.put(PastelBlocks.SHALE_CLAY_TILES.get(), PastelBlocks.EXPOSED_SHALE_CLAY_TILES.get())
			.put(PastelBlocks.EXPOSED_SHALE_CLAY_TILES.get(), PastelBlocks.WEATHERED_SHALE_CLAY_TILES.get())
			
			.put(PastelBlocks.SHALE_CLAY_TILE_STAIRS.get(), PastelBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS.get())
			.put(PastelBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS.get(), PastelBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS.get())
			
			.put(PastelBlocks.SHALE_CLAY_TILE_SLAB.get(), PastelBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB.get())
			.put(PastelBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB.get(), PastelBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB.get())
			.build());
	
	Supplier<BiMap<Block, Block>> WEATHERING_LEVEL_DECREASES = Suppliers.memoize(() -> WEATHERING_LEVEL_INCREASES.get().inverse());
	
	static Optional<Block> getDecreasedWeatheredBlock(Block block) {
		return Optional.ofNullable(WEATHERING_LEVEL_DECREASES.get().get(block));
	}
	
	static Block getUnaffectedWeatheredBlock(Block block) {
		Block returnBlock = block;
		for (Block block3 = WEATHERING_LEVEL_DECREASES.get().get(block); block3 != null; block3 = WEATHERING_LEVEL_DECREASES.get().get(block3)) {
			returnBlock = block3;
		}
		return returnBlock;
	}
	
	static Optional<BlockState> getDecreasedWeatheredState(BlockState state) {
		return getDecreasedWeatheredBlock(state.getBlock()).map((block) -> block.withPropertiesOf(state));
	}

	static Optional<Block> getIncreasedWeatheredBlock(Block block) {
		return Optional.ofNullable(WEATHERING_LEVEL_INCREASES.get().get(block));
	}
	
	static BlockState getUnaffectedWeatheredState(BlockState state) {
		return getUnaffectedWeatheredBlock(state.getBlock()).withPropertiesOf(state);
	}
	
	@Override
	default Optional<BlockState> getNext(BlockState state) {
		return getIncreasedWeatheredBlock(state.getBlock()).map((block) -> block.withPropertiesOf(state));
	}
	
	@Override
	default float getChanceModifier() {
		return this.getAge() == WeatheringLevel.UNAFFECTED ? 0.75F : 1.0F;
	}
	
	default boolean shouldTryWeather(Level world, BlockPos pos) {
		float chance = world.canSeeSky(pos) ? 0.5F : 0.0F;
		if (world.isRaining() && world.getBiome(pos).value().getPrecipitationAt(pos) != Biome.Precipitation.NONE) {
			chance += 0.5;
		}
		return world.random.nextFloat() < chance;
	}
	
	enum WeatheringLevel {
		UNAFFECTED,
		EXPOSED,
		WEATHERED
	}
}
