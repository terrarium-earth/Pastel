package earth.terrarium.pastel.blocks.mob_head.client.models;

import earth.terrarium.pastel.blocks.mob_head.client.PastelSkullModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(
    Dist.CLIENT
)
public class FoxHeadModel extends PastelSkullModel {

    public FoxHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData
            .addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder
                    .create()
                    .texOffs(1, 5)
                    .addBox(-4.0F, -6.0F, -3.0F, 8.0F, 6.0F, 6.0F),
                PartPose.ZERO
            );
        head
            .addOrReplaceChild(
                PartNames.RIGHT_EAR,
                CubeListBuilder
                    .create()
                    .texOffs(8, 1)
                    .addBox(-4.0F, -8.0F, -2.0F, 2.0F, 2.0F, 1.0F),
                PartPose.ZERO
            );
        head
            .addOrReplaceChild(
                PartNames.LEFT_EAR,
                CubeListBuilder
                    .create()
                    .texOffs(15, 1)
                    .addBox(2.0F, -8.0F, -2.0F, 2.0F, 2.0F, 1.0F),
                PartPose.ZERO
            );
        head
            .addOrReplaceChild(
                PartNames.NOSE,
                CubeListBuilder
                    .create()
                    .texOffs(6, 18)
                    .addBox(-2.0F, -1.99F, -6.0F, 4.0F, 2.0F, 3.0F),
                PartPose.ZERO
            );

        return LayerDefinition.create(modelData, 48, 32);
    }

}
