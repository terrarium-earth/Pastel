package earth.terrarium.pastel.blocks.spirit_instiller;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.FastColor;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Rotation;

public class SpiritInstillerBlockEntityRenderer implements BlockEntityRenderer<SpiritInstillerBlockEntity> {

    private static final Material SPRITE = new Material(
        InventoryMenu.BLOCK_ATLAS, PastelCommon.locate("block/spirit_instiller"));
    protected final double ITEM_STACK_RENDER_HEIGHT = 0.95F;

    private final ModelPart head;
    private final ModelPart spectralblossom;
    private final ModelPart geode;
    private final ModelPart calcite;
    private final ModelPart innergeode;

    public SpiritInstillerBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        var root = getTexturedModelData().bakeRoot();
        this.head = root.getChild("head");
        this.spectralblossom = root.getChild("spectralblossom");
        this.geode = root.getChild("geode");
        this.calcite = geode.getChild("calcite");
        this.innergeode = calcite.getChild("innergeode");
    }

    @Override
    public void render(
        SpiritInstillerBlockEntity instiller, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
        int light, int overlay
    ) {
        if (instiller.animator == null)
            return;

        var time = instiller.getLevel()
                            .getGameTime() % 1000000;
        instiller.animator.animate(tickDelta, time);

        // The item lying on top of the spirit instiller
        ItemStack stack = instiller.getItem(0);
        if (instiller.getMultiblockRotation() != null) {
            Rotation itemFacingDirection = instiller.getMultiblockRotation();

            // item stack rotation
            var rotation = switch (itemFacingDirection) {
                case NONE -> 0;
                case CLOCKWISE_90 -> -90;
                case CLOCKWISE_180 -> 180;
                case COUNTERCLOCKWISE_90 -> 90;
            };

            if (!stack.isEmpty()) {
                matrices.pushPose();
                matrices.translate(0.5, 1F + instiller._platformY.get() / 16F, 0.5);
                matrices.mulPose(Axis.YP.rotationDegrees((float) (rotation - Math.toDegrees(instiller.platform))));
                matrices.mulPose(Axis.XP.rotationDegrees(90));
                matrices.translate(-0.0, -0.1, 0.0);
                Minecraft.getInstance()
                         .getItemRenderer()
                         .renderStatic(
                             stack, ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers,
                             instiller.getLevel(), 0
                         );
                matrices.popPose();
            }

            matrices.pushPose();
            var haloAlpha = FastColor.ARGB32.color(Math.round(instiller._haloAlpha.get() * 255), 0xFFFFFF);
            var blossomAlpha = FastColor.ARGB32.color(Math.round(instiller._blossomAlpha.get() * 255), 0xFFFFFF);

            matrices.translate(0.5D, 1.5D, 0.5D);
            matrices.mulPose(Axis.YP.rotationDegrees(rotation));
            matrices.mulPose(Axis.XP.rotationDegrees(180));
            var vertices = SPRITE.buffer(vertexConsumers, RenderType::entityTranslucent);

            instiller.platform += (float) Math.toRadians(instiller._platformSpin.get());

            head.y = 9.5F - instiller._platformY.get();
            head.yRot = instiller.platform;
            head.render(matrices, vertices, light, overlay);

            if (instiller._blossomAlpha.get() > 0) {
                spectralblossom.y = -25 - instiller._haloY.get();
                spectralblossom.render(matrices, vertices, LightTexture.FULL_BRIGHT, overlay, blossomAlpha);
            }

            if (instiller._haloAlpha.get() > 0) {
                geode.y = -25 - instiller._haloY.get();

                instiller.geode += (float) Math.toRadians(instiller._haloSpin.get());
                instiller.calcite += (float) Math.toRadians(-instiller._haloSpin.get() * 1.25);
                instiller.innergeode += (float) Math.toRadians(instiller._haloSpin.get() * 1.4);

                geode.zRot = instiller.geode;
                calcite.zRot = instiller.calcite;
                innergeode.zRot = instiller.innergeode;
                geode.render(matrices, vertices, LightTexture.FULL_BRIGHT, overlay, haloAlpha);
            }

            matrices.popPose();
        }

    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addOrReplaceChild(
            "head", CubeListBuilder.create(), PartPose.offset(0.0F, 9.5F, 0.0F));

        head.addOrReplaceChild(
            "pedestal_r1", CubeListBuilder.create()
                                          .texOffs(43, 38)
                                          .addBox(
                                              -7.5F, -1.5F, -7.5F, 15.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, -0.025F, 0.0F, 0.0F, -0.7854F, 0.0F)
        );
        modelPartData.addOrReplaceChild(
            "spectralblossom", CubeListBuilder.create()
                                              .texOffs(58, 24)
                                              .addBox(
                                                  -6.5F, -6.5F, 0.0F, 13.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -25.0F, 0.0F)
        );
        PartDefinition geode = modelPartData.addOrReplaceChild(
            "geode", CubeListBuilder.create()
                                    .texOffs(0, 24)
                                    .addBox(
                                        -14.5F, -14.5F, 0.0F, 29.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, -25.0F, 0.0F)
        );
        PartDefinition calcite = geode.addOrReplaceChild(
            "calcite", CubeListBuilder.create()
                                      .texOffs(0, 56)
                                      .addBox(-12.5F, -12.5F, 1.0F, 25.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, 0.25F)
        );
        calcite.addOrReplaceChild(
            "innergeode", CubeListBuilder.create()
                                         .texOffs(50, 56)
                                         .addBox(
                                             -10.5F, -10.5F, 3.0F, 21.0F, 21.0F, 0.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 0.0F, -0.75F)
        );
        return LayerDefinition.create(modelData, 128, 128);
    }
}
