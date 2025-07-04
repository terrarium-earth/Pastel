package earth.terrarium.pastel.blocks.decoration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.helpers.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ProjectorBlockEntityRenderer implements BlockEntityRenderer<ProjectorBlockEntity> {

	protected static EntityRenderDispatcher dispatcher;

	@SuppressWarnings("unused")
    public ProjectorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
	}

	@Override
	public void render(ProjectorBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		float time = entity.getLevel().getGameTime() % 24000 + tickDelta;
		double bob = Math.sin((time / 19)) * 0.075 * entity.bobMultiplier;

		matrices.pushPose();
		matrices.translate(0.5D, entity.heightOffset + bob, 0.5D);
		var center = Vec3.atLowerCornerOf(entity.getBlockPos()).add(0.5, 0, 0.5);
		var xOffset = center.x() - dispatcher.camera.getPosition().x;
		var zOffset = center.z() - dispatcher.camera.getPosition().z;
		matrices.mulPose(Axis.YP.rotation((float) Mth.atan2(xOffset, zOffset)));
		var buffer = vertexConsumers.getBuffer(RenderType.entityTranslucent(entity.texture));
		RenderHelper.renderFlatTrans(matrices, buffer, false, entity.scaling, 0.75F, 0F, overlay);
		matrices.popPose();
	}
}
