package earth.terrarium.pastel.blocks.mob_head.client.models;

import earth.terrarium.pastel.blocks.mob_head.client.PastelSkullModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmadilloHeadModel extends PastelSkullModel {

    public ArmadilloHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition modelPartData3 = modelPartData.addOrReplaceChild(
            PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));
        modelPartData3.addOrReplaceChild(
            "head_cube", CubeListBuilder.create()
                                        .texOffs(43, 15)
                                        .addBox(
                                            -1.5F, -1.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F)
        );
        PartDefinition modelPartData4 = modelPartData3.addOrReplaceChild(
            PartNames.RIGHT_EAR, CubeListBuilder.create(), PartPose.offset(-1.0F, -1.0F, 0.0F));
        modelPartData4.addOrReplaceChild(
            "right_ear_cube", CubeListBuilder.create()
                                             .texOffs(43, 10)
                                             .addBox(
                                                 -2.0F, -3.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(-0.5F, 0.0F, -0.6F, 0.1886F, -0.3864F, -0.0718F)
        );
        PartDefinition modelPartData5 = modelPartData3.addOrReplaceChild(
            PartNames.LEFT_EAR, CubeListBuilder.create(), PartPose.offset(1.0F, -2.0F, 0.0F));
        modelPartData5.addOrReplaceChild(
            "left_ear_cube", CubeListBuilder.create()
                                            .texOffs(47, 10)
                                            .addBox(
                                                0.0F, -3.0F, 0.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.5F, 1.0F, -0.6F, 0.1886F, 0.3864F, 0.0718F)
        );

        return LayerDefinition.create(modelData, 64, 64);
    }

}
