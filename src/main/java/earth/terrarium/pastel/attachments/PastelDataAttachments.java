package earth.terrarium.pastel.attachments;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.*;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PastelDataAttachments {


    private static final DeferredRegister<AttachmentType<?>> REGISTER = DeferredRegister.create(
        NeoForgeRegistries.Keys.ATTACHMENT_TYPES, PastelCommon.MOD_ID);

    public static void register(IEventBus bus) {
        REGISTER.register("primfire", () -> PrimordialFireData.ATTACHMENT);
        REGISTER.register("dike", () -> AzureDikeData.ATTACHMENT);
        REGISTER.register("ribbon", () -> EverpromiseRibbonData.ATTACHMENT);
        REGISTER.register("last_kill", () -> LastKillData.ATTACHMENT);
        REGISTER.register("player_misc", () -> MiscPlayerData.ATTACHMENT);
        REGISTER.register("inertia", () -> InertiaData.ATTACHMENT);
        REGISTER.register("spectacle", () -> SpectacleData.ATTACHMENT);
        REGISTER.register("hookshot", () -> HookshotData.ATTACHMENT);
        REGISTER.register("citrine_jumps",()->CitrineJumpsAttachment.ATTACHMENT);
        REGISTER.register("jump_cooldown",()->JumpCooldownAttachment.ATTACHMENT);
        REGISTER.register(bus);
    }

}
