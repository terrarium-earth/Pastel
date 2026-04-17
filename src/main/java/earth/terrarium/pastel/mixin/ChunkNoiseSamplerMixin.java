package earth.terrarium.pastel.mixin;

import com.google.common.collect.ImmutableList;
import earth.terrarium.pastel.imbrifer.ImbriferOreVeinSampler;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseChunk.BlockStateFiller;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NoiseChunk.class)
public abstract class ChunkNoiseSamplerMixin {

    @Inject(method = "<init>",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;oreVeinsEnabled()Z"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void init(
        int cellCountXZ, RandomState random, int firstNoiseX, int firstNoiseZ, NoiseSettings noiseSettings,
        DensityFunctions.BeardifierOrMarker beardifier, NoiseGeneratorSettings noiseGeneratorSettings,
        Aquifer.FluidPicker fluidPicker, Blender blendifier, CallbackInfo ci, NoiseRouter noiseRouter,
        NoiseRouter noiseRouter2, ImmutableList.Builder<BlockStateFiller> builder, DensityFunction densityFunction
    ) {
        if (noiseGeneratorSettings.defaultBlock() == PastelBlocks.BLACKSLAG.get()
                                                                           .defaultBlockState()) {
            builder.add(
                ImbriferOreVeinSampler.create(
                    noiseRouter.veinToggle(), noiseRouter.veinRidged(), noiseRouter.veinGap(),
                    random.oreRandom()
                ));
        }
    }

}
