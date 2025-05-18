package de.dafuqs.spectrum.mixin.client;

import de.dafuqs.spectrum.SpectrumClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Environment(EnvType.CLIENT)
@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin {
	
	@ModifyArg(method = "timeOfDay", at = @At(value = "INVOKE", target = "Ljava/util/OptionalLong;orElse(J)J"))
	private long spectrum$getLerpedSkyAngle(long time) {
		if (!Minecraft.getInstance().isPaused() && SpectrumClient.skyLerper.isActive((DimensionType) (Object) this)) {
			return SpectrumClient.skyLerper.tickLerp(time, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false));
		} else {
			return time;
		}
	}
	
}
