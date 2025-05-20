package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.networking.c2s_payloads.AddLoreBedrockAnvilPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.BindEnderSpliceToPlayerPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.ChangeCompactingChestSettingsPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.GuidebookConfirmationButtonPressedPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.GuidebookHintBoughtPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.InkColorSelectedC2SPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.ParticleSpawnerConfigurationC2SPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.RenameItemInBedrockAnvilPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.SetShadowSlotPayload;
import de.dafuqs.spectrum.networking.c2s_payloads.WorkstaffToggleSelectedPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.network.event.*;
import net.neoforged.neoforge.network.registration.*;

public class SpectrumC2SPackets {

	public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> makeId(String id) {
		return new CustomPacketPayload.Type<>(SpectrumCommon.locate(id));
	}

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		
		registrar.playToServer(AddLoreBedrockAnvilPayload.ID, AddLoreBedrockAnvilPayload.CODEC, AddLoreBedrockAnvilPayload.getPayloadHandler());
		registrar.playToServer(AddLoreBedrockAnvilPayload.ID, AddLoreBedrockAnvilPayload.CODEC, AddLoreBedrockAnvilPayload.getPayloadHandler());
		registrar.playToServer(BindEnderSpliceToPlayerPayload.ID, BindEnderSpliceToPlayerPayload.CODEC, BindEnderSpliceToPlayerPayload.getPayloadHandler());
		registrar.playToServer(ChangeCompactingChestSettingsPayload.ID, ChangeCompactingChestSettingsPayload.CODEC, ChangeCompactingChestSettingsPayload.getPayloadHandler());
		registrar.playToServer(GuidebookConfirmationButtonPressedPayload.ID, GuidebookConfirmationButtonPressedPayload.CODEC, GuidebookConfirmationButtonPressedPayload.getPayloadHandler());
		registrar.playToServer(GuidebookHintBoughtPayload.ID, GuidebookHintBoughtPayload.CODEC, GuidebookHintBoughtPayload.getPayloadHandler());
		registrar.playToServer(InkColorSelectedC2SPayload.ID, InkColorSelectedC2SPayload.CODEC, InkColorSelectedC2SPayload.getPayloadHandler());
		registrar.playToServer(ParticleSpawnerConfigurationC2SPayload.ID, ParticleSpawnerConfigurationC2SPayload.CODEC, ParticleSpawnerConfigurationC2SPayload.getPayloadHandler());
		registrar.playToServer(RenameItemInBedrockAnvilPayload.ID, RenameItemInBedrockAnvilPayload.CODEC, RenameItemInBedrockAnvilPayload.getPayloadHandler());
		registrar.playToServer(SetShadowSlotPayload.ID, SetShadowSlotPayload.CODEC, SetShadowSlotPayload.getPayloadHandler());
		registrar.playToServer(WorkstaffToggleSelectedPayload.ID, WorkstaffToggleSelectedPayload.CODEC, WorkstaffToggleSelectedPayload.getPayloadHandler());
	}
	
}
