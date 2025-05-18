package de.dafuqs.spectrum.entity.entity;

import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PhantomFrameEntity extends ItemFrame {

	public PhantomFrameEntity(EntityType<? extends ItemFrame> entityType, Level world) {
		super(entityType, world);
	}

	public PhantomFrameEntity(EntityType<? extends ItemFrame> type, Level world, BlockPos pos, Direction facing) {
		super(type, world, pos, facing);
	}

	@Override
	public boolean isInvisible() {
		if (this.getItem().isEmpty()) {
			return super.isInvisible();
		} else {
			return true;
		}
	}

	@Override
	protected ItemStack getFrameItemStack() {
		return new ItemStack(SpectrumItems.PHANTOM_FRAME);
	}

	@Override
	public void setItem(ItemStack value, boolean update) {
		super.setItem(value, update);
		if (update && this.isAlive() && !this.level().isClientSide()) {
			PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) this.level(), position(), ParticleTypes.END_ROD, 10, new Vec3(0, 0, 0), new Vec3(0.1, 0.1, 0.1));
			this.level().playSound(null, this, SpectrumSoundEvents.CRAFTING_DING, SoundSource.BLOCKS, 0.5F, 1.0F);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean success = super.hurt(source, amount);
		if (success && this.isAlive() && !this.level().isClientSide()) {
			PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity((ServerLevel) this.level(), position(), ParticleTypes.END_ROD, 10, new Vec3(0, 0, 0), new Vec3(0.1, 0.1, 0.1));
			this.level().playSound(null, this, SpectrumSoundEvents.CRAFTING_DING, SoundSource.BLOCKS, 0.5F, 1.0F);
		}
		return success;
	}
	
	public boolean isRedstonePowered() {
		return this.level().getBestNeighborSignal(this.blockPosition()) > 0;
	}
	
	public boolean shouldRenderAtMaxLight() {
		return isRedstonePowered();
	}
	
}
