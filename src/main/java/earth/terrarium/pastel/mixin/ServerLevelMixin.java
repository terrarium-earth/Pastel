package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.events.game.PastelGameEvents;
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

    @Shadow @Final private MinecraftServer server;

    @Inject(method = "onBlockStateChange", at = @At("HEAD"))
    private void emitBlockChangedEvent(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo ci) {
        if (!this.server.isSameThread())
            return;

        ((ServerLevel) (Object) this).gameEvent(PastelGameEvents.BLOCK_CHANGED, pos, GameEvent.Context.of(newBlock));
    }
}
