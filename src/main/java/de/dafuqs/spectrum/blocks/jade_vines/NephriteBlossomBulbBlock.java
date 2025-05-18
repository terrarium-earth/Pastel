package de.dafuqs.spectrum.blocks.jade_vines;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.SpectrumConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NephriteBlossomBulbBlock extends BushBlock implements BonemealableBlock {

	public static final MapCodec<NephriteBlossomBulbBlock> CODEC = simpleCodec(NephriteBlossomBulbBlock::new);

	public NephriteBlossomBulbBlock(Properties settings) {
		super(settings);
		registerDefaultState(defaultBlockState());
	}

	@Override
	public MapCodec<? extends NephriteBlossomBulbBlock> codec() {
		return CODEC;
	}
	
	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return true;
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (random.nextFloat() < 0.025) {
			performBonemeal(world, random, pos, state);
		}
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return true;
	}

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).get(SpectrumConfiguredFeatures.NEPHRITE_BLOSSOM_BULB).place(world, world.getChunkSource().getGenerator(), random, pos);
    }
}
