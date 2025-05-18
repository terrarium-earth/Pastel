package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.cca.HardcoreDeathComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerInteractionManagerMixin {
	
	@Shadow
	@Final
	protected ServerPlayer player;
	
	// If someone puts players out of spectator manually
	// forget about their hardcore death
	@Inject(at = @At("HEAD"), method = "setGameModeForPlayer")
	public void spectrum$mitigateFallDamageWithPuffCirclet(GameType gameMode, GameType previousGameMode, CallbackInfo ci) {
		if (gameMode != GameType.SPECTATOR && previousGameMode == GameType.SPECTATOR && HardcoreDeathComponent.hasHardcoreDeath(player.getGameProfile())) {
			HardcoreDeathComponent.removeHardcoreDeath(player.getGameProfile());
		}
	}
	
}
