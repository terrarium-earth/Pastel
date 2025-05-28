package earth.terrarium.pastel.blocks.bottomless_bundle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BottomlessBundleBlockEntityRenderer implements BlockEntityRenderer<BottomlessBundleBlockEntity> {
	
	private static final Material SPRITE = new Material(InventoryMenu.BLOCK_ATLAS, SpectrumCommon.locate("block/bottomless_bundle"));
	private final ModelPart root, locked, unlocked;
	
	
	public BottomlessBundleBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		root = getTexturedModelData().bakeRoot();
		this.locked = root.getChild("locked");
		this.unlocked = root.getChild("unlocked");
	}
	
	@Override
	public void render(BottomlessBundleBlockEntity bundle, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
		matrices.pushPose();
		
		boolean bl = bundle.getLevel() != null;
		BlockState blockState = bl ? bundle.getBlockState() : SpectrumBlocks.BOTTOMLESS_BUNDLE.get().defaultBlockState();
		var yaw = 22.5F;
		yaw *= blockState.getValue(SkullBlock.ROTATION);
		matrices.translate(0.5D, 1.5D, 0.5D);
		matrices.mulPose(Axis.YP.rotationDegrees(-yaw + 180));
		matrices.mulPose(Axis.XP.rotationDegrees(180));
		
		var vertices = SPRITE.buffer(vertexConsumers, RenderType::entityCutout);
		root.render(matrices, vertices, light, overlay);
		if (blockState.getValue(BottomlessBundleBlock.LOCKED)) {
			locked.render(matrices, vertices, light, overlay);
		}
		else {
			unlocked.render(matrices, vertices, light, overlay);
		}
		
		matrices.popPose();
	}
	
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition main = modelPartData.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 19.0F, 0.0F));
		
		PartDefinition foot_r1 = main.addOrReplaceChild("foot_r1", CubeListBuilder.create().texOffs(13, 13).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		
		PartDefinition pyramid = main.addOrReplaceChild("pyramid", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.0F, 5.0F));
		
		PartDefinition side_r1 = pyramid.addOrReplaceChild("side_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.1358F, -0.0429F, -2.8923F, 1.9221F, 0.6819F, 2.5046F));
		
		PartDefinition side_r2 = pyramid.addOrReplaceChild("side_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0828F, -0.0429F, -7.188F, 2.7873F, -0.674F, 2.5093F));
		
		PartDefinition side_r3 = pyramid.addOrReplaceChild("side_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9172F, -0.0429F, -2.812F, -0.3543F, 0.674F, 0.6323F));
		
		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -16.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 9.5F, -0.5F));
		
		PartDefinition knot_r1 = head.addOrReplaceChild("knot_r1", CubeListBuilder.create().texOffs(9, 5).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.0F, 0.5F, 0.0873F, 0.0F, -0.0873F));
		
		PartDefinition cord_r1 = head.addOrReplaceChild("cord_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.25F, 0.05F, -2.825F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -14.5F, 0.25F, 0.6658F, -0.1245F, -0.8232F));
		
		PartDefinition top_r1 = head.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.75F, -16.5F, 0.25F, 0.2618F, 0.7854F, 0.0F));
		
		PartDefinition locked = modelPartData.addOrReplaceChild("locked", CubeListBuilder.create(), PartPose.offset(-2.1358F, 18.9571F, -2.1077F));
		
		PartDefinition s_locked_r1 = locked.addOrReplaceChild("s_locked_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2195F, -0.6819F, 0.637F));
		
		PartDefinition unlocked = modelPartData.addOrReplaceChild("unlocked", CubeListBuilder.create(), PartPose.offset(-2.1358F, 18.9571F, -2.1077F));
		
		PartDefinition s_unlocked_r1 = unlocked.addOrReplaceChild("s_unlocked_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-0.4423F, -4.7187F, -4.716F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2195F, -0.6819F, 0.637F));
		return LayerDefinition.create(modelData, 64, 64);
	}
}
