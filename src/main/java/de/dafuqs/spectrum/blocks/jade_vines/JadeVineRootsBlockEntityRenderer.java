package de.dafuqs.spectrum.blocks.jade_vines;

import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

@OnlyIn(Dist.CLIENT)
public class JadeVineRootsBlockEntityRenderer implements BlockEntityRenderer<JadeVineRootsBlockEntity> {
	
	@SuppressWarnings("unused")
    public JadeVineRootsBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
	}
	
	@Override
    public void render(JadeVineRootsBlockEntity entity, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light, int overlay) {
		Level world = entity.getLevel();
		if (entity.getLevel() != null) {
			BlockState fenceBlockState = entity.getFenceBlockState();
			if (fenceBlockState.getRenderShape() == RenderShape.MODEL && fenceBlockState.getRenderShape() != RenderShape.INVISIBLE) {
				poseStack.pushPose();
				
				BlockRenderDispatcher blockRenderManager = Minecraft.getInstance().getBlockRenderer();
				blockRenderManager.getModelRenderer().tesselateBlock(entity.getLevel(),
						blockRenderManager.getBlockModel(fenceBlockState),
						fenceBlockState,
						entity.getBlockPos(),
						poseStack,
						vertexConsumerProvider.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(fenceBlockState)),
						true,
						world.random,
						fenceBlockState.getSeed(entity.getBlockPos()),
						OverlayTexture.NO_OVERLAY
				);
				
				poseStack.popPose();
			}
		}
	}
	
}
