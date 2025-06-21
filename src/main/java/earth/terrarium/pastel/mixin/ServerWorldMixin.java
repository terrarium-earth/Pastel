package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.events.game.PastelGameEvents;
import earth.terrarium.pastel.helpers.TimeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
	private void emitSpawnEntityEvent(Entity entity, final CallbackInfoReturnable<Boolean> info) {
		entity.gameEvent(PastelGameEvents.ENTITY_SPAWNED);
	}

	@Inject(method = "onBlockStateChange", at = @At("HEAD"))
	private void emitBlockChangedEvent(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo ci) {
		((ServerLevel) (Object) this).gameEvent(PastelGameEvents.BLOCK_CHANGED, pos, GameEvent.Context.of(newBlock));
	}

	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
	private void sleepThroughDay(ServerLevel instance, long timeOfDay, Operation<Void> original, @Local long l) {
		var time = TimeHelper.getTimeOfDay(l);
		if (time.isDay()) {
			setDayTime((l - l % 24000L) - 11000L);
			return;
		}
		original.call(instance, timeOfDay);
	}
}