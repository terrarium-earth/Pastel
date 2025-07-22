package earth.terrarium.pastel.entity.render;

import com.cmdpro.databank.misc.TrailRender;
import com.cmdpro.databank.rendering.RenderHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.entity.entity.BidentBaseEntity;
import earth.terrarium.pastel.entity.entity.BidentMirrorImageEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class BidentEntityRenderer extends EntityRenderer<BidentBaseEntity> {

    private final ItemRenderer itemRenderer;
    private final float scale;
    private final float offset;

    public BidentEntityRenderer(EntityRendererProvider.Context context) {
        this(context, 2F, -0.625F);
    }

    public BidentEntityRenderer(EntityRendererProvider.Context context, float scale, float offset) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.scale = scale;
        this.offset = offset;
    }

    @Override
    public boolean shouldRender(BidentBaseEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(
        BidentBaseEntity bidentBaseEntity, float yaw, float tickDelta, PoseStack poseStack,
        MultiBufferSource vertexConsumerProvider, int light
    ) {
        TrailRender trail = bidentBaseEntity.getTrail();
        if (trail != null) {
            poseStack.pushPose();
            double d0 = Mth.lerp(tickDelta, bidentBaseEntity.xOld, bidentBaseEntity.getX());
            double d1 = Mth.lerp(tickDelta, bidentBaseEntity.yOld, bidentBaseEntity.getY());
            double d2 = Mth.lerp(tickDelta, bidentBaseEntity.zOld, bidentBaseEntity.getZ());
            Vec3 posOffset = new Vec3(d0, d1, d2).subtract(bidentBaseEntity.position());
            Vec3 pos = bidentBaseEntity.position()
                                       .add(posOffset);
            poseStack.translate(-pos.x, -pos.y, -pos.z);
            Quaternionf offsetRot = new Quaternionf()
                .rotateY((float) Math.toRadians(
                    Mth.lerp(tickDelta, bidentBaseEntity.yRotO, bidentBaseEntity.getYRot()) - 90.0F))
                .rotateZ((float) Math.toRadians(
                    -135 + Mth.lerp(tickDelta, bidentBaseEntity.xRotO, bidentBaseEntity.getXRot()) + 90.0F));
            Vector3f offset = new Vector3f(0, this.offset, 0).rotate(offsetRot);
            trail.position = bidentBaseEntity.getBoundingBox()
                                             .getCenter()
                                             .add(posOffset)
                                             .add(offset.x, offset.y, offset.z);
            trail.render(
                poseStack, RenderHandler.createBufferSource(), LightTexture.FULL_BRIGHT,
                bidentBaseEntity.getGradient()
            );
            poseStack.popPose();
        }

        ItemStack itemStack = bidentBaseEntity.getTrackedStack();
        renderAsItemStack(bidentBaseEntity, tickDelta, poseStack, vertexConsumerProvider, light, itemStack);
        super.render(bidentBaseEntity, yaw, tickDelta, poseStack, vertexConsumerProvider, light);
    }

    private void renderAsItemStack(
        BidentBaseEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider,
        int light, ItemStack itemStack
    ) {
        BakedModel bakedModel = this.itemRenderer.getModel(
            itemStack, entity.level(), null, entity instanceof BidentMirrorImageEntity ? 80085 : 817210941);

        poseStack.pushPose();
        poseStack.translate(
            0, entity.makeBoundingBox()
                     .getSize() / 2, 0
        );
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(tickDelta, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-135 + Mth.lerp(tickDelta, entity.xRotO, entity.getXRot()) + 90.0F));
        poseStack.translate(0, offset, 0);

        poseStack.scale(scale, scale, scale);

        this.itemRenderer.render(
            itemStack, ItemDisplayContext.NONE, false, poseStack, vertexConsumerProvider,
            entity instanceof BidentMirrorImageEntity ? LightTexture.FULL_BRIGHT : light, OverlayTexture.NO_OVERLAY,
            bakedModel
        );

        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(BidentBaseEntity entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }

}
