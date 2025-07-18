package earth.terrarium.pastel.blocks.structure;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;

public class PreservationControllerBlockEntityRenderer
    implements BlockEntityRenderer<PreservationControllerBlockEntity> {

    private static final ResourceLocation AETHER_CORE = PastelCommon.locate(
        "textures/block/preservation_controller_aether.png");
    protected static EntityRenderDispatcher dispatcher;

    public PreservationControllerBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        dispatcher = Minecraft.getInstance()
                              .getEntityRenderDispatcher();
    }

    @Override
    public void render(
        PreservationControllerBlockEntity entity, float tickDelta, PoseStack matrices,
        MultiBufferSource vertexConsumers, int light, int overlay
    ) {
        matrices.pushPose();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.mulPose(dispatcher.camera.rotation());
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F));
        var buffer = vertexConsumers.getBuffer(RenderType.entityTranslucent(AETHER_CORE));

        float time = entity.getLevel()
                           .getGameTime() % 24000 + tickDelta;

        matrices.mulPose(Axis.ZP.rotationDegrees(time / 1.5F));
        float pulse = (float) (Math.sin((time / 19)));
        float scale = pulse * 0.2F + 0.8F;
        matrices.scale(scale, scale, scale);
        RenderHelper.renderFlatTrans(matrices, buffer, false, 15F, pulse * 0.25F + 0.75F, 0F, overlay);
        matrices.popPose();
    }
}
