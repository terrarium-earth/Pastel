package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.spectrum.attachments.data.MiscPlayerData;
import de.dafuqs.spectrum.events.SpectrumGameEvents;
import de.dafuqs.spectrum.helpers.TimeHelper;
import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin {

	@Shadow public abstract void setDayTime(long timeOfDay);

	@Inject(at = @At("TAIL"), method = "addFreshEntity")
	private void spectrum$emitSpawnEntityEvent(Entity entity, final CallbackInfoReturnable<Boolean> info) {
		entity.gameEvent(SpectrumGameEvents.ENTITY_SPAWNED);
	}

	@Inject(method = "onBlockStateChange", at = @At("HEAD"))
	private void spectrum$emitBlockChangedEvent(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo ci) {
		((ServerLevel) (Object) this).gameEvent(SpectrumGameEvents.BLOCK_CHANGED, pos, GameEvent.Context.of(newBlock));
	}

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
	private void spectrum$sleepThroughDay(ServerLevel instance, long timeOfDay, Operation<Void> original, @Local long l) {
		var time = TimeHelper.getTimeOfDay(l);
		if (time.isDay()) {
			setDayTime((l - l % 24000L) - 11000L);
			return;
		}
		original.call(instance, timeOfDay);
	}

	@Inject(method = "method_18773", at = @At(value = "HEAD"))
	private static void spectrum$applyWakeupEffects(ServerPlayer player, CallbackInfo ci) {
		MiscPlayerData.get(player).resetSleepingState(false);
		player.removeEffect(SpectrumStatusEffects.SOMNOLENCE);
	}
}