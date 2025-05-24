package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.networking.s2c_payloads.BlackHoleChestStatusUpdatePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.ColorTransmissionPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.CompactingChestStatusUpdatePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.FabricationChestStatusUpdatePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.InkColorSelectedS2CPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.MoonstoneBlastPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.ParticleSpawnerConfigurationS2CPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PastelNetworkEdgeSyncPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PastelNetworkRemovedPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PastelNodeStatusUpdatePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PastelTransmissionPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayAscensionAppliedEffectsPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayBlockBoundSoundInstancePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayDivinityAppliedEffectsPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayFusionCraftingFinishedParticlePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayFusionCraftingInProgressParticlePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayMemoryManifestingParticlesPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleAroundBlockSidesPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithPatternAndVelocityPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayPedestalCraftingFinishedParticlePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayPedestalStartCraftingParticlePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayPedestalUpgradedParticlePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayPresentOpeningParticlesPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayShootingStarParticlesPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayTakeOffBeltSoundInstancePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.StartSkyLerpingPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.SyncArtisansAtlasPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.SyncMentalPresencePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.TypedTransmissionPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.UpdateBlockEntityInkPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class SpectrumS2CPackets {

	@SubscribeEvent
	public static void register(PayloadRegistrar registrar) {
		registrar.playToClient(PlayParticleWithRandomOffsetAndVelocityPayload.ID, PlayParticleWithRandomOffsetAndVelocityPayload.CODEC, PlayParticleWithRandomOffsetAndVelocityPayload::execute);
		registrar.playToClient(PlayParticleWithExactVelocityPayload.ID, PlayParticleWithExactVelocityPayload.CODEC, PlayParticleWithExactVelocityPayload::execute);
		registrar.playToClient(PlayParticleWithPatternAndVelocityPayload.ID, PlayParticleWithPatternAndVelocityPayload.CODEC, PlayParticleWithPatternAndVelocityPayload::execute);
		registrar.playToClient(PlayParticleAroundBlockSidesPayload.ID, PlayParticleAroundBlockSidesPayload.CODEC, PlayParticleAroundBlockSidesPayload::execute);
		registrar.playToClient(StartSkyLerpingPayload.ID, StartSkyLerpingPayload.CODEC, StartSkyLerpingPayload::execute);
		registrar.playToClient(PlayPedestalCraftingFinishedParticlePayload.ID, PlayPedestalCraftingFinishedParticlePayload.CODEC, PlayPedestalCraftingFinishedParticlePayload::execute);
		registrar.playToClient(PlayShootingStarParticlesPayload.ID, PlayShootingStarParticlesPayload.CODEC, PlayShootingStarParticlesPayload::execute);
		registrar.playToClient(PlayFusionCraftingInProgressParticlePayload.ID, PlayFusionCraftingInProgressParticlePayload.CODEC, PlayFusionCraftingInProgressParticlePayload::execute);
		registrar.playToClient(PlayFusionCraftingFinishedParticlePayload.ID, PlayFusionCraftingFinishedParticlePayload.CODEC, PlayFusionCraftingFinishedParticlePayload::execute);
		registrar.playToClient(PlayMemoryManifestingParticlesPayload.ID, PlayMemoryManifestingParticlesPayload.CODEC, PlayMemoryManifestingParticlesPayload::execute);
		registrar.playToClient(PlayPedestalUpgradedParticlePayload.ID, PlayPedestalUpgradedParticlePayload.CODEC, PlayPedestalUpgradedParticlePayload::execute);
		registrar.playToClient(PlayPedestalStartCraftingParticlePayload.ID, PlayPedestalStartCraftingParticlePayload.CODEC, PlayPedestalStartCraftingParticlePayload::execute);
		registrar.playToClient(ParticleSpawnerConfigurationS2CPayload.ID, ParticleSpawnerConfigurationS2CPayload.CODEC, ParticleSpawnerConfigurationS2CPayload::execute);
		registrar.playToClient(PastelTransmissionPayload.ID, PastelTransmissionPayload.CODEC, PastelTransmissionPayload::execute);
		registrar.playToClient(TypedTransmissionPayload.ID, TypedTransmissionPayload.CODEC, TypedTransmissionPayload::execute);
		registrar.playToClient(ColorTransmissionPayload.ID, ColorTransmissionPayload.CODEC, ColorTransmissionPayload::execute);
		registrar.playToClient(PlayBlockBoundSoundInstancePayload.ID, PlayBlockBoundSoundInstancePayload.CODEC, PlayBlockBoundSoundInstancePayload::execute);
		registrar.playToClient(PlayTakeOffBeltSoundInstancePayload.ID, PlayTakeOffBeltSoundInstancePayload.CODEC, PlayTakeOffBeltSoundInstancePayload::execute);
		registrar.playToClient(UpdateBlockEntityInkPayload.ID, UpdateBlockEntityInkPayload.CODEC, UpdateBlockEntityInkPayload::execute);
		registrar.playToClient(InkColorSelectedS2CPayload.ID, InkColorSelectedS2CPayload.CODEC, InkColorSelectedS2CPayload::execute);
		registrar.playToClient(PlayPresentOpeningParticlesPayload.ID, PlayPresentOpeningParticlesPayload.CODEC, PlayPresentOpeningParticlesPayload::execute);
		registrar.playToClient(PlayAscensionAppliedEffectsPayload.ID, PlayAscensionAppliedEffectsPayload.CODEC, PlayAscensionAppliedEffectsPayload::execute);
		registrar.playToClient(PlayDivinityAppliedEffectsPayload.ID, PlayDivinityAppliedEffectsPayload.CODEC, PlayDivinityAppliedEffectsPayload::execute);
		registrar.playToClient(MoonstoneBlastPayload.ID, MoonstoneBlastPayload.CODEC, MoonstoneBlastPayload::execute);
		registrar.playToClient(SyncArtisansAtlasPayload.ID, SyncArtisansAtlasPayload.CODEC, SyncArtisansAtlasPayload::execute);
		registrar.playToClient(SyncMentalPresencePayload.ID, SyncMentalPresencePayload.CODEC, SyncMentalPresencePayload::execute);
		registrar.playToClient(CompactingChestStatusUpdatePayload.ID, CompactingChestStatusUpdatePayload.CODEC, CompactingChestStatusUpdatePayload::execute);
		registrar.playToClient(FabricationChestStatusUpdatePayload.ID, FabricationChestStatusUpdatePayload.CODEC, FabricationChestStatusUpdatePayload::execute);
		registrar.playToClient(BlackHoleChestStatusUpdatePayload.ID, BlackHoleChestStatusUpdatePayload.CODEC, BlackHoleChestStatusUpdatePayload::execute);
		registrar.playToClient(PastelNodeStatusUpdatePayload.ID, PastelNodeStatusUpdatePayload.CODEC, PastelNodeStatusUpdatePayload::execute);
		registrar.playToClient(PastelNetworkEdgeSyncPayload.ID, PastelNetworkEdgeSyncPayload.CODEC, PastelNetworkEdgeSyncPayload::execute);
		registrar.playToClient(PastelNetworkRemovedPayload.ID, PastelNetworkRemovedPayload.CODEC, PastelNetworkRemovedPayload::execute);
	}
	
}
