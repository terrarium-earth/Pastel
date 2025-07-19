package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.entity.entity.LightMineEntity;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class LightMineEntityRenderer extends EntityRenderer<LightMineEntity> {

    public LightMineEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }
    
    @Override
    public void render(LightMineEntity mine, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.pushPose();
	
		var age = mine.tickCount;
        var alpha = Mth.clamp(1 - Mth.lerp(tickDelta, mine.getVanishingProgress(age - 1), mine.getVanishingProgress(age)), 0F, 1F);
        var scaleFactor = Mth.sin((age + tickDelta) / 8F) / 6F + mine.getScaleOffset();
	
		matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrices.mulPose(Axis.YP.rotationDegrees(180f));
		matrices.scale(scaleFactor, scaleFactor, 1);
		matrices.translate(-0.5F, -0.5F, 0);
	
		var consumer = vertexConsumers.getBuffer(RenderType.entityTranslucentCull(getTextureLocation(mine)));
		var matrix = matrices.last();
		var positions = matrix.pose();
		
		Vector3f color = ColorHelper.colorIntToVec(mine.getColor());
		consumer.addVertex(positions, 0, 0, 0).setColor(color.x(), color.y(), color.z(), alpha).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(matrix, 0, 1, 0);
		consumer.addVertex(positions, 1, 0, 0).setColor(color.x(), color.y(), color.z(), alpha).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(matrix, 0, 1, 0);
		consumer.addVertex(positions, 1, 1, 0).setColor(color.x(), color.y(), color.z(), alpha).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(matrix, 0, 1, 0);
		consumer.addVertex(positions, 0, 1, 0).setColor(color.x(), color.y(), color.z(), alpha).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(LightTexture.FULL_BRIGHT).setNormal(matrix, 0, 1, 0);
	
		matrices.popPose();
	
		super.render(mine, yaw, tickDelta, matrices, vertexConsumers, light);
	}
    
    @Override
    public ResourceLocation getTextureLocation(LightMineEntity entity) {
        return entity.getTextureLocation();
    }
    
}
