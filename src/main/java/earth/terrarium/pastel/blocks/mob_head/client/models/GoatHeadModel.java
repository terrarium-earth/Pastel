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
public class GoatHeadModel extends SpectrumSkullModel {
	
	public GoatHeadModel(ModelPart root) {
		super(root);
	}
	
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		
		PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create()
				.texOffs(12, 55).addBox(0.25F, -14.0F, -1.0F, 2.0F, 7.0F, 2.0F)
				.texOffs(12, 55).addBox(-2.25F, -14.0F, -1.0F, 2.0F, 7.0F, 2.0F)
				.texOffs(2, 61).addBox(-5.5F, -9.0F, -1.0F, 3.0F, 2.0F, 1.0F)
				.texOffs(2, 61).addBox(2.5F, -9.0F, -1.0F, 3.0F, 2.0F, 1.0F, true)
				.texOffs(23, 52).addBox(0.0F, -2.0F, -7.0F, 0.0F, 7.0F, 5.0F), PartPose.ZERO);
		
		head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(34, 46).addBox(-2.0F, -4.0F, -5.0F, 5.0F, 7.0F, 10.0F), PartPose.offsetAndRotation(-0.5F, -3.5F, -2.0F, 0.7854F, 0.0F, 0.0F));
		
		return LayerDefinition.create(modelData, 64, 64);
	}
	
}