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
public class BeeHeadModel extends SpectrumSkullModel {

    public BeeHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.5F, -7.0F, -5.0F, 7.0F, 7.0F, 10.0F)
                        .texOffs(2, 0).addBox(-1.5F, -7.0F, -8.0F, 0.0F, 2.0F, 3.0F)
                        .texOffs(3, 3).addBox(-1.5F, -7.0F, -8.0F, 0.0F, 2.0F, 3.0F)
                        .texOffs(3, 3).addBox(1.5F, -7.0F, -8.0F, 0.0F, 2.0F, 3.0F)
                        .texOffs(2, 0).addBox(1.5F, -7.0F, -8.0F, 0.0F, 2.0F, 3.0F),
                PartPose.ZERO
        );

        return LayerDefinition.create(modelData, 64, 64);
    }

}