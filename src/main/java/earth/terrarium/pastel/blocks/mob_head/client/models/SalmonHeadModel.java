package earth.terrarium.pastel.blocks.mob_head.client.models;

import earth.terrarium.pastel.blocks.mob_head.client.SpectrumSkullModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@OnlyIn(Dist.CLIENT)
public class SalmonHeadModel extends SpectrumSkullModel {
	
	public SalmonHeadModel(ModelPart root) {
		super(root);
	}
	
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		
		PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -5.0F, -4.0F, 3.0F, 5.0F, 8.0F), PartPose.ZERO);
		head.addOrReplaceChild("face", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -4.0F, -7.0F, 2.0F, 4.0F, 3.0F), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.TOP_FIN, CubeListBuilder.create().texOffs(2, 1).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F), PartPose.offset(0.0F, -7.0F, 1.0F));
		head.addOrReplaceChild(PartNames.BACK_FIN, CubeListBuilder.create().texOffs(0, 2).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 4.0F), PartPose.offset(0.0F, -7.0F, 3.0F));
		head.addOrReplaceChild(PartNames.RIGHT_FIN, CubeListBuilder.create().texOffs(-4, 0).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), PartPose.offsetAndRotation(-1.5F, -1.0F, -4.0F, 0.0F, 0.0F, -(float) Math.PI / 4.0F));
		head.addOrReplaceChild(PartNames.LEFT_FIN, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F), PartPose.offsetAndRotation(1.5F, -1.0F, -4.0F, 0.0F, 0.0F, (float) Math.PI / 4.0F));
		
		return LayerDefinition.create(modelData, 32, 32);
	}
	
}
