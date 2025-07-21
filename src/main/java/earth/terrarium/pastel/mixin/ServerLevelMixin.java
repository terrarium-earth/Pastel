package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.events.game.PastelGameEvents;
import earth.terrarium.pastel.helpers.interaction.TimeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @Shadow
    public abstract void setDayTime(long timeOfDay);

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "onBlockStateChange", at = @At("HEAD"))
    private void emitBlockChangedEvent(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo ci) {
        if (!this.server.isSameThread())
            return;

        ((ServerLevel) (Object) this).gameEvent(PastelGameEvents.BLOCK_CHANGED, pos, GameEvent.Context.of(newBlock));
    }

    @WrapOperation(method = "tick",
                   at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
    private void sleepThroughDay(ServerLevel instance, long timeOfDay, Operation<Void> original, @Local long l) {
        var time = TimeHelper.getTimeOfDay(l);
        if (time.isDay()) {
            setDayTime((l - l % 24000L) - 11000L);
            return;
        }
        original.call(instance, timeOfDay);
    }
}
