package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import earth.terrarium.pastel.registries.PastelBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class WorldMixin {

    @Shadow
    @Final
    private BiomeManager biomeManager;

    // using a mixin additional to net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents,
    // since the fabric api event does not trigger for indirect breaks, like via projectile
    @Inject(method = "destroyBlock", at = @At(value = "INVOKE",
                                              target = "Lnet/minecraft/world/level/Level;getFluidState" +
                                                       "(Lnet/minecraft/core/BlockPos;)" +
                                                       "Lnet/minecraft/world/level/material/FluidState;"))
    public void breakBlock(
        BlockPos pos, boolean drop, Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir,
        @Local BlockState state
    ) {
        if (breakingEntity instanceof ServerPlayer serverPlayerEntity) {
            PastelAdvancementCriteria.BLOCK_BROKEN.trigger(serverPlayerEntity, state);
        }
    }

    @Inject(method = "isRainingAt", at = @At("HEAD"), cancellable = true)
    public void forcePermanentRain(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var biome = biomeManager.getBiome(pos);
        if (biome.is(PastelBiomes.DEEP_DRIPSTONE_CAVES) || biome.is(PastelBiomes.DRAGONROT_SWAMP))
            cir.setReturnValue(true);
    }
}
