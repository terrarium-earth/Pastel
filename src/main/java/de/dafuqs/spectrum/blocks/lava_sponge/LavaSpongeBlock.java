package de.dafuqs.spectrum.blocks.lava_sponge;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.registry.tag.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class LavaSpongeBlock extends SpongeBlock {

	public static final MapCodec<LavaSpongeBlock> CODEC = createCodec(LavaSpongeBlock::new);

	public LavaSpongeBlock(Settings settings) {
		super(settings);
	}

//	@Override
//	public MapCodec<? extends LavaSpongeBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}

	@Override
	protected void update(World world, BlockPos pos) {
		if (this.absorbLava(world, pos)) {
			world.setBlockState(pos, SpectrumBlocks.WET_LAVA_SPONGE.getDefaultState(), 2);
			world.playSound(null, pos, SoundEvents.BLOCK_SPONGE_ABSORB, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}
	
	private boolean absorbLava(World world, BlockPos pos) {
		return BlockPos.iterateRecursively(pos, 6, 65, (currentPos, queuer) -> {
			for (Direction direction : Direction.values()) {
				queuer.accept(currentPos.offset(direction));
			}
		}, (currentPos) -> {
			if (currentPos.equals(pos)) {
				return true;
			} else {
				BlockState blockState = world.getBlockState(currentPos);
				FluidState fluidState = world.getFluidState(currentPos);
				if (!fluidState.isIn(FluidTags.LAVA)) {
					return false;
				} else {
					Block block = blockState.getBlock();
					if (block instanceof FluidDrainable fluidDrainable) {
						if (!fluidDrainable.tryDrainFluid(null, world, currentPos, blockState).isEmpty()) {
							return true;
						}
					}
					if (blockState.getBlock() instanceof FluidBlock) {
						world.setBlockState(currentPos, Blocks.AIR.getDefaultState(), 3);
					}
					return true;
				}
			}
		}) > 1;
	}
	
}
