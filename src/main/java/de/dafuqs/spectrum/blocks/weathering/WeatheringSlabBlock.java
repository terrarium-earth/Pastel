package de.dafuqs.spectrum.blocks.weathering;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringSlabBlock extends SlabBlock implements Weathering {
	
	private final Weathering.WeatheringLevel weatheringLevel;
	
	public WeatheringSlabBlock(Weathering.WeatheringLevel weatheringLevel, BlockBehaviour.Properties settings) {
		super(settings);
		this.weatheringLevel = weatheringLevel;
	}

	@Override
	public MapCodec<? extends WeatheringSlabBlock> codec() {
		//TODO: Make the codec
		return null;
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (shouldTryWeather(world, pos)) {
			this.changeOverTime(state, world, pos, random);
		}
	}
	
	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return Weathering.getIncreasedWeatheredBlock(state.getBlock()).isPresent();
	}
	
	@Override
	public Weathering.WeatheringLevel getAge() {
		return this.weatheringLevel;
	}
	
}
