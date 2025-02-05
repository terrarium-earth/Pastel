package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.color.*;
import de.dafuqs.spectrum.helpers.ColorHelper;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;
import org.joml.*;

public record PlayFusionCraftingFinishedParticlePayload(BlockPos pos, DyeColor color) implements CustomPayload {
	
	public static final Id<PlayFusionCraftingFinishedParticlePayload> ID = SpectrumC2SPackets.makeId("play_fusion_crafting_finished_particle");
	public static final PacketCodec<PacketByteBuf, PlayFusionCraftingFinishedParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayFusionCraftingFinishedParticlePayload::pos,
			DyeColor.PACKET_CODEC, PlayFusionCraftingFinishedParticlePayload::color,
			PlayFusionCraftingFinishedParticlePayload::new
	);
	
	public static void sendPlayFusionCraftingFinishedParticles(World world, BlockPos pos, @NotNull ItemStack itemStack) {
		DyeColor color = ColorRegistry.ITEM_COLORS.getMapping(itemStack.getItem(), DyeColor.LIGHT_GRAY);
		
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
			ServerPlayNetworking.send(player, new PlayFusionCraftingFinishedParticlePayload(pos, color));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayFusionCraftingFinishedParticlePayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		BlockPos pos = payload.pos;
		Vec3d sourcePos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
		
		Vector3f color = ColorHelper.getRGBVec(payload.color);
		float velocityModifier = 0.25F;
		for (Vec3d velocity : VectorPattern.SIXTEEN.getVectors()) {
			client.world.addParticle(
					new DynamicParticleEffect(SpectrumParticleTypes.WHITE_CRAFTING, 0.0F, color, 1.5F, 40, false, true),
					sourcePos.x, sourcePos.y, sourcePos.z,
					velocity.x * velocityModifier, 0.0F, velocity.z * velocityModifier
			);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
