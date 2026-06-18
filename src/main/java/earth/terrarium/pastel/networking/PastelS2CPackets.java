package earth.terrarium.pastel.networking;

import earth.terrarium.pastel.attachments.HardcoreDeathTracker;
import earth.terrarium.pastel.attachments.data.ConsumptionRingData;
import earth.terrarium.pastel.attachments.data.EverpromiseRibbonData;
import earth.terrarium.pastel.attachments.data.InertiaData;
import earth.terrarium.pastel.attachments.data.LastKillData;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.attachments.data.SpectacleData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeData;
import earth.terrarium.pastel.networking.s2c_payloads.BlackHoleChestStatusUpdatePayload;
import earth.terrarium.pastel.networking.s2c_payloads.ColorTransmissionPayload;
import earth.terrarium.pastel.networking.s2c_payloads.CompactingChestStatusUpdatePayload;
import earth.terrarium.pastel.networking.s2c_payloads.FabricationChestStatusUpdatePayload;
import earth.terrarium.pastel.networking.s2c_payloads.InkColorSelectedS2CPayload;
import earth.terrarium.pastel.networking.s2c_payloads.MoonstoneBlastPayload;
import earth.terrarium.pastel.networking.s2c_payloads.ParticleSpawnerConfigurationS2CPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PastelNetworkEdgeSyncPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PastelNetworkRemovedPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PastelNodeStatusUpdatePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PastelTransmissionPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayAscensionAppliedEffectsPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayBlockBoundSoundInstancePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayDivinityAppliedEffectsPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayFusionCraftingFinishedParticlePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayFusionCraftingInProgressParticlePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayMemoryManifestingParticlesPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleAroundBlockSidesPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithPatternAndVelocityPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayPedestalCraftingFinishedParticlePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayPedestalStartCraftingParticlePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayPresentOpeningParticlesPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayShootingStarParticlesPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayTakeOffBeltSoundInstancePayload;
import earth.terrarium.pastel.networking.s2c_payloads.StartSkyLerpingPayload;
import earth.terrarium.pastel.networking.s2c_payloads.SyncArtisansAtlasPayload;
import earth.terrarium.pastel.networking.s2c_payloads.SyncMentalPresencePayload;
import earth.terrarium.pastel.networking.s2c_payloads.TypedTransmissionPayload;
import earth.terrarium.pastel.networking.s2c_payloads.UpdateBlockEntityInkPayload;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PastelS2CPackets {

    public static void register(PayloadRegistrar registrar) {
        registrar
            .playToClient(
                PlayParticleWithRandomOffsetAndVelocityPayload.ID,
                PlayParticleWithRandomOffsetAndVelocityPayload.CODEC,
                PlayParticleWithRandomOffsetAndVelocityPayload::execute
            );
        registrar
            .playToClient(
                PlayParticleWithExactVelocityPayload.ID,
                PlayParticleWithExactVelocityPayload.CODEC,
                PlayParticleWithExactVelocityPayload::execute
            );
        registrar
            .playToClient(
                PlayParticleWithPatternAndVelocityPayload.ID,
                PlayParticleWithPatternAndVelocityPayload.CODEC,
                PlayParticleWithPatternAndVelocityPayload::execute
            );
        registrar
            .playToClient(
                PlayParticleAroundBlockSidesPayload.ID,
                PlayParticleAroundBlockSidesPayload.CODEC,
                PlayParticleAroundBlockSidesPayload::execute
            );
        registrar
            .playToClient(
                StartSkyLerpingPayload.ID,
                StartSkyLerpingPayload.CODEC,
                StartSkyLerpingPayload::execute
            );
        registrar
            .playToClient(
                PlayPedestalCraftingFinishedParticlePayload.ID,
                PlayPedestalCraftingFinishedParticlePayload.CODEC,
                PlayPedestalCraftingFinishedParticlePayload::execute
            );
        registrar
            .playToClient(
                PlayShootingStarParticlesPayload.ID,
                PlayShootingStarParticlesPayload.CODEC,
                PlayShootingStarParticlesPayload::execute
            );
        registrar
            .playToClient(
                PlayFusionCraftingInProgressParticlePayload.ID,
                PlayFusionCraftingInProgressParticlePayload.CODEC,
                PlayFusionCraftingInProgressParticlePayload::execute
            );
        registrar
            .playToClient(
                PlayFusionCraftingFinishedParticlePayload.ID,
                PlayFusionCraftingFinishedParticlePayload.CODEC,
                PlayFusionCraftingFinishedParticlePayload::execute
            );
        registrar
            .playToClient(
                PlayMemoryManifestingParticlesPayload.ID,
                PlayMemoryManifestingParticlesPayload.CODEC,
                PlayMemoryManifestingParticlesPayload::execute
            );
        registrar
            .playToClient(
                PlayPedestalStartCraftingParticlePayload.ID,
                PlayPedestalStartCraftingParticlePayload.CODEC,
                PlayPedestalStartCraftingParticlePayload::execute
            );
        registrar
            .playToClient(
                ParticleSpawnerConfigurationS2CPayload.ID,
                ParticleSpawnerConfigurationS2CPayload.CODEC,
                ParticleSpawnerConfigurationS2CPayload::execute
            );
        registrar
            .playToClient(
                PastelTransmissionPayload.ID,
                PastelTransmissionPayload.CODEC,
                PastelTransmissionPayload::execute
            );
        registrar
            .playToClient(
                TypedTransmissionPayload.ID,
                TypedTransmissionPayload.CODEC,
                TypedTransmissionPayload::execute
            );
        registrar
            .playToClient(
                ColorTransmissionPayload.ID,
                ColorTransmissionPayload.CODEC,
                ColorTransmissionPayload::execute
            );
        registrar
            .playToClient(
                PlayBlockBoundSoundInstancePayload.ID,
                PlayBlockBoundSoundInstancePayload.CODEC,
                PlayBlockBoundSoundInstancePayload::execute
            );
        registrar
            .playToClient(
                PlayTakeOffBeltSoundInstancePayload.ID,
                PlayTakeOffBeltSoundInstancePayload.CODEC,
                PlayTakeOffBeltSoundInstancePayload::execute
            );
        registrar
            .playToClient(
                UpdateBlockEntityInkPayload.ID,
                UpdateBlockEntityInkPayload.CODEC,
                UpdateBlockEntityInkPayload::execute
            );
        registrar
            .playToClient(
                InkColorSelectedS2CPayload.ID,
                InkColorSelectedS2CPayload.CODEC,
                InkColorSelectedS2CPayload::execute
            );
        registrar
            .playToClient(
                PlayPresentOpeningParticlesPayload.ID,
                PlayPresentOpeningParticlesPayload.CODEC,
                PlayPresentOpeningParticlesPayload::execute
            );
        registrar
            .playToClient(
                PlayAscensionAppliedEffectsPayload.ID,
                PlayAscensionAppliedEffectsPayload.CODEC,
                PlayAscensionAppliedEffectsPayload::execute
            );
        registrar
            .playToClient(
                PlayDivinityAppliedEffectsPayload.ID,
                PlayDivinityAppliedEffectsPayload.CODEC,
                PlayDivinityAppliedEffectsPayload::execute
            );
        registrar.playToClient(MoonstoneBlastPayload.ID, MoonstoneBlastPayload.CODEC, MoonstoneBlastPayload::execute);
        registrar
            .playToClient(
                SyncArtisansAtlasPayload.ID,
                SyncArtisansAtlasPayload.CODEC,
                SyncArtisansAtlasPayload::execute
            );
        registrar
            .playToClient(
                SyncMentalPresencePayload.ID,
                SyncMentalPresencePayload.CODEC,
                SyncMentalPresencePayload::execute
            );
        registrar
            .playToClient(
                CompactingChestStatusUpdatePayload.ID,
                CompactingChestStatusUpdatePayload.CODEC,
                CompactingChestStatusUpdatePayload::execute
            );
        registrar
            .playToClient(
                FabricationChestStatusUpdatePayload.ID,
                FabricationChestStatusUpdatePayload.CODEC,
                FabricationChestStatusUpdatePayload::execute
            );
        registrar
            .playToClient(
                BlackHoleChestStatusUpdatePayload.ID,
                BlackHoleChestStatusUpdatePayload.CODEC,
                BlackHoleChestStatusUpdatePayload::execute
            );
        registrar
            .playToClient(
                PastelNodeStatusUpdatePayload.ID,
                PastelNodeStatusUpdatePayload.CODEC,
                PastelNodeStatusUpdatePayload::execute
            );
        registrar
            .playToClient(
                PastelNetworkEdgeSyncPayload.ID,
                PastelNetworkEdgeSyncPayload.CODEC,
                PastelNetworkEdgeSyncPayload::execute
            );
        registrar
            .playToClient(
                PastelNetworkRemovedPayload.ID,
                PastelNetworkRemovedPayload.CODEC,
                PastelNetworkRemovedPayload::execute
            );

        // Data attachment stuff
        registrar
            .playToClient(
                PrimordialFireData.Payload.TYPE,
                PrimordialFireData.Payload.CODEC,
                PrimordialFireData.Payload::execute
            );
        registrar
            .playToClient(
                ConsumptionRingData.Payload.TYPE,
                ConsumptionRingData.Payload.CODEC,
                ConsumptionRingData.Payload::execute
            );
        registrar.playToClient(AzureDikeData.Payload.TYPE, AzureDikeData.Payload.CODEC, AzureDikeData.Payload::execute);
        registrar
            .playToClient(
                EverpromiseRibbonData.Payload.TYPE,
                EverpromiseRibbonData.Payload.CODEC,
                EverpromiseRibbonData.Payload::execute
            );
        registrar.playToClient(LastKillData.Payload.TYPE, LastKillData.Payload.CODEC, LastKillData.Payload::execute);
        registrar
            .playToClient(
                MiscPlayerData.Payload.TYPE,
                MiscPlayerData.Payload.CODEC,
                MiscPlayerData.Payload::execute
            );
        registrar.playToClient(InertiaData.Payload.TYPE, InertiaData.Payload.CODEC, InertiaData.Payload::execute);
        registrar.playToClient(SpectacleData.Payload.TYPE, SpectacleData.Payload.CODEC, SpectacleData.Payload::execute);

        // SavedData stuff
        registrar
            .playToClient(
                HardcoreDeathTracker.SyncPayload.TYPE,
                HardcoreDeathTracker.SyncPayload.CODEC,
                HardcoreDeathTracker.SyncPayload::execute
            );

    }

}
