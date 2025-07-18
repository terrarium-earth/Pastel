package earth.terrarium.pastel.blocks.mob_head.client.models;

import earth.terrarium.pastel.blocks.mob_head.client.PastelSkullModel;
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
public class StriderHeadModel extends PastelSkullModel {

    public StriderHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(
            PartNames.HEAD, CubeListBuilder.create()
                                           .texOffs(0, 0)
                                           .addBox(
                                               -8.0F, -14.0F, -8.0F, 16.0F, 14.0F, 16.0F), PartPose.ZERO
        );
        head.addOrReplaceChild(
            "right_bottom_bristle", CubeListBuilder.create()
                                                   .texOffs(16, 65)
                                                   .addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true),
            PartPose.offsetAndRotation(-8.0F, -4.0F, -8.0F, 0.0F, 0.0F, -1.2217305F)
        );
        head.addOrReplaceChild(
            "right_middle_bristle", CubeListBuilder.create()
                                                   .texOffs(16, 49)
                                                   .addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true),
            PartPose.offsetAndRotation(-8.0F, -9.0F, -8.0F, 0.0F, 0.0F, -1.134464F)
        );
        head.addOrReplaceChild(
            "right_top_bristle", CubeListBuilder.create()
                                                .texOffs(16, 33)
                                                .addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, true),
            PartPose.offsetAndRotation(-8.0F, -13.0F, -8.0F, 0.0F, 0.0F, -0.87266463F)
        );
        head.addOrReplaceChild(
            "left_top_bristle", CubeListBuilder.create()
                                               .texOffs(16, 33)
                                               .addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F),
            PartPose.offsetAndRotation(8.0F, -14.0F, -8.0F, 0.0F, 0.0F, 0.87266463F)
        );
        head.addOrReplaceChild(
            "left_middle_bristle", CubeListBuilder.create()
                                                  .texOffs(16, 49)
                                                  .addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F),
            PartPose.offsetAndRotation(8.0F, -10.0F, -8.0F, 0.0F, 0.0F, 1.134464F)
        );
        head.addOrReplaceChild(
            "left_bottom_bristle", CubeListBuilder.create()
                                                  .texOffs(16, 65)
                                                  .addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F),
            PartPose.offsetAndRotation(8.0F, -5.0F, -8.0F, 0.0F, 0.0F, 1.2217305F)
        );

        return LayerDefinition.create(modelData, 64, 128);
    }

    @Override
    public float getScale() {
        return 0.5F;
    }

}
