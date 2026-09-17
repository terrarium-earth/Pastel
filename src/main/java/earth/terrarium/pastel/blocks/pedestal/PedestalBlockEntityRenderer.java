package earth.terrarium.pastel.blocks.pedestal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.helpers.render.ParticleHelper;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.registries.client.PastelRenderLayers;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(
    Dist.CLIENT
)
public class PedestalBlockEntityRenderer<C extends PedestalBlockEntity> implements BlockEntityRenderer<C> {
    private static final float[] cmyDists = {
        0.55f,
        0.65f,
        0.75f,
        0.85f,
        0.95f,
        1.05f,
        1.15f,
        1.25f,
        1.35f,
        1.45f,
        1.55f,
        1.65f,
        1.75f,
        1.85f,
        1.95f,
        2.05f,
        2.15f,
        2.25f,
        -0.55f,
        -0.65f,
        -0.75f,
        -0.85f,
        -0.95f,
        -1.05f,
        -1.15f,
        -1.25f,
        -1.35f,
        -1.45f,
        -1.55f,
        -1.65f,
        -1.75f,
        -1.85f,
        -1.95f,
        -2.05f,
        -2.15f,
        -2.25f
    };

    private static final int kNum = 150;

    private static final int wNum = 150;

    private Map<Vector3f, Color> cyanLocs = new HashMap<>();

    private Map<Vector3f, Color> magentaLocs = new HashMap<>();

    private Map<Vector3f, Color> yellowLocs = new HashMap<>();

    private Map<Vector3f, Color> blackLocs = new HashMap<>();

    private Map<Vector3f, Color> whiteLocs = new HashMap<>();

    public PedestalBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super();
        // CMY lines
        var magenta = new Color(InkColors.MAGENTA_COLOR);
        var cyan = new Color(InkColors.CYAN_COLOR);
        var yellow = new Color(InkColors.YELLOW_COLOR);

        for (
            float dist : cmyDists
        ) {
            magentaLocs.put(new Vector3f(dist, 0.1f, 0f), magenta);
            cyanLocs.put(new Vector3f(dist, 0.1f, 0f).rotateY(2f * (float) Math.PI / 3f), cyan);
            yellowLocs.put(new Vector3f(dist, 0.1f, 0f).rotateY(-2f * (float) Math.PI / 3f), yellow);
        }

        // black circle
        var black = new Color(0xff121545);
        float kIncrement = (float) (2f * Math.PI / (float) kNum);
        for (
            int i = 0;
            i < kNum;
            i++
        ) {
            blackLocs.put(new Vector3f(2.35f, 0.1f, 0f).rotateY(kIncrement * i), black);
        }

