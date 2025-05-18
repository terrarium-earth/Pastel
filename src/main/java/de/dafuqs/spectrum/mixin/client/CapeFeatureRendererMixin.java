package de.dafuqs.spectrum.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.dafuqs.spectrum.compat.vanityslots.VanitySlotsCompat;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.render.armor.BedrockCapeRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(CapeLayer.class)
public abstract class CapeFeatureRendererMixin extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

	public CapeFeatureRendererMixin(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> ctx) {
		super(ctx);
	}

    /**
     * Renders a custom flap on the front of the Bedrock Armor, as well as a custom cape render
     */
	@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V", at = @At("HEAD"), cancellable = true)
	public void spectrum$renderBedrockCape(PoseStack ms, MultiBufferSource vertices, int light, AbstractClientPlayer player, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		// Check for the chestplate, and begin rendering the cape if equipped
		ItemStack chestStack = VanitySlotsCompat.getEquippedStack(player, EquipmentSlot.CHEST);
		if (chestStack.getItem() == SpectrumItems.BEDROCK_CHESTPLATE) {
			BedrockCapeRenderer.renderBedrockCapeAndCloth(ms, vertices, light, player, h, chestStack);
			// TODO - Cancel for now, as the new armor tailoring system is not implemented yet
			ci.cancel();
		}	
	}
	
}
