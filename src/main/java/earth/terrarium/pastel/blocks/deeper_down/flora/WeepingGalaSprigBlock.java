package earth.terrarium.pastel.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.SpectrumBlockTags;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumSaplingGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeepingGalaSprigBlock extends SaplingBlock {

	public static final MapCodec<WeepingGalaSprigBlock> CODEC = simpleCodec(WeepingGalaSprigBlock::new);

	public WeepingGalaSprigBlock(BlockBehaviour.Properties settings) {
		super(SpectrumSaplingGenerators.WEEPING_GALA_SAPLING_GENERATOR, settings);
	}

	@Override
	public MapCodec<? extends WeepingGalaSprigBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return floor.is(SpectrumBlockTags.ASH) || floor.is(SpectrumBlocks.ASHEN_BLACKSLAG.get()) || super.mayPlaceOn(floor, world, pos);
	}
	
}
