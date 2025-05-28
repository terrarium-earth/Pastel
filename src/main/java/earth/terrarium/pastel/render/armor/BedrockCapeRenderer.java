package earth.terrarium.pastel.render.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import earth.terrarium.pastel.items.armor.BedrockArmorItem;
import earth.terrarium.pastel.registries.client.*;
import earth.terrarium.pastel.render.RenderingContext;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.client.event.*;

public class BedrockCapeRenderer {

	//TODO: does this really need an event?
	public static void register(RenderLivingEvent.Post<LivingEntity, HumanoidModel<LivingEntity>> event) {
		renderBedrockCapeAndCloth(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPartialTick(), event.getEntity());
	}

	/**
	 * Renders the bedrock cloth and cape on the player
	 */
	public static void renderBedrockCapeAndCloth(PoseStack ms, MultiBufferSource vertices, int light, float h, LivingEntity entity) {

		// Transform and render front cloth
        assert entity != null;
        var capeRotations = BedrockArmorModel.computeFrontClothRotation(entity, h);
		float capeZOffset = capeRotations.getB();

		VertexConsumer vertexConsumer = vertices.getBuffer(RenderType.entitySolid(SpectrumModelLayers.BEDROCK_ARMOR_MAIN_ID));
		ms.pushPose();
		ms.translate(0, 0.5, 0);
		ms.mulPose(Axis.XP.rotationDegrees(Mth.clamp(capeRotations.getA(), -25, 0)));
		if (!entity.isCrouching()) {
			ms.mulPose(Axis.ZP.rotationDegrees(capeZOffset / 2.0F));
		}

		// Make some space for your legs if crouching
		ms.translate(0, -0.5, -0.025);
		if (entity.isCrouching()) {
			ms.translate(0, 0.05, 0.35);
		}
		BedrockArmorCapeModel.FRONT_CLOTH.render(ms, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
		ms.popPose();

		// TODO - Respect player capes once armor tailoring is implemented
		// Respect Elytras and Fabrics Render Event

		if (RenderingContext.isElytraRendered) {
			return;
		}

		// The front and back cape are almost matching, but inverted
		float backCapeRotation = Mth.clamp(-capeRotations.getA(), -30, 45);

		// Transform and render the custom cape
		ms.pushPose();
		ms.translate(0, -0.05, 0.0); // Push up and backwards, then rotate
		ms.mulPose(Axis.XP.rotationDegrees(backCapeRotation));
		ms.mulPose(Axis.ZP.rotationDegrees(capeZOffset / 2.0F));
		ms.mulPose(Axis.YP.rotationDegrees(180.0F - capeZOffset / 3.5F));
		ms.translate(0, 0.05, -0.325); // Move back down
		if (entity.isCrouching()) {
			ms.translate(0, 0.15, 0.125);
		}

		BedrockArmorCapeModel.CAPE_MODEL.render(ms, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
		ms.popPose();
	}
	
}
