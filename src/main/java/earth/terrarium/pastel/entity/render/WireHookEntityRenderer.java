package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.WireHookEntity;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

public class WireHookEntityRenderer extends EntityRenderer<WireHookEntity> {

    public WireHookEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(
        WireHookEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
        int packedLight
    ) {
        poseStack.pushPose();
        poseStack.scale(1.5F, 1.5F, 1.5F);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher()
                                   .cameraOrientation());

        Minecraft.getInstance().getItemRenderer().renderStatic(PastelItems.CITRINE_GLASS_ARROW.get().getDefaultInstance(),
                                                         ItemDisplayContext.GROUND,
                                                               LightTexture.FULL_BRIGHT,
                                                         OverlayTexture.NO_OVERLAY,
                                                               poseStack,
                                                               bufferSource,
                                                               entity.level(),
                                                               0
        );

        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(WireHookEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(WireHookEntity entity) {
        return PastelCommon.locate("");
    }
}
