package de.dafuqs.spectrum.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.dafuqs.spectrum.entity.entity.GlassArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GlassArrowEntityRenderer extends EntityRenderer<GlassArrowEntity> {

    private final ItemRenderer itemRenderer;

    public GlassArrowEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }
    
    @Override
    public void render(GlassArrowEntity persistentProjectileEntity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light) {
        ItemStack itemStack = persistentProjectileEntity.getVariant().getArrow().getDefaultInstance();
        renderAsItemStack(persistentProjectileEntity, tickDelta, poseStack, vertexConsumerProvider, light, itemStack);
        super.render(persistentProjectileEntity, yaw, tickDelta, poseStack, vertexConsumerProvider, light);
    }

    private void renderAsItemStack(AbstractArrow entity, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light, ItemStack itemStack) {
        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.level(), null, entity.getId());

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(tickDelta, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(getAdditionalPitch() + Mth.lerp(tickDelta, entity.xRotO, entity.getXRot())));

        ItemTransform transformation = bakedModel.getTransforms().getTransform(ItemDisplayContext.GROUND);
        float scaleX = transformation.scale.x();
        float scaleY = transformation.scale.y();
        float scaleZ = transformation.scale.z();

        poseStack.translate(0.0, (0.25F * scaleY), 0.0);

        float scale = getScale();
        poseStack.scale(scale, scale, scale);

        if (!bakedModel.isGui3d()) {
            float r = -0.0F * (float) (0) * 0.5F * scaleX;
            float s = -0.0F * (float) (0) * 0.5F * scaleY;
            float t = -0.09375F * (float) (0) * 0.5F * scaleZ;
            poseStack.translate(r, s, t);
        }
        float shake = (float) entity.shakeTime - tickDelta;
        if (shake > 0.0F) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(-Mth.sin(shake * 3.0F) * shake));
        }
    
        this.itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, vertexConsumerProvider, light, OverlayTexture.NO_OVERLAY, bakedModel);
    
        poseStack.popPose();
    }
    
    public float getScale() {
        return 1.5F;
    }
    
    public int getAdditionalPitch() {
        return -45;
    }
	
	@Override
    public ResourceLocation getTextureLocation(GlassArrowEntity itemEntity) {
		return InventoryMenu.BLOCK_ATLAS;
    }
    
}
