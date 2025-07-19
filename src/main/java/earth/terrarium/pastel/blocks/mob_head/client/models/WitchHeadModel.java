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
public class WitchHeadModel extends PastelSkullModel {

    public WitchHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F)
                        .texOffs(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 4.0F, 2.0F)
                        .texOffs(0, 64).addBox(-5.0F, -11.0F, -5.0F, 10.0F, 1.0F, 10.0F)
                        .texOffs(0, 76).addBox(-3.5F, -15.0F, -3.5F, 7.0F, 4.0F, 7.0F)
                        .texOffs(0, 87).addBox(-2.0F, -19.0F, -2.0F, 4.0F, 4.0F, 4.0F)
                        .texOffs(0, 95).addBox(0.0F, -20.0F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.ZERO
        );

        return LayerDefinition.create(modelData, 64, 128);
    }

}