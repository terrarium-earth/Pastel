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
public class ShulkerHeadModel extends SpectrumSkullModel {

	public ShulkerHeadModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		
		PartDefinition base = modelPartData.addOrReplaceChild(
				PartNames.HEAD,
				CubeListBuilder.create().texOffs(0, 28).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F),
				PartPose.ZERO
		);
		
		base.addOrReplaceChild(
				"shulker_head",
				CubeListBuilder.create().texOffs(0, 52).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 6.0F, 6.0F),
				PartPose.ZERO
		);

		return LayerDefinition.create(modelData, 64, 64);
	}
	
	@Override
	public float getScale() {
		return 0.5F;
	}

}
