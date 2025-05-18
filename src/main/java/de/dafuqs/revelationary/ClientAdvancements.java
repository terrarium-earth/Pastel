package de.dafuqs.revelationary;

import de.dafuqs.revelationary.api.advancements.ClientAdvancementPacketCallback;
import de.dafuqs.revelationary.mixin.client.AccessorClientAdvancementManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ClientAdvancements {
	protected static boolean receivedFirstAdvancementPacket = false;
	public static List<ClientAdvancementPacketCallback> callbacks = new ArrayList<>();
	
	public static void onClientPacket(@NotNull ClientboundUpdateAdvancementsPacket packet) {
		boolean hadPacketBefore = receivedFirstAdvancementPacket;
		receivedFirstAdvancementPacket = true;
		boolean isReset = packet.shouldReset();
		boolean isFirstPacket = !hadPacketBefore || isReset;
		
		Set<ResourceLocation> doneAdvancements = getDoneAdvancements(packet);
		Set<ResourceLocation> removedAdvancements = packet.getRemoved();
		
		ClientRevelationHolder.processRemovedAdvancements(removedAdvancements);
		ClientRevelationHolder.processNewAdvancements(doneAdvancements, isFirstPacket);
		
		for (ClientAdvancementPacketCallback callback : callbacks) {
			callback.onClientAdvancementPacket(doneAdvancements, removedAdvancements, isFirstPacket);
		}
	}
	
	public static boolean hasDone(ResourceLocation identifier) {
		// If we never received the initial packet: assume false
		if (!receivedFirstAdvancementPacket) {
			return false;
		}
		
		if (identifier != null) {
			ClientPacketListener conn = Minecraft.getInstance().getConnection();
			if (conn != null) {
				net.minecraft.client.multiplayer.ClientAdvancements cm = conn.getAdvancements();
				AdvancementNode adv = cm.getTree().get(identifier);
				if (adv != null) {
					Map<AdvancementHolder, AdvancementProgress> progressMap = ((AccessorClientAdvancementManager) cm).getProgress();
					AdvancementProgress progress = progressMap.get(adv.holder());
					return progress != null && progress.isDone();
				}
			}
		}
		return false;
	}
	
	public static @NotNull Set<ResourceLocation> getDoneAdvancements(@NotNull ClientboundUpdateAdvancementsPacket packet) {
		Set<ResourceLocation> doneAdvancements = new HashSet<>();
		
		for (AdvancementHolder earnedAdvancementEntry : packet.getAdded()) {
			doneAdvancements.add(earnedAdvancementEntry.id());
		}
		for (Map.Entry<ResourceLocation, AdvancementProgress> progressedAdvancement : packet.getProgress().entrySet()) {
			if (progressedAdvancement.getValue().isDone()) {
				doneAdvancements.add(progressedAdvancement.getKey());
			}
		}
		
		return doneAdvancements;
	}
	
	public static void playerLogout() {
		receivedFirstAdvancementPacket = false;
	}
}