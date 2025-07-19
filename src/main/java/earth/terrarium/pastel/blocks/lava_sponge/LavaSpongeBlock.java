package earth.terrarium.pastel.blocks.lava_sponge;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class LavaSpongeBlock extends SpongeBlock {

	public static final MapCodec<LavaSpongeBlock> CODEC = simpleCodec(LavaSpongeBlock::new);

	public LavaSpongeBlock(Properties settings) {
		super(settings);
	}

//	@Override
//	public MapCodec<? extends LavaSpongeBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}

	@Override
	protected void tryAbsorbWater(Level world, BlockPos pos) {
		if (this.absorbLava(world, pos)) {
			world.setBlock(pos, PastelBlocks.WET_LAVA_SPONGE.get().defaultBlockState(), 2);
			world.playSound(null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}
	
	private boolean absorbLava(Level world, BlockPos pos) {
		return BlockPos.breadthFirstTraversal(pos, 12, 512, (currentPos, queuer) -> {
			for (Direction direction : Direction.values()) {
				queuer.accept(currentPos.relative(direction));
			}
		}, (currentPos) -> {
			if (currentPos.equals(pos)) {
				return true;
			} else {
				BlockState blockState = world.getBlockState(currentPos);
				FluidState fluidState = world.getFluidState(currentPos);
				if (!fluidState.is(FluidTags.LAVA)) {
					return false;
				} else {
					Block block = blockState.getBlock();
					if (block instanceof BucketPickup fluidDrainable) {
						if (!fluidDrainable.pickupBlock(null, world, currentPos, blockState).isEmpty()) {
							return true;
						}
					}
					if (blockState.getBlock() instanceof LiquidBlock) {
						world.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3);
					}
					return true;
				}
			}
		}) > 1;
	}
	
}
