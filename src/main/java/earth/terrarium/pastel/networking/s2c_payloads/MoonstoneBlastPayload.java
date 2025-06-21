package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.helpers.PacketCodecHelper;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.spells.MoonstoneStrike;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
	
	public static final Type<MoonstoneBlastPayload> ID = PastelC2SPackets.makeId("moonstone_blast");
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
		for (ServerPlayer player : serverWorld.getChunkSource().chunkMap.getPlayers(new ChunkPos(BlockPos.containing(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ())), false)) {
			PacketDistributor.sendToPlayer(player,
					new MoonstoneBlastPayload(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ(), moonstoneStrike.getPower(), moonstoneStrike.getKnockbackMod(), moonstoneStrike.getAffectedPlayers().getOrDefault(player, Vec3.ZERO)));
		}
	}
	
	public static void execute(MoonstoneBlastPayload payload, IPayloadContext context) {
		Player player = context.player();
		Vec3 playerVelocity = payload.playerVelocity();
		MoonstoneStrike.create(player.level(), null, null, payload.x, payload.y, payload.z, payload.power, payload.knockbackMod);
		player.setDeltaMovement(player.getDeltaMovement().add(playerVelocity.x, playerVelocity.y, playerVelocity.z));
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
