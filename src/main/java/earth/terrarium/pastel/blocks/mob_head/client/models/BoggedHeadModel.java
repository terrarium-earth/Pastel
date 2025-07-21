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
public class BoggedHeadModel extends PastelSkullModel {

    public BoggedHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addOrReplaceChild(
            PartNames.HEAD,
            CubeListBuilder.create()
                           .texOffs(0, 0)
                           .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)),
            PartPose.ZERO
        );
        head.addOrReplaceChild("red_mushroom_1", CubeListBuilder.create()
                                                                .texOffs(50, 16)
                                                                .addBox(-3.0F, -3.0F, 0.0F, 6.0F, 4.0F, 0.0F),
                               PartPose.offsetAndRotation(3.0F, -8.0F, 3.0F, 0.0F, 0.7853982F, 0.0F)
        );
        head.addOrReplaceChild("red_mushroom_2", CubeListBuilder.create()
                                                                .texOffs(50, 16)
                                                                .addBox(-3.0F, -3.0F, 0.0F, 6.0F, 4.0F, 0.0F),
                               PartPose.offsetAndRotation(3.0F, -8.0F, 3.0F, 0.0F, 2.3561945F, 0.0F)
        );
        head.addOrReplaceChild("brown_mushroom_1", CubeListBuilder.create()
                                                                  .texOffs(50, 22)
                                                                  .addBox(-3.0F, -3.0F, 0.0F, 6.0F, 4.0F, 0.0F),
                               PartPose.offsetAndRotation(-3.0F, -8.0F, -3.0F, 0.0F, 0.7853982F, 0.0F)
        );
        head.addOrReplaceChild("brown_mushroom_2", CubeListBuilder.create()
                                                                  .texOffs(50, 22)
                                                                  .addBox(-3.0F, -3.0F, 0.0F, 6.0F, 4.0F, 0.0F),
                               PartPose.offsetAndRotation(-3.0F, -8.0F, -3.0F, 0.0F, 2.3561945F, 0.0F)
        );
        head.addOrReplaceChild("brown_mushroom_3", CubeListBuilder.create()
                                                                  .texOffs(50, 28)
                                                                  .addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F),
                               PartPose.offsetAndRotation(-2.0F, -1.0F, 4.0F, -1.5707964F, 0.0F, 0.7853982F)
        );
        head.addOrReplaceChild("brown_mushroom_4", CubeListBuilder.create()
                                                                  .texOffs(50, 28)
                                                                  .addBox(-3.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F),
                               PartPose.offsetAndRotation(-2.0F, -1.0F, 4.0F, -1.5707964F, 0.0F, 2.3561945F)
        );

        // TODO Fix textures/texture offsets
        head.addOrReplaceChild("overlay", CubeListBuilder.create()
                                                         .texOffs(0, 0)
                                                         .addBox(
                                                             -4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F,
                                                             new CubeDeformation(0.25F)
                                                         ), PartPose.ZERO
        );

        return LayerDefinition.create(modelData, 64, 32);
    }

}
