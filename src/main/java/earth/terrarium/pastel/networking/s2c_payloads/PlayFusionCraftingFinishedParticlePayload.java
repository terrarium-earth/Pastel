package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.api.color.ColorRegistry;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.particle.effect.DynamicParticleEffect;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record PlayFusionCraftingFinishedParticlePayload(BlockPos pos, InkColor color) implements CustomPacketPayload {
	
	public static final Type<PlayFusionCraftingFinishedParticlePayload> ID = PastelC2SPackets.makeId("play_fusion_crafting_finished_particle");
	public static final StreamCodec<FriendlyByteBuf, PlayFusionCraftingFinishedParticlePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PlayFusionCraftingFinishedParticlePayload::pos,
			InkColor.STREAM_CODEC, PlayFusionCraftingFinishedParticlePayload::color,
			PlayFusionCraftingFinishedParticlePayload::new
	);
	
	public static void sendPlayFusionCraftingFinishedParticles(Level world, BlockPos pos, @NotNull ItemStack itemStack) {
		InkColor inkColor = ColorRegistry.ITEM_COLORS.getMapping(itemStack.getItem(), InkColors.LIGHT_GRAY);
		PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) world, new ChunkPos(pos), new PlayFusionCraftingFinishedParticlePayload(pos, inkColor));
	}
	
	@SuppressWarnings("resource")
	public static void execute(PlayFusionCraftingFinishedParticlePayload payload, IPayloadContext context) {
		BlockPos pos = payload.pos;
		Vec3 sourcePos = new Vec3(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
		
		Vector3f color = payload.color.getColorVec();
		float velocityModifier = 0.25F;
		for (Vec3 velocity : VectorPattern.SIXTEEN.getVectors()) {
			context.player().level().addParticle(
					new DynamicParticleEffect(ColoredCraftingParticleEffect.of(payload.color.getColorInt()).getType(), 0.0F, color, 1.5F, 40, false, true),
					sourcePos.x, sourcePos.y, sourcePos.z,
					velocity.x * velocityModifier, 0.0F, velocity.z * velocityModifier
			);
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
