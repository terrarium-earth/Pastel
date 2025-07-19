package earth.terrarium.pastel.blocks.mob_head.client.models;

import earth.terrarium.pastel.blocks.mob_head.client.PastelSkullModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@OnlyIn(Dist.CLIENT)
public class FrogHeadModel extends PastelSkullModel {
	
	public FrogHeadModel(ModelPart root) {
		super(root);
	}
	
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		
		PartDefinition head = modelPartData.addOrReplaceChild(
				PartNames.HEAD,
				CubeListBuilder.create()
						.texOffs(3, 1).addBox(-3.5F, -3.0F, -4.5F, 7.0F, 3.0F, 9.0F)
						.texOffs(23, 22).addBox(-3.5F, -2.0F, -4.5F, 7.0F, 0.0F, 9.0F),
				PartPose.ZERO
		);
		
		PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create()
						.texOffs(23, 13).addBox(-3.5F, -1.0F, -7.0F, 7.0F, 0.0F, 9.0F)
						.texOffs(0, 13).addBox(-3.5F, -2.0F, -7.0F, 7.0F, 3.0F, 9.0F),
				PartPose.offset(0.0F, -3.0F, 2.5F));
		head.addOrReplaceChild(PartNames.TONGUE, CubeListBuilder.create()
						.texOffs(17, 13).addBox(-2.0F, 0.0F, -7.1F, 4.0F, 0.0F, 7.0F),
				PartPose.offset(0.0F, -2.01F, 4.5F));
		face.addOrReplaceChild(PartNames.RIGHT_EYE, CubeListBuilder.create()
						.texOffs(0, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F),
				PartPose.offset(-2.0F, -3.0F, -4.5F));
		face.addOrReplaceChild(PartNames.LEFT_EYE, CubeListBuilder.create()
						.texOffs(0, 5).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F),
				PartPose.offset(2.0F, -3.0F, -4.5F));
		head.addOrReplaceChild(PartNames.CROAKING_BODY, CubeListBuilder.create()
						.texOffs(26, 5).addBox(-3.5F, -0.1F, -2.9F, 7.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)),
				PartPose.offset(0.0F, -3.0F, -1.5F));
		
		return LayerDefinition.create(modelData, 48, 48);
	}
	
}
