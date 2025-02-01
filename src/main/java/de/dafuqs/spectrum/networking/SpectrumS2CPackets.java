package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;

public class SpectrumS2CPackets {

	public static void register() {
		PayloadTypeRegistry.playS2C().register(PlayParticleWithRandomOffsetAndVelocityPayload.ID, PlayParticleWithRandomOffsetAndVelocityPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayParticleWithExactVelocityPayload.ID, PlayParticleWithExactVelocityPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayParticleWithPatternAndVelocityPayload.ID, PlayParticleWithPatternAndVelocityPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayParticleAroundBlockSidesPayload.ID, PlayParticleAroundBlockSidesPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(StartSkyLerpingPayload.ID, StartSkyLerpingPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayPedestalCraftingFinishedParticlePayload.ID, PlayPedestalCraftingFinishedParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayShootingStarParticlesPayload.ID, PlayShootingStarParticlesPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayFusionCraftingInProgressParticlePayload.ID, PlayFusionCraftingInProgressParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayFusionCraftingFinishedParticlePayload.ID, PlayFusionCraftingFinishedParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayMemoryManifestingParticlesPayload.ID, PlayMemoryManifestingParticlesPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayPedestalUpgradedParticlePayload.ID, PlayPedestalUpgradedParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayPedestalStartCraftingParticlePayload.ID, PlayPedestalStartCraftingParticlePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(ParticleSpawnerConfigurationS2CPayload.ID, ParticleSpawnerConfigurationS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PastelTransmissionPayload.ID, PastelTransmissionPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(TypedTransmissionPayload.ID, TypedTransmissionPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(ColorTransmissionPayload.ID, ColorTransmissionPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayBlockBoundSoundInstancePayload.ID, PlayBlockBoundSoundInstancePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayTakeOffBeltSoundInstancePayload.ID, PlayTakeOffBeltSoundInstancePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(UpdateBlockEntityInkPayload.ID, UpdateBlockEntityInkPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(InkColorSelectedS2CPayload.ID, InkColorSelectedS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayPresentOpeningParticlesPayload.ID, PlayPresentOpeningParticlesPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayAscensionAppliedEffectsPayload.ID, PlayAscensionAppliedEffectsPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PlayDivinityAppliedEffectsPayload.ID, PlayDivinityAppliedEffectsPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(MoonstoneBlastPayload.ID, MoonstoneBlastPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SyncArtisansAtlasPayload.ID, SyncArtisansAtlasPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(SyncMentalPresencePayload.ID, SyncMentalPresencePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(CompactingChestStatusUpdatePayload.ID, CompactingChestStatusUpdatePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(FabricationChestStatusUpdatePayload.ID, FabricationChestStatusUpdatePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(BlackHoleChestStatusUpdatePayload.ID, BlackHoleChestStatusUpdatePayload.CODEC);
		PayloadTypeRegistry.playS2C().register(PastelNodeStatusUpdatePayload.ID, PastelNodeStatusUpdatePayload.CODEC);
	}
	
	@Environment(EnvType.CLIENT)
	public static void registerS2CReceivers() {
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleWithRandomOffsetAndVelocityPayload.ID, PlayParticleWithRandomOffsetAndVelocityPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleWithExactVelocityPayload.ID, PlayParticleWithExactVelocityPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleWithPatternAndVelocityPayload.ID, PlayParticleWithPatternAndVelocityPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayParticleAroundBlockSidesPayload.ID, PlayParticleAroundBlockSidesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(StartSkyLerpingPayload.ID, StartSkyLerpingPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPedestalCraftingFinishedParticlePayload.ID, PlayPedestalCraftingFinishedParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayShootingStarParticlesPayload.ID, PlayShootingStarParticlesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayFusionCraftingInProgressParticlePayload.ID, PlayFusionCraftingInProgressParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayFusionCraftingFinishedParticlePayload.ID, PlayFusionCraftingFinishedParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayMemoryManifestingParticlesPayload.ID, PlayMemoryManifestingParticlesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPedestalUpgradedParticlePayload.ID, PlayPedestalUpgradedParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPedestalStartCraftingParticlePayload.ID, PlayPedestalStartCraftingParticlePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(ParticleSpawnerConfigurationS2CPayload.ID, ParticleSpawnerConfigurationS2CPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PastelTransmissionPayload.ID, PastelTransmissionPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(TypedTransmissionPayload.ID, TypedTransmissionPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(ColorTransmissionPayload.ID, ColorTransmissionPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayBlockBoundSoundInstancePayload.ID, PlayBlockBoundSoundInstancePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayTakeOffBeltSoundInstancePayload.ID, PlayTakeOffBeltSoundInstancePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(UpdateBlockEntityInkPayload.ID, UpdateBlockEntityInkPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(InkColorSelectedS2CPayload.ID, InkColorSelectedS2CPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayPresentOpeningParticlesPayload.ID, PlayPresentOpeningParticlesPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayAscensionAppliedEffectsPayload.ID, PlayAscensionAppliedEffectsPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PlayDivinityAppliedEffectsPayload.ID, PlayDivinityAppliedEffectsPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(MoonstoneBlastPayload.ID, MoonstoneBlastPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(SyncArtisansAtlasPayload.ID, SyncArtisansAtlasPayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(SyncMentalPresencePayload.ID, SyncMentalPresencePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(CompactingChestStatusUpdatePayload.ID, CompactingChestStatusUpdatePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(FabricationChestStatusUpdatePayload.ID, FabricationChestStatusUpdatePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(BlackHoleChestStatusUpdatePayload.ID, BlackHoleChestStatusUpdatePayload.getPayloadHandler());
		ClientPlayNetworking.registerGlobalReceiver(PastelNodeStatusUpdatePayload.ID, PastelNodeStatusUpdatePayload.getPayloadHandler());
	}

}
