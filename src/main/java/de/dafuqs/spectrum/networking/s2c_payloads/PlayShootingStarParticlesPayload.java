package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.shooting_star.ShootingStar;
import de.dafuqs.spectrum.entity.entity.ShootingStarEntity;
import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record PlayShootingStarParticlesPayload(Vec3 shootingStarPos, ShootingStar.Variant variant) implements CustomPacketPayload {
	
	public static final Type<PlayShootingStarParticlesPayload> ID = SpectrumC2SPackets.makeId("play_shooting_star_particles");
	public static final StreamCodec<FriendlyByteBuf, PlayShootingStarParticlesPayload> CODEC = StreamCodec.composite(
			PacketCodecHelper.VEC3D, PlayShootingStarParticlesPayload::shootingStarPos,
			ShootingStar.Variant.PACKET_CODEC, PlayShootingStarParticlesPayload::variant,
			PlayShootingStarParticlesPayload::new
	);
	
	public static void sendPlayShootingStarParticles(@NotNull ShootingStarEntity shootingStarEntity) {
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) shootingStarEntity.level(), shootingStarEntity.blockPosition())) {
			ServerPlayNetworking.send(player, new PlayShootingStarParticlesPayload(shootingStarEntity.position(), shootingStarEntity.getShootingStarType()));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayShootingStarParticlesPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		
		ShootingStarEntity.playHitParticles(client.level, payload.shootingStarPos.x, payload.shootingStarPos.y, payload.shootingStarPos.z, payload.variant, 25);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
