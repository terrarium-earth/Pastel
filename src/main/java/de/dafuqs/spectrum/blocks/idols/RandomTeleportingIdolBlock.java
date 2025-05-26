package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RandomTeleportingIdolBlock extends IdolBlock {
	
	protected final int horizontalRange;
	protected final int verticalRange;
	
	public RandomTeleportingIdolBlock(Properties settings, ParticleOptions particleEffect, int horizontalRange, int verticalRange) {
		super(settings, particleEffect);
		this.horizontalRange = horizontalRange;
		this.verticalRange = verticalRange;
	}

	@Override
	public MapCodec<? extends RandomTeleportingIdolBlock> codec() {
		//TODO: Make the codec
		return null;
	}
	
	public static void teleportTo(ServerLevel world, Entity entity, int x, int y, int z) {
		teleportTo(world, entity, new BlockPos(x, y, z));
	}
	
	public static boolean teleportTo(ServerLevel world, Entity entity, BlockPos blockPos) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		// if in solid: move up
		while (mutable.getY() < world.getMaxBuildHeight() && world.getBlockState(mutable).blocksMotion()) {
			mutable.move(Direction.UP);
		}
		// if in air: move down
		while (mutable.getY() > world.getMinBuildHeight() && !world.getBlockState(mutable).blocksMotion()) {
			mutable.move(Direction.DOWN);
		}
		
		BlockState blockState = world.getBlockState(mutable);
		if (blockState.blocksMotion()) {
			double boundingBoxY = entity.getBoundingBox().getYsize(); // bouncy
			if (entity instanceof ServerPlayer serverPlayerEntity) {
				serverPlayerEntity.teleportTo((ServerLevel) serverPlayerEntity.level(), mutable.getX() + 0.5, mutable.getY() + boundingBoxY, mutable.getZ() + 0.5, serverPlayerEntity.getYRot(), serverPlayerEntity.getXRot());
				world.broadcastEntityEvent(serverPlayerEntity, EntityEvent.IN_LOVE_HEARTS);
				return true;
			} else if (entity instanceof LivingEntity livingEntity) {
				boolean success = livingEntity.randomTeleport(mutable.getX() + 0.5, mutable.getY() + boundingBoxY, mutable.getZ() + 0.5, true);
				if (success) {
					world.playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
					entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				}
				return success;
			} else {
				entity.teleportTo(mutable.getX() + 0.5, mutable.getY() + boundingBoxY, mutable.getZ() + 0.5);
				world.playSound(null, entity.xo, entity.yo, entity.zo, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
				entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.pastel.random_teleporting_idol.tooltip", horizontalRange));
	}
	
	@Override
	public boolean trigger(ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		if (entity != null) {
			RandomSource random = world.getRandom();
			int x = (int) (blockPos.getX() + (random.nextDouble() - 0.5D) * (this.horizontalRange + this.horizontalRange));
			int y = blockPos.getY() + (random.nextInt(this.verticalRange + this.verticalRange) - (this.verticalRange));
			int z = (int) (blockPos.getZ() + (random.nextDouble() - 0.5D) * (this.horizontalRange + this.horizontalRange));
			teleportTo(world, entity, x, y, z);
			return true;
		}
		return false;
	}
	
}
