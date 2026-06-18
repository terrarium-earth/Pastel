package earth.terrarium.pastel.particle.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.particle.render.EarlyRenderingParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(
    Dist.CLIENT
)
public class PastelTransmissionParticle extends TransmissionParticle implements EarlyRenderingParticle {

    private final ItemRenderer itemRenderer;

    private final List<Vec3> travelPositions;

    private final ItemStack itemStack;

    private final ParticleOptions particleEffect;

    public PastelTransmissionParticle(
        ItemRenderer itemRenderer,
        ClientLevel world,
        double x,
        double y,
        double z,
        List<BlockPos> travelPositions,
        ItemStack stack,
        int travelTime,
        int networkColor
    ) {
        super(world, x, y, z, new BlockPositionSource(travelPositions.get(travelPositions.size() - 1)), travelTime);

        this.itemRenderer = itemRenderer;
        this.itemStack = stack;
        this.quadSize = 0.25F;
        this.particleEffect = new DustParticleOptions(ColorHelper.colorIntToVec(networkColor), 0.8F);

        this.travelPositions = new ArrayList<>();
        for (
            BlockPos p : travelPositions
        ) {
            this.travelPositions.add(Vec3.atCenterOf(p));
        }

        // spawning sound & particles
        Vec3 startPos = this.travelPositions.get(0);
        world
            .playLocalSound(
                startPos.x(),
                startPos.y() + 0.25,
                startPos.z(),
                SoundEvents.ITEM_PICKUP,
                SoundSource.BLOCKS,
                0.15F * PastelCommon.CONFIG.BlockSoundVolume + world.random.nextFloat() / 10F,
                0.8F + world.random.nextFloat() * 0.3F,
                true
            );
        world.addParticle(ParticleTypes.BUBBLE_POP, startPos.x(), startPos.y() + 0.25, startPos.z(), 0, 0, 0);
    }

    @Override
    public void tick() {
        this.age++;

        int vertexCount = this.travelPositions.size() - 1;
        float travelPercent = (float) this.age / this.lifetime;
        if (travelPercent >= 1.0F) {
            Vec3 destination = this.travelPositions.get(vertexCount);
            level
                .playLocalSound(
                    destination.x(),
                    destination.y() + 0.25,
                    destination.z(),
                    SoundEvents.ITEM_PICKUP,
                    SoundSource.BLOCKS,
                    0.1F * PastelCommon.CONFIG.BlockSoundVolume + random.nextFloat() / 10F,
                    0.6F + level.random.nextFloat() * 0.3F,
                    true
                );
            level
                .addParticle(
                    ParticleTypes.BUBBLE_POP,
                    destination.x(),
                    destination.y() + 0.25,
                    destination.z(),
                    0,
                    0,
                    0
                );
            this.remove();
            return;
        }

        float progress = travelPercent * vertexCount;
        int startNodeID = (int) progress;
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        Vec3 source = this.travelPositions.get(startNodeID);
        Vec3 destination = this.travelPositions.get(startNodeID + 1);

        float nodeProgress = progress % 1;
        this.x = Mth.lerp(nodeProgress, source.x, destination.x);
        this.y = Mth.lerp(nodeProgress, source.y, destination.y);
        this.z = Mth.lerp(nodeProgress, source.z, destination.z);

        //if (PastelCommon.CONFIG.PastelNetworkParticles && this.age % 3 == 0) {
        //	level.addParticle(particleEffect, x + random.nextDouble() * 0.4 - 0.2, y + random.nextDouble() * 0.4 - 0
        //	.2, z + random.nextDouble() * 0.4 - 0.2, random.nextDouble() * 0.4 - 0.2, random.nextDouble() * 0.4 - 0.2,
        //	random.nextDouble() * 0.4 - 0.2);
        //} TODO make these not bad and reimplement
    }

    @Override
    public void renderAsEntity(
        final PoseStack poseStack,
        final MultiBufferSource vertexConsumers,
        final Camera camera,
        final float tickDelta
    ) {
        final Vec3 cameraPos = camera.getPosition();
        final float x = (float) (Mth.lerp(tickDelta, xo, this.x));
        final float y = (float) (Mth.lerp(tickDelta, yo, this.y));
        final float z = (float) (Mth.lerp(tickDelta, zo, this.z));
        var xOffset = x - cameraPos.x;
        var zOffset = z - cameraPos.z;

        Quaternionf rot = Axis.YP.rotation((float) Mth.atan2(xOffset, zOffset));

        poseStack.pushPose();

        poseStack.translate(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z);
        final int light = getLightColor(tickDelta);
        poseStack.mulPose(rot);
        //poseStack.scale(0.65F, 0.65F, 0.65F);
        poseStack.translate(0, -0.15, 0);
        itemRenderer
            .renderStatic(
                itemStack,
                ItemDisplayContext.GROUND,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                vertexConsumers,
                level,
                0
            );

        poseStack.popPose();
    }

}
