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
public class TurtleHeadModel extends PastelSkullModel {

    public TurtleHeadModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        
        modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create().texOffs(3, 0).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F),
                PartPose.ZERO
        );
    
        return LayerDefinition.create(modelData, 128, 64);
    }

}