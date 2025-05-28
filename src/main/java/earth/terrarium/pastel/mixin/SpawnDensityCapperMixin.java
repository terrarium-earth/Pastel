package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.mixin.accessors.DensityCapAccessor;
import earth.terrarium.pastel.registries.SpectrumStatusEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.LocalMobCapCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalMobCapCalculator.class)
public class SpawnDensityCapperMixin {

    @WrapOperation(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LocalMobCapCalculator$MobCounts;canSpawn(Lnet/minecraft/world/entity/MobCategory;)Z"))
    public boolean reduceSpawnCap(LocalMobCapCalculator.MobCounts instance, MobCategory spawnGroup, Operation<Boolean> original, @Local LocalMobCapCalculator.MobCounts densityCap, @Local ServerPlayer serverPlayerEntity) {
        var calming = serverPlayerEntity.getEffect(SpectrumStatusEffects.CALMING);

        if (calming != null) {
            return densityCap == null || ((DensityCapAccessor) densityCap).getCounts().getOrDefault(spawnGroup, 0) < spawnGroup.getMaxInstancesPerChunk() - ((calming.getAmplifier() + 1) * 2.5);
        }

        return original.call(instance, spawnGroup);
    }
}
