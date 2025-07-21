package earth.terrarium.pastel.blocks.deeper_down;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.api.block.MoonstoneStrikeableBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class DragonboneBlock extends RotatedPillarBlock implements MoonstoneStrikeableBlock {

	public static final MapCodec<DragonboneBlock> CODEC = simpleCodec(DragonboneBlock::new);

	public DragonboneBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends DragonboneBlock> codec() {
		return CODEC;
	}
	
	@Override
	public void onMoonstoneStrike(Level world, BlockPos pos, @Nullable LivingEntity striker) {
		crack(world, pos);
	}
	
	public void crack(Level world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof DragonboneBlock) {
			world.setBlockAndUpdate(pos, PastelBlocks.CRACKED_DRAGONBONE.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
			if (world.isClientSide) {
				world.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 1.0F, Mth.randomBetween(world.random, 0.8F, 1.2F));
			}
		}
	}
	
	@Override
	public boolean dropFromExplosion(Explosion explosion) {
		return false;
	}
	
	@Override
	protected void onExplosionHit(BlockState state, Level world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
		if (state.getBlock() instanceof RotatedPillarBlock) {
			world.setBlockAndUpdate(pos, PastelBlocks.CRACKED_DRAGONBONE.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)));
		}
	}
}
