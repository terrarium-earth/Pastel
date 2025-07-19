package earth.terrarium.pastel.blocks.mob_head.client.models;

import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StrayHeadModel {

    public static LayerDefinition createMobHeadLayer() {
        var head = SkullModel.createHeadModel();

        // TODO Fix textures/texture offsets
        head.getRoot()
            .addOrReplaceChild(
                "overlay",
                CubeListBuilder.create()
                               .texOffs(0, 0)
                               .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.ZERO
            );

        return LayerDefinition.create(head, 64, 32);
    }

}
