package de.dafuqs.spectrum.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.entity.entity.SeatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SeatEntityRenderer extends EntityRenderer<SeatEntity> {

    private static final ResourceLocation NOOP = SpectrumCommon.locate("noop");

    public SeatEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SeatEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
    }

    @Override
    public ResourceLocation getTextureLocation(SeatEntity entity) {
        return NOOP;
    }
}
