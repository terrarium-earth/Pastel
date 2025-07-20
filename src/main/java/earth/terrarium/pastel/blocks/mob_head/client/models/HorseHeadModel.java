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
public class HorseHeadModel extends PastelSkullModel {

    public HorseHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(
            PartNames.HEAD, CubeListBuilder.create()
                                           .texOffs(0, 35)
                                           .addBox(
                                               -2.0F, -6.0F, -3.5F, 4.0F, 6.0F, 7.0F), PartPose.ZERO
        );
        PartDefinition face = head.addOrReplaceChild(
            "face", CubeListBuilder.create()
                                   .texOffs(0, 13)
                                   .addBox(-3.0F, -11.0F, -3.5F, 6.0F, 5.0F, 7.0F), PartPose.ZERO
        );
        head.addOrReplaceChild(
            "mane", CubeListBuilder.create()
                                   .texOffs(56, 36)
                                   .addBox(-1.0F, -11.0F, 3.51F, 2.0F, 10.0F, 2.0F), PartPose.ZERO
        );
        head.addOrReplaceChild(
            "upper_mouth", CubeListBuilder.create()
                                          .texOffs(0, 25)
                                          .addBox(-2.0F, -11.0F, -8.5F, 4.0F, 5.0F, 5.0F), PartPose.ZERO
        );
        face.addOrReplaceChild(
            "left_ear", CubeListBuilder.create()
                                       .texOffs(19, 16)
                                       .addBox(
                                           0.55F, -13.0F, 2.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)),
            PartPose.ZERO
        );
        face.addOrReplaceChild(
            "right_ear", CubeListBuilder.create()
                                        .texOffs(19, 16)
                                        .addBox(
                                            -2.55F, -13.0F, 2.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)),
            PartPose.ZERO
        );

        return LayerDefinition.create(modelData, 64, 64);
    }

}
