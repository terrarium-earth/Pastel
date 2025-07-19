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

@OnlyIn(Dist.CLIENT)
public class PreservationTurretHeadModel extends PastelSkullModel {

    public PreservationTurretHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();

        modelData.getRoot()
                 .addOrReplaceChild(
                     PartNames.HEAD,
                     CubeListBuilder.create()
                                    .texOffs(0, 0)
                                    .addBox(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F)
                                    .texOffs(0, 24)
                                    .addBox(-8.0F, -16.0F, -8.0F, 16.0F, 2.0F, 16.0F)
                                    .texOffs(0, 42)
                                    .addBox(-7.0F, -14.0F, -7.0F, 14.0F, 6.0F, 14.0F),
                     PartPose.ZERO
                 );

        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public float getScale() {
        return 0.5F;
    }

}
