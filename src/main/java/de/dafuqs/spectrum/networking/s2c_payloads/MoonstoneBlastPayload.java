package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.spells.MoonstoneStrike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public record MoonstoneBlastPayload(double x, double y, double z, float power, float knockbackMod, Vec3 playerVelocity) implements CustomPacketPayload {
	
	public static final Type<MoonstoneBlastPayload> ID = SpectrumC2SPackets.makeId("moonstone_blast");
	public static final StreamCodec<FriendlyByteBuf, MoonstoneBlastPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE, MoonstoneBlastPayload::x,
			ByteBufCodecs.DOUBLE, MoonstoneBlastPayload::y,
			ByteBufCodecs.DOUBLE, MoonstoneBlastPayload::z,
			ByteBufCodecs.FLOAT, MoonstoneBlastPayload::power,
			ByteBufCodecs.FLOAT, MoonstoneBlastPayload::knockbackMod,
			PacketCodecHelper.VEC3D, MoonstoneBlastPayload::playerVelocity,
			MoonstoneBlastPayload::new
	);
	
	public static void sendMoonstoneBlast(ServerLevel serverWorld, MoonstoneStrike moonstoneStrike) {
		for (ServerPlayer player : PlayerLookup.tracking(serverWorld, BlockPos.containing(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ()))) {
			Vec3 playerVelocity = moonstoneStrike.getAffectedPlayers().getOrDefault(player, Vec3.ZERO);
			ServerPlayNetworking.send(player, new MoonstoneBlastPayload(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ(), moonstoneStrike.getPower(), moonstoneStrike.getKnockbackMod(), playerVelocity));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(MoonstoneBlastPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		ClientLevel world = client.level;
		Player player = context.player();
		Vec3 playerVelocity = payload.playerVelocity();
		MoonstoneStrike.create(world, null, null, payload.x, payload.y, payload.z, payload.power, payload.knockbackMod);
		player.setDeltaMovement(player.getDeltaMovement().add(playerVelocity.x, playerVelocity.y, playerVelocity.z));
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
