package earth.terrarium.pastel.blocks.upgrade;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class UpgradeBlockBlockEntityRenderer<PedestalUpgradeBlockEntity extends BlockEntity>
    implements BlockEntityRenderer<PedestalUpgradeBlockEntity> {

    private final ModelPart root;
    private final ModelPart disk;
    private Material spriteIdentifier;

    public UpgradeBlockBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        LayerDefinition texturedModelData = getTexturedModelData(Upgradeable.UpgradeType.SPEED);
        root = texturedModelData.bakeRoot();
        root.setPos(8.0F, 8.0F, 8.0F);
        disk = root.getChild("gemstone_disk");
    }

    @Override
    public void render(
        PedestalUpgradeBlockEntity entity, float tickDelta, PoseStack poseStack,
        MultiBufferSource vertexConsumerProvider, int light, int overlay
    ) {
        // do not render the floating disk when there is a non-opaque block on top of the pedestal upgrade block
        if (entity.getLevel() != null && entity.getLevel()
                                               .getBlockState(entity.getBlockPos()
                                                                    .above())
                                               .canOcclude()) {
            return;
        }

        Block block = entity.getLevel()
                            .getBlockState(entity.getBlockPos())
                            .getBlock();
        if (block instanceof UpgradeBlock upgradeBlock) {
            float upgradeMod = upgradeBlock.getUpgradeMod();

            VertexConsumer vertexConsumer = spriteIdentifier.buffer(vertexConsumerProvider, RenderType::entityCutout);

            float newYaw = (entity.getLevel()
                                  .getGameTime() % 24000 + tickDelta) / 80.0F;
            root.y = 16.0F + (float) (Math.sin(newYaw) * 0.5);
            disk.yRot = newYaw * upgradeMod * 4;
            root.render(poseStack, vertexConsumer, light, overlay);
        }
    }

    // TODO: Use a different model for each upgrade type
    public @NotNull LayerDefinition getTexturedModelData(Upgradeable.@NotNull UpgradeType upgradeType) {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        switch (upgradeType) {
            case SPEED -> {
                spriteIdentifier = new Material(
                    InventoryMenu.BLOCK_ATLAS, PastelCommon.locate("block/pedestal_upgrade_speed"));

                modelPartData.addOrReplaceChild(
                    "bone", CubeListBuilder.create()
                                           .texOffs(0, 0)
                                           .addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt", CubeListBuilder.create()
                                             .texOffs(20, 2)
                                             .mirror()
                                             .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt2", CubeListBuilder.create()
                                              .texOffs(20, 3)
                                              .mirror()
                                              .addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "gemstone_disk", CubeListBuilder.create()
                                                    .texOffs(0, 16)
                                                    .mirror()
                                                    .addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                return LayerDefinition.create(modelData, 48, 48);
            }
            case YIELD -> {
                spriteIdentifier = new Material(
                    InventoryMenu.BLOCK_ATLAS, PastelCommon.locate("block/pedestal_upgrade_yield"));

                modelPartData.addOrReplaceChild(
                    "bone", CubeListBuilder.create()
                                           .texOffs(0, 0)
                                           .addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt", CubeListBuilder.create()
                                             .texOffs(20, 2)
                                             .mirror()
                                             .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt2", CubeListBuilder.create()
                                              .texOffs(20, 3)
                                              .mirror()
                                              .addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "gemstone_disk", CubeListBuilder.create()
                                                    .texOffs(0, 16)
                                                    .mirror()
                                                    .addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                return LayerDefinition.create(modelData, 48, 48);
            }
            case EFFICIENCY -> {
                spriteIdentifier = new Material(
                    InventoryMenu.BLOCK_ATLAS, PastelCommon.locate("block/pedestal_upgrade_efficiency"));

                modelPartData.addOrReplaceChild(
                    "bone", CubeListBuilder.create()
                                           .texOffs(0, 0)
                                           .addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt", CubeListBuilder.create()
                                             .texOffs(20, 2)
                                             .mirror()
                                             .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt2", CubeListBuilder.create()
                                              .texOffs(20, 3)
                                              .mirror()
                                              .addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "gemstone_disk", CubeListBuilder.create()
                                                    .texOffs(0, 16)
                                                    .mirror()
                                                    .addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                return LayerDefinition.create(modelData, 48, 48);
            }
            default -> {
                spriteIdentifier = new Material(
                    InventoryMenu.BLOCK_ATLAS, PastelCommon.locate("block/pedestal_upgrade_experience"));

                modelPartData.addOrReplaceChild(
                    "bone", CubeListBuilder.create()
                                           .texOffs(0, 0)
                                           .addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt", CubeListBuilder.create()
                                             .texOffs(20, 2)
                                             .mirror()
                                             .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "basalt2", CubeListBuilder.create()
                                              .texOffs(20, 3)
                                              .mirror()
                                              .addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                modelPartData.addOrReplaceChild(
                    "gemstone_disk", CubeListBuilder.create()
                                                    .texOffs(0, 16)
                                                    .mirror()
                                                    .addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F),
                    PartPose.offset(0.0F, 0.0F, 0.0F)
                );
                return LayerDefinition.create(modelData, 48, 48);
            }
        }
    }
}
