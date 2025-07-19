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
public class PandaHeadModel extends PastelSkullModel {

    public PandaHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        
        modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create()
                        .texOffs(0, 6).addBox(-6.5F, -10.0F, -4.5F, 13.0F, 10.0F, 9.0F)
                        .texOffs(45, 16).addBox("nose", -3.5F, -5.0F, -6.5F, 7.0F, 5.0F, 2.0F)
                        .texOffs(52, 25).addBox("left_ear", 3.5F, -13.0F, -1.5F, 5.0F, 4.0F, 1.0F)
                        .texOffs(52, 25).addBox("right_ear", -8.5F, -13.0F, -1.5F, 5.0F, 4.0F, 1.0F),
                PartPose.ZERO
        );
    
        return LayerDefinition.create(modelData, 64, 64);
    }

}