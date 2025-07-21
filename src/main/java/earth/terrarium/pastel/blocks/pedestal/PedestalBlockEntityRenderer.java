package earth.terrarium.pastel.blocks.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.registries.client.PastelRenderLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PedestalBlockEntityRenderer<C extends PedestalBlockEntity> implements BlockEntityRenderer<C> {

    private final ResourceLocation GROUND_MARK = PastelCommon.locate("textures/misc/circle.png");
    private final ModelPart circle;

    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super();
        this.circle = getTexturedModelData().bakeRoot()
                                            .getChild("circle");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild("circle", CubeListBuilder.create(), PartPose.offset(8.0F, 0.1F, 8.0F));
        modelPartData.getChild("circle")
                     .addOrReplaceChild(
                         "circle2", CubeListBuilder.create()
                                                   .texOffs(0, 0)
                                                   .addBox(-32.0F, 0.0F, -29F, 64.0F, 0.0F, 64.0F),
                         PartPose.rotation(0.0F, 0.0F, 0.0F)
                     );

        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void render(
        PedestalBlockEntity pedestal, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider,
        int light, int overlay) {

        if (pedestal.getLevel() == null) {
            return;
        }

        if (pedestal.recipe.isEmpty())
            return;

        var recipe = pedestal.recipe.get().value();
        if (recipe instanceof PedestalRecipe pr) {
            float time = pedestal.getLevel()
                               .getGameTime() % 50000 + tickDelta;
            this.circle.yRot = time / 25.0F;
            this.circle.render(
                poseStack, vertexConsumerProvider.getBuffer(
                    PastelRenderLayers.GlowInTheDarkRenderLayer.get(GROUND_MARK)), light, overlay
            );

            poseStack.pushPose();
            double height = Math.sin((time) / 8.0) / 6.0; // item height
            poseStack.translate(0.5F, 1.3 + height, 0.5F); // position offset
            poseStack.mulPose(Axis.YP.rotationDegrees((time) * 2)); // item stack rotation


            Minecraft.getInstance()
                     .getItemRenderer()
                     .renderStatic(
                         pr.getResultItem(pedestal.getLevel().registryAccess()),
                             ItemDisplayContext.GROUND, LightTexture.FULL_BRIGHT, overlay,
                         poseStack, vertexConsumerProvider, pedestal.getLevel(), 0
                     );
            poseStack.popPose();
        }
    }

}