        // white circle
        var white = new Color(InkColors.WHITE_COLOR);
        float wIncrement = (float) (2f * Math.PI / (float) wNum);
        for (
            int i = 0;
            i < wNum;
            i++
        ) {
            whiteLocs.put(new Vector3f(5f, 0.1f, 0f).rotateY(wIncrement * i), white);
        }
    }

    @Override
    public void render(
        PedestalBlockEntity pedestal,
        float partialTicks,
        PoseStack poseStack,
        MultiBufferSource vertexConsumerProvider,
        int packedLight,
        int overlay
    ) {
        if (pedestal.getLevel() == null) {
            return;
        }

        if (pedestal.recipe.isEmpty())
            return;

        var particleBuffer = vertexConsumerProvider
            .getBuffer(
                PastelRenderLayers.GlowInTheDarkRenderLayer
                    .get(PastelCommon.locate("textures/particle/pastel_transmission.png"))
            );
        var camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        var recipe = pedestal.recipe.get().value();
        if (recipe instanceof PedestalRecipe pr) {
            float time = pedestal
                .getLevel()
                .getGameTime() % 50000 + partialTicks;
            var cyan = pr.getPowderInputs().getOrDefault(PastelGemstoneColor.CYAN, 0);
            var magenta = pr.getPowderInputs().getOrDefault(PastelGemstoneColor.MAGENTA, 0);
            var yellow = pr.getPowderInputs().getOrDefault(PastelGemstoneColor.YELLOW, 0);
            var black = pr.getPowderInputs().getOrDefault(PastelGemstoneColor.BLACK, 0);
            var white = pr.getPowderInputs().getOrDefault(PastelGemstoneColor.WHITE, 0);

            Map<Vector3f, Color> cmyLocs = new HashMap<>();
            if (cyan > 0) cmyLocs.putAll(cyanLocs);
            if (magenta > 0) cmyLocs.putAll(magentaLocs);
            if (yellow > 0) cmyLocs.putAll(yellowLocs);

            if (!cmyLocs.isEmpty())
                for (
                    Map.Entry<Vector3f, Color> entry : cmyLocs.entrySet()
                ) {
                    var particleLoc = entry.getKey();
                    var color = entry.getValue();
                    var gTime = pedestal.getLevel().getGameTime();
                    var offsetVec = getOffsetVecCMY(gTime, particleLoc, partialTicks);
                    float x = offsetVec.x + pedestal.getBlockPos().getX() + 0.5f;
                    float y = offsetVec.y + pedestal.getBlockPos().getY();
                    float z = offsetVec.z + pedestal.getBlockPos().getZ() + 0.5f;
                    var hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                    hsb[1] *= (float) Math.sqrt(1.1 - particleLoc.length() / 4.0);
                    renderRotatedQuad(
                        particleBuffer,
                        camera,
                        camera.rotation(),
                        0,
                        x,
                        y,
                        z,
                        packedLight,
                        getQuadSize(particleLoc),
                        Color.getHSBColor(hsb[0], hsb[1], hsb[2])
                    );
                }

            if (black > 0) {
                var wiggle = false;
                var blackBuffer = vertexConsumerProvider
                    .getBuffer(
                        PastelRenderLayers.GlowInTheDarkRenderLayer
                            .get(PastelCommon.locate("textures/particle/sphere_small.png"))
                    );
                for (
                    Map.Entry<Vector3f, Color> entry : blackLocs.entrySet()
                ) {
                    var particleLoc = entry.getKey();
                    var color = entry.getValue();
                    var gTime = pedestal.getLevel().getGameTime();
                    var offsetVec = getOffsetVecK(gTime, particleLoc, partialTicks, wiggle);
                    float x = offsetVec.x + pedestal.getBlockPos().getX() + 0.5f;
                    float y = offsetVec.y + pedestal.getBlockPos().getY();
                    float z = offsetVec.z + pedestal.getBlockPos().getZ() + 0.5f;
                    renderRotatedQuad(
                        blackBuffer,
                        camera,
                        camera.rotation(),
                        (float) (gTime % 50000) / 25,
                        x,
                        y,
                        z,
                        packedLight,
                        0.08f,
                        color
                    );
                    wiggle = !wiggle;
                }
            }

            if (white > 0) {
                var whiteBuffer = vertexConsumerProvider
                    .getBuffer(
                        PastelRenderLayers.GlowInTheDarkRenderLayer
                            .get(PastelCommon.locate("textures/particle/sphere.png"))
                    );
                for (
                    Map.Entry<Vector3f, Color> entry : whiteLocs.entrySet()
                ) {
                    var particleLoc = entry.getKey();
                    var color = entry.getValue();
                    var gTime = pedestal.getLevel().getGameTime();
                    var offsetVec = getOffsetVecW(gTime, particleLoc, partialTicks);
                    var test = 1f - (gTime % 50000 + partialTicks) % 200 / 200f;
                    if (pedestal.active && test < 0.13f && test > 0.129f) {
                        pedestal
                            .getLevel()
                            .addParticle(
                                ColoredSparkleRisingParticleEffect.WHITE,
                                pedestal.getBlockPos().getCenter().x,
                                pedestal.getBlockPos().getCenter().y,
                                pedestal.getBlockPos().getCenter().z,
                                (Math.random() - 0.5) / 2.5d,
                                Math.random() / 5d,
                                (Math.random() - 0.5) / 2.5d
                            );
                    }
                    if (offsetVec == null) continue;
                    float x = offsetVec.x + pedestal.getBlockPos().getX() + 0.5f;
                    float y = offsetVec.y + pedestal.getBlockPos().getY();
                    float z = offsetVec.z + pedestal.getBlockPos().getZ() + 0.5f;
                    renderRotatedQuad(whiteBuffer, camera, camera.rotation(), 0, x, y, z, packedLight, 0.1f, color);
                }
            }

            poseStack.pushPose();
            double height = Math.sin((time) / 8.0) / 6.0; // item height
            poseStack.translate(0.5F, 1.3 + height, 0.5F); // position offset
            poseStack.mulPose(Axis.YP.rotationDegrees((time) * 2)); // item stack rotation

            Minecraft
                .getInstance()
                .getItemRenderer()
                .renderStatic(
                    pr.getResultItem(pedestal.getLevel().registryAccess()),
                    ItemDisplayContext.GROUND,
                    LightTexture.FULL_BRIGHT,
                    overlay,
                    poseStack,
                    vertexConsumerProvider,
                    pedestal.getLevel(),
                    0
                );
            poseStack.popPose();
        }
    }

    protected Vector3f getOffsetVecCMY(long time, Vector3f vec, float partialTicks) {
        float t = (time % 50000) + partialTicks;
//        float magnitude = vec.length() * 0.14f;
        float magnitude = 0.4f + vec.length() * 0.01f;
//        float spd = 0.2f * (vec.length() / 10f);
        float spd = 0.1f;
        float offset = (vec.length() / 2f) * ((float) Math.PI);
        Vector3f newVec = new Vector3f(vec);
        return newVec.rotateY((float) Math.sin(t * spd + offset) * magnitude);
    }

    protected Vector3f getOffsetVecK(long time, Vector3f vec, float partialTicks, boolean wiggle) {
        float t = (time % 50000) + partialTicks;
        float magnitude = 0.01f;
        Vector3f newVec = getOffsetVecCMY(time, vec, partialTicks);
        return newVec.mul(1f + (Mth.sin(t) - 0.5f) * magnitude * (wiggle ? -1 : 1));
    }

    @Nullable protected Vector3f getOffsetVecW(long time, Vector3f vec, float partialTicks) {
        float t = time % 50000 + partialTicks;
        float distMod = 1f - t % 200 / 200f;
        if (distMod < 0.1f) {
            return null;
        }
        Vector3f newVec = new Vector3f(vec);
        return newVec.mul(distMod).rotateY((float) (distMod * Math.PI));
    }

    protected void renderRotatedQuad(
        VertexConsumer buffer,
        Camera camera,
        Quaternionf quaternion,
        float partialTicks,
        float x,
        float y,
        float z,
        int packedLight,
        float quadSize,
        Color color
    ) {
        Vec3 vec3 = camera.getPosition();
        float f = (float) (x - vec3.x());
        float f1 = (float) (y - vec3.y());
        float f2 = (float) (z - vec3.z());
        this.renderRotatedQuad(buffer, quaternion, f, f1, f2, partialTicks, packedLight, quadSize, color);
    }

    protected float getQuadSize(Vector3f pos) {
        return 0.25f * (0.8f - pos.length() / 4f);
    }

    protected void renderRotatedQuad(
        VertexConsumer buffer,
        Quaternionf quaternion,
        float x,
        float y,
        float z,
        float partialTicks,
        int packedLight,
        float quadSize,
        Color color
    ) {
        float f1 = 0;
        float f2 = 1;
        float f3 = 0;
        float f4 = 1;
        this.renderVertex(buffer, quaternion, x, y, z, 1.0F, -1.0F, quadSize, f2, f4, packedLight, color);
        this.renderVertex(buffer, quaternion, x, y, z, 1.0F, 1.0F, quadSize, f2, f3, packedLight, color);
        this.renderVertex(buffer, quaternion, x, y, z, -1.0F, 1.0F, quadSize, f1, f3, packedLight, color);
        this.renderVertex(buffer, quaternion, x, y, z, -1.0F, -1.0F, quadSize, f1, f4, packedLight, color);
    }

    private void renderVertex(
        VertexConsumer buffer,
        Quaternionf quaternion,
        float x,
        float y,
        float z,
        float xOffset,
        float yOffset,
        float quadSize,
        float u,
        float v,
        int packedLight,
        Color color
    ) {
        Vector3f vector3f = new Vector3f(xOffset, yOffset, 0.0F).rotate(quaternion).mul(quadSize).add(x, y, z);
        buffer
            .addVertex(vector3f.x(), vector3f.y(), vector3f.z())
            .setUv(u, v)
            .setColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
            .setLight(packedLight)
            .setNormal(0, 1, 0)
            .setUv1(0, 10);
    }

}
