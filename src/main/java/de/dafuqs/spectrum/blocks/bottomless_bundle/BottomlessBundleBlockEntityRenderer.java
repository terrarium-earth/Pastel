package de.dafuqs.spectrum.blocks.bottomless_bundle;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.util.*;
import net.minecraft.client.util.math.*;
import net.minecraft.screen.*;
import net.minecraft.state.property.*;
import net.minecraft.util.math.*;

public class BottomlessBundleBlockEntityRenderer implements BlockEntityRenderer<BottomlessBundleBlockEntity> {
	
	private static final SpriteIdentifier SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, SpectrumCommon.locate("block/bottomless_bundle"));
	private final ModelPart root, locked, unlocked;
	
	
	public BottomlessBundleBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		root = getTexturedModelData().createModel();
		this.locked = root.getChild("locked");
		this.unlocked = root.getChild("unlocked");
	}
	
	@Override
	public void render(BottomlessBundleBlockEntity bundle, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		
		boolean bl = bundle.getWorld() != null;
		BlockState blockState = bl ? bundle.getCachedState() : SpectrumBlocks.BOTTOMLESS_BUNDLE.getDefaultState();
		var yaw = 22.5F;
		yaw *= blockState.get(SkullBlock.ROTATION);
		matrices.translate(0.5D, 1.5D, 0.5D);
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yaw + 180));
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
		
		var vertices = SPRITE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
		root.render(matrices, vertices, light, overlay);
		if (blockState.get(BottomlessBundleBlock.LOCKED)) {
			locked.render(matrices, vertices, light, overlay);
		}
		else {
			unlocked.render(matrices, vertices, light, overlay);
		}
		
		matrices.pop();
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));
		
		ModelPartData foot_r1 = main.addChild("foot_r1", ModelPartBuilder.create().uv(13, 13).cuboid(-3.5F, 0.0F, -3.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		
		ModelPartData pyramid = main.addChild("pyramid", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, 0.0F, 5.0F));
		
		ModelPartData side_r1 = pyramid.addChild("side_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(7.1358F, -0.0429F, -2.8923F, 1.9221F, 0.6819F, 2.5046F));
		
		ModelPartData side_r2 = pyramid.addChild("side_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(7.0828F, -0.0429F, -7.188F, 2.7873F, -0.674F, 2.5093F));
		
		ModelPartData side_r3 = pyramid.addChild("side_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(2.9172F, -0.0429F, -2.812F, -0.3543F, 0.674F, 0.6323F));
		
		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -16.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, 9.5F, -0.5F));
		
		ModelPartData knot_r1 = head.addChild("knot_r1", ModelPartBuilder.create().uv(9, 5).cuboid(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -15.0F, 0.5F, 0.0873F, 0.0F, -0.0873F));
		
		ModelPartData cord_r1 = head.addChild("cord_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-4.25F, 0.05F, -2.825F, 5.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -14.5F, 0.25F, 0.6658F, -0.1245F, -0.8232F));
		
		ModelPartData top_r1 = head.addChild("top_r1", ModelPartBuilder.create().uv(0, 4).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.75F, -16.5F, 0.25F, 0.2618F, 0.7854F, 0.0F));
		
		ModelPartData locked = modelPartData.addChild("locked", ModelPartBuilder.create(), ModelTransform.pivot(-2.1358F, 18.9571F, -2.1077F));
		
		ModelPartData s_locked_r1 = locked.addChild("s_locked_r1", ModelPartBuilder.create().uv(0, 25).cuboid(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.2195F, -0.6819F, 0.637F));
		
		ModelPartData unlocked = modelPartData.addChild("unlocked", ModelPartBuilder.create(), ModelTransform.pivot(-2.1358F, 18.9571F, -2.1077F));
		
		ModelPartData s_unlocked_r1 = unlocked.addChild("s_unlocked_r1", ModelPartBuilder.create().uv(0, 15).cuboid(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.2195F, -0.6819F, 0.637F));
		return TexturedModelData.of(modelData, 64, 64);
	}
}
