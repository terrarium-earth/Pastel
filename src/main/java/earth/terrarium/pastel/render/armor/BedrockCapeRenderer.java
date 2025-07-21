package earth.terrarium.pastel.render.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import earth.terrarium.pastel.compat.vanityslots.VanitySlotsCompat;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import earth.terrarium.pastel.render.RenderingContext;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class BedrockCapeRenderer {

    private static <T extends Entity> void registerCapeLayer(EntityRenderer<T> baseRenderer) {
        if (!(baseRenderer instanceof LivingEntityRenderer<?, ?> livingRenderer)) {
            return;
        }

        if (!(livingRenderer.getModel() instanceof HumanoidModel<?>)) {
            return;
        }

        @SuppressWarnings("unchecked")
        var humanoidRenderer = (LivingEntityRenderer<LivingEntity, HumanoidModel<LivingEntity>>) livingRenderer;

        humanoidRenderer.addLayer(new BedrockCapeLayer<>(humanoidRenderer));
    }

    /**
     * Registers the bedrock cloth and cape layers on humanoid entities
     */
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(skin);

            registerCapeLayer(renderer);
        }

        for (EntityType<?> entityType : event.getEntityTypes()) {
            EntityRenderer<?> renderer = event.getRenderer(entityType);

            registerCapeLayer(renderer);
        }
    }

    private static class BedrockCapeLayer<T extends LivingEntity, M extends HumanoidModel<T>>
        extends RenderLayer<T, M> {

        public BedrockCapeLayer(RenderLayerParent<T, M> renderer) {
            super(renderer);
        }

        @Override
        public void render(
            PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, LivingEntity livingEntity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw,
            float headPitch
        ) {
            // Check for the chestplate, and begin rendering the cape if equipped
            ItemStack chestStack = VanitySlotsCompat.getEquippedStack(livingEntity, EquipmentSlot.CHEST);
            if (chestStack.getItem() != PastelItems.BEDROCK_CHESTPLATE.get()) {
                return;
            }

            // Transform and render front cloth
            var capeRotations = BedrockArmorModel.computeFrontClothRotation(livingEntity, partialTick);
            float capeZOffset = capeRotations.getB();

            VertexConsumer vertexConsumer = bufferSource.getBuffer(
                RenderType.entitySolid(PastelModelLayers.BEDROCK_ARMOR_MAIN_ID));
            poseStack.pushPose();
            poseStack.translate(0, 0.5, 0);
            poseStack.mulPose(Axis.XP.rotationDegrees(Mth.clamp(capeRotations.getA(), -25, 0)));
            if (!livingEntity.isCrouching()) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(capeZOffset / 2.0F));
            }

            // Make some space for your legs if crouching
            poseStack.translate(0, -0.5, -0.025);
            if (livingEntity.isCrouching()) {
                poseStack.translate(0, 0.05, 0.35);
            }
            BedrockArmorCapeModel.FRONT_CLOTH.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();

            // TODO - Respect player capes once armor tailoring is implemented
            // Respect Elytras and Fabrics Render Event

            if (RenderingContext.isElytraRendered) {
                return;
            }

            // The front and back cape are almost matching, but inverted
            float backCapeRotation = Mth.clamp(-capeRotations.getA(), -30, 45);

            // Transform and render the custom cape
            poseStack.pushPose();
            poseStack.translate(0, -0.05, 0.0); // Push up and backwards, then rotate
            poseStack.mulPose(Axis.XP.rotationDegrees(backCapeRotation));
            poseStack.mulPose(Axis.ZP.rotationDegrees(capeZOffset / 2.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - capeZOffset / 3.5F));
            poseStack.translate(0, 0.05, -0.325); // Move back down
            if (livingEntity.isCrouching()) {
                poseStack.translate(0, 0.15, 0.125);
            }

            BedrockArmorCapeModel.CAPE_MODEL.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

}
