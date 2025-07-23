package earth.terrarium.pastel.entity.render;

import com.cmdpro.databank.rendering.RenderHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.GlassArrowEntity;
import earth.terrarium.pastel.items.tools.GlassArrowVariant;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlassArrowEntityRenderer extends EntityRenderer<GlassArrowEntity> {

    private final ResourceLocation TEXTURE = PastelCommon.locate("textures/entity/projectile/arrow/white_glass_arrow.png");
    private final Model model;

    public GlassArrowEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new Model(context.bakeLayer(PastelModelLayers.GLASS_ARROW));
    }

    @Override
    public void render(
        GlassArrowEntity arrow, float yaw, float tickDelta, PoseStack poseStack,
        MultiBufferSource buffers, int light
    ) {
        renderType(arrow, tickDelta, poseStack, buffers);
        super.render(arrow, yaw, tickDelta, poseStack, buffers, light);
    }

    private void renderType(
        GlassArrowEntity arrow, float tickDelta, PoseStack poseStack, MultiBufferSource consumers
    ) {

        var trail = arrow.getTrail();
        if (trail != null) {
            poseStack.pushPose();
            double d0 = Mth.lerp(tickDelta, arrow.xOld, arrow.getX());
            double d1 = Mth.lerp(tickDelta, arrow.yOld, arrow.getY());
            double d2 = Mth.lerp(tickDelta, arrow.zOld, arrow.getZ());
            Vec3 posOffset = new Vec3(d0, d1, d2).subtract(arrow.position());
            Vec3 pos = arrow.position()
                                       .add(posOffset);
            poseStack.translate(-pos.x, -pos.y, -pos.z);

            trail.position = arrow.getBoundingBox()
                                             .getCenter()
                                             .add(posOffset);
            trail.render(
                poseStack, RenderHandler.createBufferSource(), LightTexture.FULL_BRIGHT,
                arrow.getGradient()
            );

            poseStack.popPose();
        }

        poseStack.pushPose();
        poseStack.translate(0, -1.25F, 0);

        var buffer = consumers.getBuffer(RenderType.entityCutout(getTextureLocation(arrow)));
        model.main.setRotation(
            (float) Math.toRadians(Mth.lerp(tickDelta, arrow.xRotO, arrow.getXRot()) + 90F),
            (float) Math.toRadians(Mth.lerp(tickDelta, arrow.yRotO, arrow.getYRot()) - 180),
            0
        );

        var alpha = 1 - Math.min(arrow.getInGroundTime() / 20F, 1);
        model.renderToBuffer(poseStack, buffer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(alpha, 1, 1, 1));
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(GlassArrowEntity arrow) {
        return arrow.getVariant().getTexture();
    }

    public static class Model extends EntityModel<GlassArrowEntity> {
        private final ModelPart main;

        public Model(ModelPart root) {
            this.main = root.getChild("bone");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.5F, 24.0F, 2.0F, 1.5708F, 0.0F, -1.5708F));

            PartDefinition face_r1 = bone.addOrReplaceChild("face_r1", CubeListBuilder.create().texOffs(1, 0).addBox(-2.5F, -12.0F, 0.0F, 5.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

            PartDefinition face_r2 = bone.addOrReplaceChild("face_r2", CubeListBuilder.create().texOffs(1, 0).addBox(-2.5F, -12.0F, 0.0F, 5.0F, 24.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

            return LayerDefinition.create(meshdefinition, 32, 32);
        }

        @Override
        public void setupAnim(GlassArrowEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

        @Override
        public void renderToBuffer(
            PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
            main.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
    }
}
