package de.dafuqs.revelationary.mixin.client;

import de.dafuqs.revelationary.ClientAdvancements;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.multiplayer.ClientAdvancements.class)
public abstract class ClientAdvancementManagerMixin {
	/**
	 * Intercepts advancement packets sent from server to client
	 * When new advancements are added ClientAdvancements is triggered
	 * resulting in updating block visibility in the world (ModelSwapper)
	 *
	 * @param packet The vanilla advancement packet
	 * @param info   Mixin callback info
	 */
	@Inject(method = "update", at = @At("RETURN"))
	public void revelationary$onAdvancementSync(ClientboundUpdateAdvancementsPacket packet, CallbackInfo info) {
		ClientAdvancements.onClientPacket(packet);
	}
}