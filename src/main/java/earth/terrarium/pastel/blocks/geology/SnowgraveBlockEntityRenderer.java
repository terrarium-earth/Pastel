package earth.terrarium.pastel.blocks.geology;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;

public class SnowgraveBlockEntityRenderer implements BlockEntityRenderer<SnowgraveBlockEntity> {
    protected static EntityRenderDispatcher dispatcher;

    public SnowgraveBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
    }

    @Override
    public void render(
        SnowgraveBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
        int packedLight, int packedOverlay
    ) {
        // we only render once per feature
        if (!blockEntity.getBlockState()
                        .getValue(SnowgraveBlock.SHOULD_RENDER) || blockEntity.getLevel() == null)
            return;
        var variant = blockEntity.getBlockState()
                                 .getValue(SnowgraveBlock.FROZEN_MOB);
        // we store a cached mob to render so we don't have to recreate one every render tick
        if (blockEntity.cachedMob == null) {
            switch (variant) {
                case ZOMBIE -> blockEntity.cachedMob = new Zombie(blockEntity.getLevel());
                case SKELETON -> blockEntity.cachedMob = new Skeleton(EntityType.SKELETON, blockEntity.getLevel());
                case SPIDER -> blockEntity.cachedMob = new Spider(EntityType.SPIDER, blockEntity.getLevel());
            }
            blockEntity.cachedMob.setNoAi(true);
        }
        var toRender = blockEntity.cachedMob;
        var offset = variant == SnowgraveBlock.FrozenMob.SPIDER ? 1 : 0.5;
        poseStack.pushPose();
        poseStack.scale(0.9f,0.9f,0.9f);
        poseStack.translate(offset/0.9,0, offset/0.9);
        dispatcher.render(toRender,0,0,0,0,0,poseStack,bufferSource,packedLight);
        poseStack.popPose();
    }
}
