package earth.terrarium.pastel.attachments;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.CitrineJumpsAttachment;
import earth.terrarium.pastel.attachments.data.ConsumptionRingData;
import earth.terrarium.pastel.attachments.data.EverpromiseRibbonData;
import earth.terrarium.pastel.attachments.data.HookshotData;
import earth.terrarium.pastel.attachments.data.InertiaData;
import earth.terrarium.pastel.attachments.data.JeopardantBonusData;
import earth.terrarium.pastel.attachments.data.JumpCooldownAttachment;
import earth.terrarium.pastel.attachments.data.LastKillData;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.attachments.data.SpectacleData;
import earth.terrarium.pastel.attachments.data.WhipFollowupStrikesAttachment;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PastelDataAttachments {

    private static final DeferredRegister<AttachmentType<?>> REGISTER = DeferredRegister
        .create(
            NeoForgeRegistries.Keys.ATTACHMENT_TYPES,
            PastelCommon.MOD_ID
        );

    public static void register(IEventBus bus) {
        REGISTER.register("primfire", () -> PrimordialFireData.ATTACHMENT);
        REGISTER.register("dike", () -> AzureDikeData.ATTACHMENT);
        REGISTER.register("ribbon", () -> EverpromiseRibbonData.ATTACHMENT);
        REGISTER.register("last_kill", () -> LastKillData.ATTACHMENT);
        REGISTER.register("player_misc", () -> MiscPlayerData.ATTACHMENT);
        REGISTER.register("inertia", () -> InertiaData.ATTACHMENT);
        REGISTER.register("spectacle", () -> SpectacleData.ATTACHMENT);
        REGISTER.register("hookshot", () -> HookshotData.ATTACHMENT);
        REGISTER.register("jeopardant", () -> JeopardantBonusData.ATTACHMENT);
        REGISTER.register("citrine_jumps", () -> CitrineJumpsAttachment.ATTACHMENT);
        REGISTER.register("jump_cooldown", () -> JumpCooldownAttachment.ATTACHMENT);
        REGISTER.register("ring_of_consumption_equipped", () -> ConsumptionRingData.ATTACHMENT);
        REGISTER.register("whip_victim", () -> WhipFollowupStrikesAttachment.ATTACHMENT);
        REGISTER.register(bus);
    }

}
