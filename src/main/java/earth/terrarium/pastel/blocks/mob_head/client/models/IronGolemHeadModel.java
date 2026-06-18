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
public class IronGolemHeadModel extends PastelSkullModel {

    public IronGolemHeadModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings(
        "unused"
    )
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData
            .addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder
                    .create()
                    .texOffs(0, 0)
                    .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F)
                    .texOffs(24, 0)
                    .addBox(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                PartPose.ZERO
            );

        return LayerDefinition.create(modelData, 128, 128);
    }

}
