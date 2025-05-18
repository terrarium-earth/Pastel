package de.dafuqs.spectrum.blocks.mob_head.client.models;


import de.dafuqs.spectrum.blocks.mob_head.client.SpectrumSkullModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class DolphinHeadModel extends SpectrumSkullModel {
	
	public DolphinHeadModel(ModelPart root) {
		super(root);
	}
	
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		
		PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -3.0F, 8.0F, 7.0F, 6.0F), PartPose.ZERO);
		head.addOrReplaceChild(PartNames.NOSE, CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F), PartPose.offset(0.0F, -4.0F, 0.0F));
		
		return LayerDefinition.create(modelData, 64, 64);
	}
	
}