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
public class WardenHeadModel extends PastelSkullModel {

    public WardenHeadModel(ModelPart root) {
        super(root);
    }

    // TODO Add overlays
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(
            PartNames.HEAD, CubeListBuilder.create()
                                           .texOffs(0, 32)
                                           .addBox(
                                               -8.0F, -16.0F, -5.0F, 16.0F, 16.0F, 10.0F), PartPose.ZERO
        );
        head.addOrReplaceChild(
            PartNames.LEFT_TENDRIL, CubeListBuilder.create()
                                                   .texOffs(52, 32)
                                                   .addBox(-16.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F),
            PartPose.offset(-8.0F, -12.0F, 0.0F)
        );
        head.addOrReplaceChild(
            PartNames.RIGHT_TENDRIL, CubeListBuilder.create()
                                                    .texOffs(58, 0)
                                                    .addBox(0.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F),
            PartPose.offset(8.0F, -12.0F, 0.0F)
        );

        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public float getScale() {
        return 0.6F;
    }

}
