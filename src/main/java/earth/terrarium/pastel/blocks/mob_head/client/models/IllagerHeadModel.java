package earth.terrarium.pastel.blocks.mob_head.client.models;

import earth.terrarium.pastel.blocks.mob_head.client.PastelSkullModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@OnlyIn(Dist.CLIENT)
public class IllagerHeadModel extends PastelSkullModel {

    public IllagerHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(
            PartNames.HEAD,
            CubeListBuilder.create()
                           .texOffs(0, 0)
                           .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
            PartPose.ZERO
        );
        head.addOrReplaceChild("hat", CubeListBuilder.create()
                                                     .texOffs(32, 0)
                                                     .addBox(
                                                         -4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F,
                                                         new CubeDeformation(0.45F)
                                                     ), PartPose.ZERO
        );
        head.addOrReplaceChild("nose", CubeListBuilder.create()
                                                      .texOffs(24, 0)
                                                      .addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                               PartPose.offset(0.0F, -2.0F, 0.0F)
        );

        return LayerDefinition.create(modelData, 64, 64);
    }

}
