package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.spells.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

public record MoonstoneBlastPayload(double x, double y, double z, float power, float knockbackMod, Vec3d playerVelocity) implements CustomPayload {
	
	public static final Id<MoonstoneBlastPayload> ID = SpectrumC2SPackets.makeId("moonstone_blast");
	public static final PacketCodec<PacketByteBuf, MoonstoneBlastPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.DOUBLE, MoonstoneBlastPayload::x,
			PacketCodecs.DOUBLE, MoonstoneBlastPayload::y,
			PacketCodecs.DOUBLE, MoonstoneBlastPayload::z,
			PacketCodecs.FLOAT, MoonstoneBlastPayload::power,
			PacketCodecs.FLOAT, MoonstoneBlastPayload::knockbackMod,
			PacketCodecHelper.VEC3D, MoonstoneBlastPayload::playerVelocity,
			MoonstoneBlastPayload::new
	);
	
	public static void sendMoonstoneBlast(ServerWorld serverWorld, MoonstoneStrike moonstoneStrike) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, BlockPos.ofFloored(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ()))) {
			Vec3d playerVelocity = moonstoneStrike.getAffectedPlayers().getOrDefault(player, Vec3d.ZERO);
			ServerPlayNetworking.send(player, new MoonstoneBlastPayload(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ(), moonstoneStrike.getPower(), moonstoneStrike.getKnockbackMod(), playerVelocity));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(MoonstoneBlastPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		ClientWorld world = client.world;
		PlayerEntity player = context.player();
		Vec3d playerVelocity = payload.playerVelocity();
		MoonstoneStrike.create(world, null, null, payload.x, payload.y, payload.z, payload.power, payload.knockbackMod);
		player.setVelocity(player.getVelocity().add(playerVelocity.x, playerVelocity.y, playerVelocity.z));
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
